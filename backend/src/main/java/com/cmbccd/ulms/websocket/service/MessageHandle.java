package com.cmbccd.ulms.websocket.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import cn.dev33.satoken.stp.StpUtil;

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

	@Resource
	private ChatRecordFile chatRecordFile;

	/**
	 * 刷新用户的未结案件缓存并广播用户更新。
	 * 案件状态变更后调用，确保在线列表中的用户状态实时同步。
	 */
	private static void refreshUserCaseAndBroadcast(String userId) {
		InitUser user = WebSocketServer.getState().getUser(userId);
		if (user == null || user.getOhtRole() == null) return;

		Case unfinishCase;
		if (user.getOhtRole().getRoleType() == 0) {
			unfinishCase = handle.caseService.getUnfinishCaseByHelper(userId);
		} else {
			unfinishCase = handle.caseService.getUnfinishCaseByListener(userId);
		}
		if (unfinishCase != null) {
			if (unfinishCase.getBuildId() != null) {
				unfinishCase.setBuildUser(DataCache.getEmployees().get(unfinishCase.getBuildId()));
			}
			if (unfinishCase.getPickId() != null) {
				unfinishCase.setPickUser(DataCache.getEmployees().get(unfinishCase.getPickId()));
			}
		}
		user.setUnfinishCase(unfinishCase);
		WebSocketServer.getState().putUser(userId, user);
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
		InitUser iUser = WebSocketServer.getState().getUser(userId);
		if ("oht".equals(jsonMsg.getString("modal"))) {
			if ("message".equals(jsonMsg.getString("type"))) {
				OhtMsgTemplate ohtMsg = new OhtMsgTemplate();
				String roomName = WebSocketServer.getState().getUserRoom(userId);
				// 获取room中的其他用户
				if (Util.isNullorEmpty(WebSocketServer.isRoomExist(roomName))) {
					return;
				}
				for (String revUserId : WebSocketServer.getState().getRoomMembers(roomName)) {
					if (revUserId.equals(userId)) {
						ohtMsg.setDirection(1);
					} else {
						ohtMsg.setDirection(2);
					}

					InitUser revUser = WebSocketServer.getState().getUser(revUserId);
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
				chatRecordFile.writeChatRecordFile(roomName,
						JSON.toJSONString(MsgTemplate.success(modal, type).add("data", ohtMsg)));
				log.info("写聊天记录调用完成: room={}", roomName);

			} else if ("identity".equals(jsonMsg.getString("type"))) {
				int ohtFlag = 0;
				if (!Util.isNullorEmpty(jsonMsg.getString("content"))) {
					String[] identitys = jsonMsg.getString("content").split(",");
					ohtFlag = this.statusTypeService.identityNameOhtFlag(identitys);
				}
				int userStatus = Integer.parseInt(jsonMsg.getString("userStatus"));
				Map<String, String> statusType = this.statusTypeService.getUserStatusAndOhtFlag(userStatus);

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
				this.statusJourService.insertNewStatusJour(record);
			} else if ("userStatus".equals(jsonMsg.getString("type"))) {
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
				Map<String, String> statusType = this.statusTypeService.getUserStatusAndOhtFlag(status);
				iUser.setStatusName(statusType.get("level1") + "-" + statusType.get("level3"));
				// 此处需获取身份中是否存在不可接单，若为0则不可接单 无需再获取当前选择的身份，若为1则需要获取当前身份所对应是否可接单
				int ohtFlag = 1;
				if (!Util.isNullorEmpty(iUser.getIdentity())) {
					String[] identitys = iUser.getIdentity().split(",");
					ohtFlag = this.statusTypeService.identityNameOhtFlag(identitys);
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
				this.statusJourService.insertNewStatusJour(record);
			} else if ("command".equals(jsonMsg.getString("type"))) {
				JSONObject command = jsonMsg.getJSONObject("content");
				// 当前端用户操作新的求助，则需要告知选择求助类型
				String action = command.getString("action");
				if ("build".equals(action)) {
					Case ohtCase = this.caseService.insertNewCase(userId, command.getInteger("caseType"),
							iUser.getStation().getExtnNum());
					WebSocketServer.getState().setWaittingCase(this.caseService.getHelpWaitCase());
					// 设置将用户接单状态修改为1-等待接单
					// User.setOrderStatus(1);
					// 返回提示当前已成功建立订单，提示前端变更状态
					WebSocketServer.sendMessage(MsgTemplate.success("oht", "command", "newCase").add("case", ohtCase),
							userId);
					// 将案件发送给所有在线业务主任，将提醒安排在客户端判断
					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.getState().getWaittingCase()),
							null);
					chatRecordFile.writeChatRecordFile(ohtCase.getCaseId(),
							JSON.toJSONString(MsgTemplate.success("oht", "command", "newCase").add("case", ohtCase)));
					WebSocketServer.getState().setWaittingCase(this.caseService.getHelpWaitCase());
					// 将用户所在房间调换至CaseId的房间
					WebSocketServer.roomChange(userId, ohtCase.getCaseId());
					// 刷新求助者的未结案件，广播在线列表状态变更
					refreshUserCaseAndBroadcast(userId);
				} else if ("cancel".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = this.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件取消失败，请确认此案件是否存在！"), userId);
						return;
					}
					// 审计加固（IDOR）：只有求助者本人可以取消自己的案件
					if (!userId.equals(ohtCase.getBuildId())) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "您无权取消他人的求助案件！"), userId);
						return;
					}
					if (ohtCase.getCaseStatus().equals(0)) {
						int bcompType = command.getInteger("bcompType");

						ohtCase.setBcompTime(Util.currentDateTime());
						ohtCase.setBcompMemo(command.getString("bcompMemo"));
						ohtCase.setBcompType(bcompType);
						ohtCase.setWaitTime(Integer.parseInt(Util.getCurrentTimestamp()) - ohtCase.getBuildTime());
						ohtCase.setCaseStatus(1);
						count = this.caseService.updateCaseBySelective(ohtCase);
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
						chatRecordFile.writeChatRecordFile(ohtCase.getCaseId(),
								JSON.toJSONString(MsgTemplate.success("oht", "command", "cancelSuccess")));
					} else {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件取消失败，请确认此案件是否已经被接起！"),
								userId);
					}
					WebSocketServer.getState().setWaittingCase(this.caseService.getHelpWaitCase());
					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.getState().getWaittingCase()),
							null);
					// 求助者取消案件，清除其未结案件缓存并广播
					refreshUserCaseAndBroadcast(userId);
				} else if ("takeOrder".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = this.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件接起失败，请确认此案件是否存在！"), userId);
						return;
					}
					// 审计加固（IDOR）：仅业务主任或被派单用户可以接单
					boolean isDirector = WebSocketServer.getState().isDirector(userId);
					boolean hasTask = this.caseTaskService.selectTaskByCaseId(ohtCase.getCaseId()).stream()
							.anyMatch(t -> userId.equals(t.getUserId()));
					if (!isDirector && !hasTask) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "您无权接起该案件！"), userId);
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
						ohtCase.setBuildUser(DataCache.getEmployees().get(ohtCase.getBuildId()));
						ohtCase.setPickUser(DataCache.getEmployees().get(ohtCase.getPickId()));
						count = this.caseService.updateCaseBySelective(ohtCase);
					}
					if (count > 0) {
						// 已经被接起的订单无需发送cancel，直接最后推送当前订单情况
						// WebSocketServer.boardcastToDirectors(MsgTemplate.success("oht", "command",
						// "cancel").add("case", ohtCase), userId);
						// 将TASK案件状态置为
						int tasksend = this.caseTaskService.updateTaskCompleteByUserIdAndCaseId(userId,
								ohtCase.getCaseId());
						if (tasksend < 1) {
							this.caseTaskService.insertNewTask(ohtCase.getCaseId(), userId, ohtCase.getBuildId(), 1);
						}
						// 将用户所在房间调换至CaseId的房间
						WebSocketServer.roomChange(userId, ohtCase.getCaseId());
						WebSocketServer.sendMessageByRoom(
								MsgTemplate.success("oht", "command", "linked").add("case", ohtCase),
								WebSocketServer.getState().getUserRoom(userId), null);
						chatRecordFile.writeChatRecordFile(ohtCase.getCaseId(), JSON
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
					WebSocketServer.getState().setWaittingCase(this.caseService.getHelpWaitCase());
					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.getState().getWaittingCase()),
							null);
				} else if ("pcompCase".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = this.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "完成结案失败，请确认此案件是否存在！"), userId);
						return;
					}
					// 审计加固（IDOR）：仅求助者本人可以确认完成自己的案件
					if (!userId.equals(ohtCase.getBuildId())) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "您无权确认他人的案件！"), userId);
						return;
					}
					if (!ohtCase.getCaseStatus().equals(0)) {
						ohtCase.setPcompTime(Util.currentDateTime());
						ohtCase.setCustId(command.getString("custId"));
						ohtCase.setPcompType(command.getInteger("pcompType"));
						ohtCase.setPcompMemo(command.getString("pcompMemo"));
						ohtCase.setCaseStatus(3);
						count = this.caseService.updateCaseBySelective(ohtCase);
					}
					if (count > 0) {
						// User.setOrderStatus(0);
						// 推送给发起人订单已接起，并将发起人状态设置为LINKED
						WebSocketServer.getState().setWaittingCase(this.caseService.getHelpWaitCase());
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "otherComplete")
								.add("case", WebSocketServer.getState().getWaittingCase()), ohtCase.getCaseId(), userId);
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "mineComplete")
								.add("case", WebSocketServer.getState().getWaittingCase()), ohtCase.getCaseId(), ohtCase.getBuildId());
					} else {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "完成结案失败，可能未找到该求助案件，请联系管理员！"),
								userId);
					}
					// 将用户所在房间调换至CaseId的房间
					WebSocketServer.roomChange(userId, "default");

					WebSocketServer.boardcastToDirectors(
							MsgTemplate.success("oht", "command", "help").add("cases", WebSocketServer.getState().getWaittingCase()),
							null);
					// 结案后清除双方的未结案件缓存并广播
					refreshUserCaseAndBroadcast(userId);
					if (!userId.equals(ohtCase.getBuildId())) {
						refreshUserCaseAndBroadcast(ohtCase.getBuildId());
					}
					chatRecordFile.writeChatRecordFile(ohtCase.getCaseId(), JSON
							.toJSONString(MsgTemplate.success("oht", "command", "otherComplete").add("case", ohtCase)));
				} else if ("bcompCase".equals(action)) {
					// 需先判断当前此订单是否已经被接起，若接起，则返回取消失败数据
					int count = 0;
					Case ohtCase = this.caseService.getCaseByCaseId(command.getString("caseId"));
					if (ohtCase == null) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "案件结案失败，请确认此案件是否存在！"), userId);
						return;
					}
					// 审计加固（IDOR）：仅接单者本人可以完成自己接起的案件
					if (ohtCase.getPickId() == null || !userId.equals(ohtCase.getPickId())) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "您无权完成他人的案件！"), userId);
						return;
					}
					if (!ohtCase.getCaseStatus().equals(0)) {
						ohtCase.setBcompTime(Util.currentDateTime());
						ohtCase.setBcompType(command.getInteger("bcompType"));
						ohtCase.setBcompMemo(command.getString("bcompMemo"));
						ohtCase.setCaseStatus(3);
						count = this.caseService.updateCaseBySelective(ohtCase);
					}
					if (count > 0) {
						// User.setOrderStatus(0);
						// 推送给发起人订单已接起，并将发起人状态设置为LINKED
						WebSocketServer.getState().setWaittingCase(this.caseService.getHelpWaitCase());
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "otherComplete")
								.add("case", WebSocketServer.getState().getWaittingCase()), ohtCase.getCaseId(), userId);
						WebSocketServer.sendMessageByRoom(MsgTemplate.success("oht", "command", "mineComplete")
								.add("case", WebSocketServer.getState().getWaittingCase()), ohtCase.getCaseId(), ohtCase.getPickId());
						WebSocketServer.roomChange(userId, "default");
						// 结案后清除双方的未结案件缓存并广播
						refreshUserCaseAndBroadcast(userId);
						refreshUserCaseAndBroadcast(ohtCase.getPickId());
					} else {
						WebSocketServer.sendMessage(
								MsgTemplate.success("oht", "error", "完成结案失败，可能未找到该求助案件或案件状态不正确，请联系管理员！"), userId);
					}
					// WebSocketServer.boardcastToDirectors(MsgTemplate.success("oht", "command",
					// "help").add("cases", WebSocketServer.getState().getWaittingCase()), null);
					chatRecordFile.writeChatRecordFile(ohtCase.getCaseId(), JSON
							.toJSONString(MsgTemplate.success("oht", "command", "otherComplete").add("case", ohtCase)));
				} else if ("newTask".equals(action)) {
					// 审计加固（IDOR）：仅业务主任可以派单
					if (!WebSocketServer.getState().isDirector(userId)) {
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "error", "您无权派单！"), userId);
						return;
					}
					this.caseTaskService.insertNewTask(command.getString("caseId"), userId,
							command.getString("buildId"), 0);
				} else if ("refuse".equals(action)) {
					int count = this.caseTaskService.updateTaskRefuseByUserIdAndCaseId(userId,
							command.getString("caseId"), command.getString("memo"));

					if (count > 0) {
						List<Case> waittingCase = this.caseService.getHelpWaitCase();
						WebSocketServer.sendMessage(MsgTemplate.success("oht", "command", "refuseCase")
								.add("caseId", command.getString("caseId")).add("cases", waittingCase), userId);
					} else {
						WebSocketServer.sendMessage(
								MsgTemplate.success("oht", "error", "拒绝案件失败，可能未找到该求助案件或案件状态不正确，请联系管理员！"), userId);
						WebSocketServer.sendMessageAsync(MsgTemplate.success("oht", "command", "help").add("cases",
								WebSocketServer.getState().getWaittingCase()), userId);
					}
				}
			}
		} else if ("admin".equals(jsonMsg.getString("modal"))) {
			// 管理员websocket推送管理：校验发送者权限，防止任意用户踢人/广播
			List<String> permissions = StpUtil.getPermissionList(userId);
			if (permissions == null || !permissions.contains("sys:ws:admin")) {
				log.warn("WebSocket admin 操作被拒绝：用户 {} 无 sys:ws:admin 权限", userId);
				return;
			}
			if ("kickOut".equals(jsonMsg.getString("type"))) {
				if (Util.isNullorEmpty(jsonMsg.getString("content"))) {
					WebSocketServer.boardcastAsync(MsgTemplate.success("admin", "kickOut", ""), userId);
				} else {
					WebSocketServer.sendMessage(MsgTemplate.success("admin", "kickOut", ""),
							jsonMsg.getString("content"));
				}
			} else if ("message".equals(jsonMsg.getString("type"))) {
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
		String roomName = WebSocketServer.getState().getUserRoom(userId);
		if (Util.isNullorEmpty(roomName)) {
			roomName = "default";
		}
		return roomName;
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
