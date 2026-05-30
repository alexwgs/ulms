package com.cmbccd.ulms.websocket.service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import com.alibaba.fastjson.parser.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.controller.ChatRecordFile;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.Case;
import com.cmbccd.ulms.oht.domain.InitUser;
import com.cmbccd.ulms.oht.domain.StatusJour;
import com.cmbccd.ulms.oht.service.CaseService;
import com.cmbccd.ulms.oht.service.CaseTaskService;
import com.cmbccd.ulms.oht.service.StatusJourService;
import com.cmbccd.ulms.oht.service.StatusTypeService;
import com.cmbccd.ulms.websocket.domain.MsgTemplate;
import com.cmbccd.ulms.websocket.domain.OhtMsgTemplate;

/**
 * 
 * <p>
 * Title: MessageHandle.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WeiGenSheng
 * @date 2020年12月21日
 * @version 1.0
 */
@Component
public class MessageHandle {
	 private static final Logger log = LoggerFactory.getLogger(MessageHandle.class);

	/** 状态更新广播频控：同一用户2秒内最多广播一次 */
	private static final java.util.concurrent.ConcurrentHashMap<String, Long> lastUpdateBroadcast = new java.util.concurrent.ConcurrentHashMap<>();
	private static final long UPDATE_DEBOUNCE_MS = 2000;

	private static boolean shouldDebounceUpdate(String userId) {
		long now = System.currentTimeMillis();
		Long last = lastUpdateBroadcast.put(userId, now);
		return last != null && (now - last) < UPDATE_DEBOUNCE_MS;
	}

	/**
	 * 设置日期格式
	 */
	private static final ThreadLocal<SimpleDateFormat> dateFormatThreadLocal =
			ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	/**
	 * 定义一个该类的静态变量
	 */
	public static MessageHandle handle;

	@Resource
	private StatusTypeService statusTypeService;

	@Resource
	private StatusJourService statusJourService;

	@Resource
	private CaseService caseService;

	@Resource
	private CaseTaskService caseTaskService;

	// 使用postconstruct注解，将需要注入的类添加到静态变量中

	@PostConstruct
	public void init() {
		handle = this;
	}

	ChatRecordFile fileRecord = new ChatRecordFile();

	/**
	 * 刷新用户的未结案件缓存并广播用户更新。
	 * 案件状态变更后调用，确保在线列表中的用户状态实时同步。
	 */
	private static void refreshUserCaseAndBroadcast(String userId) {
		InitUser user = WebSocketServer.state.getUser(userId);
		if (user == null || user.getOhtRole() == null) return;

		Case unfinishCase;
		if (user.getOhtRole().getRoleType() == 0) {
			unfinishCase = handle.caseService.getUnfinishCaseByHelper(userId);
		} else {
			unfinishCase = handle.caseService.getUnfinishCaseByListener(userId);
		}
		if (unfinishCase != null) {
			if (unfinishCase.getBuildId() != null) {
				unfinishCase.setBuildUser(DataCache.EMPLOYEE.get(unfinishCase.getBuildId()));
			}
			if (unfinishCase.getPickId() != null) {
				unfinishCase.setPickUser(DataCache.EMPLOYEE.get(unfinishCase.getPickId()));
			}
		}
		user.setUnfinishCase(unfinishCase);
		WebSocketServer.state.putUser(userId, user);
		WebSocketServer.boardcastAsync(
				MsgTemplate.success("oht", "system", "update").add("data", user), null);
	}

	public void messageHandle(String msg, String userId) {
		if (Util.isNullorEmpty(msg)) {
			return;
		}
		String modal = "oht", type = "message";

			// 调试：打印每个字符的详细信息
//		debugMessage(msg);
//		JSONObject jsonMsg = messageHandle(msg);
		log.info("ws:" + msg);
		JSONObject jsonMsg = JSONObject.parseObject(msg, Feature.DisableFieldSmartMatch);
		log.info("jsonMsg:" + jsonMsg);
		InitUser iUser = WebSocketServer.state.getUser(userId);
		if (jsonMsg.getString("modal").equals("oht")) {
			if (jsonMsg.getString("type").equals("message")) {
				OhtMsgTemplate ohtMsg = new OhtMsgTemplate();
				String roomName = WebSocketServer.state.getUserRoom(userId);
				// 获取room中的其他用户
				if (Util.isNullorEmpty(WebSocketServer.isRoomExist(roomName))) {
					return;
				}
				for (String revUserId : WebSocketServer.state.getRoomMembers(roomName)) {
					if (revUserId.equals(userId)) {
						ohtMsg.setDirection(1);
					} else {
						ohtMsg.setDirection(2);
					}

					InitUser revUser = WebSocketServer.state.getUser(revUserId);
					ohtMsg.setSendId(userId);
					ohtMsg.setSendName(iUser.getUser().getPloName());
					ohtMsg.setRevId(revUserId);
					ohtMsg.setRevName(revUser.getUser().getPloName());
					ohtMsg.setContent(jsonMsg.getString("content"));
					ohtMsg.setAvatar(jsonMsg.getString("avatar"));
					ohtMsg.setType(1);

					ohtMsg.setCtime(dateFormatThreadLocal.get().format(new Date()));
					WebSocketServer.ohtSendMessage(MsgTemplate.success(modal, type).add("data", ohtMsg), revUserId);
				}
				fileRecord.writeChatRecordFile(roomName,
						JSON.toJSONString(MsgTemplate.success(modal, type).add("data", ohtMsg)));

			} else if (jsonMsg.getString("type").equals("identity")) {
				int ohtFlag = 0;
				if (!Util.isNullorEmpty(jsonMsg.getString("content"))) {
					String[] identitys = jsonMsg.getString("content").split(",");
					ohtFlag = handle.statusTypeService.identityNameOhtFlag(identitys);
				}
				int userStatus = Integer.parseInt(jsonMsg.getString("userStatus"));
				Map<String, String> statusType = handle.statusTypeService.getUserStatusAndOhtFlag(userStatus);

				int isTakeOrder = ohtFlag * Integer.parseInt(statusType.get("ohtFlag"));
				iUser.setOhtStatus(isTakeOrder);
				// 解析出来判断是否可接单的身份，修改OTHSTATUS.
				iUser.setIdentity(jsonMsg.getString("content"));
				if (!shouldDebounceUpdate(userId)) {
					WebSocketServer.boardcastAsync(MsgTemplate.success("oht", "system", "update").add("data", iUser), null);
				}
				StatusJour record = new StatusJour();
				record.setBegTime(Util.getTime());
				record.setDataDate(Util.getDateToday());
				record.setIp(iUser.getStation().getPcIp());
				record.setUserId(userId);
				record.setStatusId(iUser.getUserStatus());
				record.setIdentity(jsonMsg.getString("content"));
				record.setOhtFlag(isTakeOrder);
				handle.statusJourService.insertNewStatusJour(record);
			} else if (jsonMsg.getString("type").equals("userStatus")) {
				JSONObject userStatus = jsonMsg.getJSONObject("content");
				int status = 0;
				String memo = "";
				if (!Util.isNullorEmpty(userStatus)) {
					status = Integer.parseInt(userStatus.getString("id"));
					memo = userStatus.getString("memo");
				}
				// 解析出来判断是否可接单的身份，修改OTHSTATUS
				iUser.setUserStatus(status);
				iUser.setStatusTime(Util.getCurrentTimestamp());
				Map<String, String> statusType = handle.statusTypeService.getUserStatusAndOhtFlag(status);
				iUser.setStatusName(statusType.get("level1") + "-" + statusType.get("level3"));
				// 此处需获取身份中是否存在不可接单，若为0则不可接单 无需再获取当前选择的身份，若为1则需要获取当前身份所对应是否可接单
				int ohtFlag = 1;
				if (!Util.isNullorEmpty(iUser.getIdentity())) {
					String[] identitys = iUser.getIdentity().split(",");
					ohtFlag = handle.statusTypeService.identityNameOhtFlag(identitys);
				}
				int isTakeOrder = ohtFlag * Integer.parseInt(statusType.get("ohtFlag"));
				iUser.setOhtStatus(isTakeOrder);

				if (!shouldDebounceUpdate(userId)) {
					WebSocketServer.boardcastAsync(MsgTemplate.success("oht", "system", "update").add("data", iUser), null);
				}
				StatusJour record = new StatusJour();
				record.setBegTime(Util.getTime());
				record.setDataDate(Util.getDateToday());
				record.setIp(iUser.getStation().getPcIp());
				record.setUserId(userId);
				record.setStatusId(status);
				record.setIdentity(iUser.getIdentity());
				record.setMemo(memo);
				record.setOhtFlag(isTakeOrder);
				handle.statusJourService.insertNewStatusJour(record);
			} else if (jsonMsg.getString("type").equals("command")) {
				JSONObject command = jsonMsg.getJSONObject("content");
				// 当前端用户操作新的求助，则需要告知选择求助类型
				String action = command.getString("action");
				if ("build".equals(action)) {
					Case ohtCase = handle.caseService.insertNewCase(userId, command.getInteger("caseType"),
							iUser.getStation().getExtnNum());
					WebSocketServer.state.setWaittingCase(handle.caseService.getHelpWaitCase());
					// 设置将用户接单状态修改为1-等待接单
					// User.setOrderStatus(1);
					// 返回提示当前已成功建立订单，提示前端变更状态
					WebSocketServer.sendMessage(MsgTemplate.success("oht", "command", "newCase").add("case", ohtCase),
							userId);
					// 将案件发送给所有在线业务主任，将提醒安排在客户端判断
					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.state.getWaittingCase()),
							null);
					fileRecord.writeChatRecordFile(ohtCase.getCaseId(),
							JSON.toJSONString(MsgTemplate.success("oht", "command", "newCase").add("case", ohtCase)));
					WebSocketServer.state.setWaittingCase(handle.caseService.getHelpWaitCase());
					// 将用户所在房间调换至CaseId的房间
					WebSocketServer.roomChange(userId, ohtCase.getCaseId());
					// 刷新求助者的未结案件，广播在线列表状态变更
					refreshUserCaseAndBroadcast(userId);
				} else if ("cancel".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = handle.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件取消失败，请确认此案件是否存在！"), userId);
						return;
					}
					if (ohtCase.getCaseStatus().equals(0)) {
						int bcompType = command.getInteger("bcompType");

						ohtCase.setBcompTime(Util.currentDateTime());
						ohtCase.setBcompMemo(command.getString("bcompMemo"));
						ohtCase.setBcompType(bcompType);
						ohtCase.setWaitTime(Integer.parseInt(Util.getCurrentTimestamp()) - ohtCase.getBuildTime());
						ohtCase.setCaseStatus(1);
						count = handle.caseService.updateCaseBySelective(ohtCase);
					}

					if (count > 0) {
						// 已经被接起的订单无需发送cancel，直接最后推送当前订单情况
						// WebSocketServer.boardcastToDirectors(MsgTemplate.success("oht", "command",
						// "cancel").add("case", ohtCase), null);
						// FIXME 20200911 对于已经推送TASK的数据需要将数据置为无法接起
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "command", "cancelSuccess"), userId);
						WebSocketServer.boardcastToDirectors(
								MsgTemplate.success("oht", "command", "cancel").add("caseId", ohtCase.getCaseId()),
								userId);
						fileRecord.writeChatRecordFile(ohtCase.getCaseId(),
								JSON.toJSONString(MsgTemplate.success("oht", "command", "cancelSuccess")));
					} else {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件取消失败，请确认此案件是否已经被接起！"),
								userId);
					}
					WebSocketServer.state.setWaittingCase(handle.caseService.getHelpWaitCase());
					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.state.getWaittingCase()),
							null);
					// 求助者取消案件，清除其未结案件缓存并广播
					refreshUserCaseAndBroadcast(userId);
				} else if ("takeOrder".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = handle.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件接起失败，请确认此案件是否存在！"), userId);
						return;
					}
					if (ohtCase.getCaseStatus().equals(0)) {
						ohtCase.setPickTime(Integer.parseInt(Util.getCurrentTimestamp()));
						ohtCase.setPickId(userId);
						ohtCase.setPickDept(iUser.getUser().getDeptNum());
						ohtCase.setPickGroup(iUser.getUser().getDeptGroup());
						ohtCase.setPickExtn(iUser.getStation().getExtnNum());
						ohtCase.setWaitTime(ohtCase.getPickTime() - ohtCase.getBuildTime());
						ohtCase.setCaseStatus(2);
						ohtCase.setBuildUser(DataCache.EMPLOYEE.get(ohtCase.getBuildId()));
						ohtCase.setPickUser(DataCache.EMPLOYEE.get(ohtCase.getPickId()));
						count = handle.caseService.updateCaseBySelective(ohtCase);
					}
					if (count > 0) {
						// 已经被接起的订单无需发送cancel，直接最后推送当前订单情况
						// WebSocketServer.boardcastToDirectors(MsgTemplate.success("oht", "command",
						// "cancel").add("case", ohtCase), userId);
						// 将TASK案件状态置为
						int tasksend = handle.caseTaskService.updateTaskCompleteByUserIdAndCaseId(userId,
								ohtCase.getCaseId());
						if (tasksend < 1) {
							handle.caseTaskService.insertNewTask(ohtCase.getCaseId(), userId, ohtCase.getBuildId(), 1);
						}
						// 将用户所在房间调换至CaseId的房间
						WebSocketServer.roomChange(userId, ohtCase.getCaseId());
						WebSocketServer.sendMessageByRoom(
								MsgTemplate.success("oht", "command", "linked").add("case", ohtCase),
								WebSocketServer.state.getUserRoom(userId), null);
						new ChatRecordFile().writeChatRecordFile(ohtCase.getCaseId(), JSON
								.toJSONString(MsgTemplate.success("oht", "command", "linked").add("case", ohtCase)));
						// 接单成功后刷新接单者和求助者的未结案件，广播在线列表状态变更
						refreshUserCaseAndBroadcast(userId);
						refreshUserCaseAndBroadcast(ohtCase.getBuildId());
					} else {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件接起失败，该案件已被接起或被用户取消！"),
								userId);
					}
					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "cancel").add("caseId", ohtCase.getCaseId()), userId);
					WebSocketServer.state.setWaittingCase(handle.caseService.getHelpWaitCase());
					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.state.getWaittingCase()),
							null);
				} else if ("pcompCase".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = handle.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "完成结案失败，请确认此案件是否存在！"), userId);
						return;
					}
					if (!ohtCase.getCaseStatus().equals(0)) {
						ohtCase.setPcompTime(Util.currentDateTime());
						ohtCase.setCustId(command.getString("custId"));
						ohtCase.setPcompType(command.getInteger("pcompType"));
						ohtCase.setPcompMemo(command.getString("pcompMemo"));
						ohtCase.setCaseStatus(3);
						count = handle.caseService.updateCaseBySelective(ohtCase);
					}
					if (count > 0) {
						// User.setOrderStatus(0);
						// 推送给发起人订单已接起，并将发起人状态设置为LINKED
						WebSocketServer.state.setWaittingCase(handle.caseService.getHelpWaitCase());
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "otherComplete")
								.add("case", WebSocketServer.state.getWaittingCase()), ohtCase.getCaseId(), userId);
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "mineComplete")
								.add("case", WebSocketServer.state.getWaittingCase()), ohtCase.getCaseId(), ohtCase.getBuildId());
					} else {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "完成结案失败，可能未找到该求助案件，请联系管理员！"),
								userId);
					}
					// 将用户所在房间调换至CaseId的房间
					WebSocketServer.roomChange(userId, "default");

					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.state.getWaittingCase()),
							null);
					// 结案后清除双方的未结案件缓存并广播
					refreshUserCaseAndBroadcast(userId);
					if (!userId.equals(ohtCase.getBuildId())) {
						refreshUserCaseAndBroadcast(ohtCase.getBuildId());
					}
					fileRecord.writeChatRecordFile(ohtCase.getCaseId(), JSON
							.toJSONString(MsgTemplate.success("oht", "command", "otherComplete").add("case", ohtCase)));
				} else if ("bcompCase".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = handle.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件结案失败，请确认此案件是否存在！"), userId);
						return;
					}
					if (!ohtCase.getCaseStatus().equals(0)) {
						ohtCase.setBcompTime(Util.currentDateTime());
						ohtCase.setBcompType(command.getInteger("bcompType"));
						ohtCase.setBcompMemo(command.getString("bcompMemo"));
						ohtCase.setCaseStatus(3);
						count = handle.caseService.updateCaseBySelective(ohtCase);
					}
					if (count > 0) {
						// User.setOrderStatus(0);
						// 推送给发起人订单已接起，并将发起人状态设置为LINKED
						WebSocketServer.state.setWaittingCase(handle.caseService.getHelpWaitCase());
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "otherComplete")
								.add("case", WebSocketServer.state.getWaittingCase()), ohtCase.getCaseId(), userId);
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "mineComplete")
								.add("case", WebSocketServer.state.getWaittingCase()), ohtCase.getCaseId(), ohtCase.getPickId());
						WebSocketServer.roomChange(userId, "default");
						// 结案后清除双方的未结案件缓存并广播
						refreshUserCaseAndBroadcast(userId);
						refreshUserCaseAndBroadcast(ohtCase.getPickId());
					} else {
						WebSocketServer.sendMessage(
								MsgTemplate.success("oht", "error", "完成结案失败，可能未找到该求助案件或案件状态不正确，请联系管理员！"), userId);
					}
					// WebSocketServer.boardcastToDirectors(MsgTemplate.success("oht", "command",
					// "help").add("cases", WebSocketServer.state.getWaittingCase()), null);
					new ChatRecordFile().writeChatRecordFile(ohtCase.getCaseId(), JSON
							.toJSONString(MsgTemplate.success("oht", "command", "otherComplete").add("case", ohtCase)));
				} else if ("newTask".equals(action)) {
					handle.caseTaskService.insertNewTask(command.getString("caseId"), userId,
							command.getString("buildId"), 0);
				} else if ("refuse".equals(action)) {
					int count = handle.caseTaskService.updateTaskRefuseByUserIdAndCaseId(userId,
							command.getString("caseId"), command.getString("memo"));

					if (count > 0) {
						List<Case> waittingCase = handle.caseService.getHelpWaitCase();
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "command", "refuseCase")
								.add("caseId", command.getString("caseId")).add("cases", waittingCase), userId);
					} else {
						WebSocketServer.sendMessage(
								MsgTemplate.success("oht", "error", "拒绝案件失败，可能未找到该求助案件或案件状态不正确，请联系管理员！"), userId);
						WebSocketServer.sendMessageAsync(MsgTemplate.success("oht", "command", "help").add("cases",
								WebSocketServer.state.getWaittingCase()), userId);
					}
				}
			}
		} else if (jsonMsg.getString("modal").equals("admin")) {
			// 管理员websocket推送管理
			if (jsonMsg.getString("type").equals("kickOut")) {
				if (Util.isNullorEmpty(jsonMsg.getString("content"))) {
					WebSocketServer.boardcastAsync(MsgTemplate.success("admin", "kickOut", ""), userId);
				} else {
					WebSocketServer.sendMessage(MsgTemplate.success("admin", "kickOut", ""),
							jsonMsg.getString("content"));
				}
			} else if (jsonMsg.getString("type").equals("message")) {
				JSONObject command = jsonMsg.getJSONObject("content");
				String toUserId = command.getString("toUserId");
				if (Util.isNullorEmpty(toUserId)) {
					WebSocketServer.boardcastAsync(MsgTemplate.success("admin", "message", command.getString("msg")),
							userId);
				} else {
					WebSocketServer.sendMessage(MsgTemplate.success("admin", "message", command.getString("msg")),
							toUserId);
				}
			}
		}
	}

	public String getRoom(String userId) {
		String roomName = WebSocketServer.state.getUserRoom(userId);
		if (Util.isNullorEmpty(roomName)) {
			roomName = "default";
		}
		return roomName;
	}
	private void debugMessage(String message) {
		System.out.println("===== 消息调试信息 =====");
		System.out.println("原始消息字符串: \"" + message + "\"");
		System.out.println("消息长度: " + message.length());

		// 打印每个字符的详细信息
		byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
		System.out.println("UTF-8字节数组: " + Arrays.toString(bytes));

		for (int i = 0; i < message.length(); i++) {
			char c = message.charAt(i);
			System.out.printf("位置 %2d: 字符='%s' (Unicode: U+%04X, int: %d, 是否为控制字符: %b)%n",
					i,
					(c >= 32 && c <= 126) ? String.valueOf(c) : "□",
					(int) c,
					(int) c,
					Character.isISOControl(c));
		}
		System.out.println("=======================");
	}

	public JSONObject messageHandle(String message) {
		log.info("ws:" + message);
		JSONObject jsonObject = null;
		try {
			// 1. 首先去除外层的引号
			String processed = message;
			// 如果整个字符串被引号包围，去掉外层引号
			if (processed.startsWith("\"") && processed.endsWith("\"")) {
				processed = processed.substring(1, processed.length() - 1);
			}
			// 2. 反转义内部的引号 (将 \" 替换为 ")
			processed = processed.replace("\\\"", "\"");

			// 3. 移除可能的其他转义字符
			processed = processed.replace("\\\\", "\\");

			// 4. 现在可以正常解析了
			jsonObject = JSON.parseObject(processed);

			// 业务处理
			String modal = jsonObject.getString("modal");
			String type = jsonObject.getString("type");
			JSONObject content = jsonObject.getJSONObject("content");

		} catch (Exception e) {
			log.error("解析失败", e);
		}
		return jsonObject;
	}
}
