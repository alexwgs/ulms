package com.cmbccd.ulms.websocket.controller;

import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.Case;
import com.cmbccd.ulms.oht.domain.InitUser;
import com.cmbccd.ulms.oht.domain.RoleList;
import com.cmbccd.ulms.oht.domain.StatusJour;
import com.cmbccd.ulms.oht.service.CaseService;
import com.cmbccd.ulms.oht.service.CaseTaskService;
import com.cmbccd.ulms.oht.service.RoleListService;
import com.cmbccd.ulms.oht.service.StatusJourService;
import com.cmbccd.ulms.sys.domain.Station;
import com.cmbccd.ulms.sys.service.StationService;
import com.cmbccd.ulms.websocket.service.WebSocketServer;

/**
 * 
* <p>Title: ConnectInitController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Component
public class ConnectInitController {

	private static final Logger LOG = LoggerFactory.getLogger(ConnectInitController.class);

	@Resource
	private RoleListService roleListService;

	@Resource
	private CaseService caseService;

	@Resource
	private CaseTaskService caseTaskService;

	@Resource
	private StationService stationService;

	@Resource
	private StatusJourService statusJourService;

	/**
	 * 静态初始化类
	 */
	public static ConnectInitController staticInit;
	
	private static final String DIRECTOR_NUM = "101";

	@PostConstruct
	public void init() {
		staticInit = this;
	}

	public InitUser initUser(String userId, String ip) {

		InitUser user = new InitUser();
		user.setUserId(userId);
		// 设置默认接单状态 0-空闲 （1-求助等待中 2-处理中 3-待结案）
		user.setOhtStatus(1);

		user.setUser(DataCache.getEmployees().get(userId));
				
		List<RoleList> roleList = this.roleListService.getUserRoleListByUserId(userId, 1);


		RoleList ohtRoles = new RoleList();
		if (roleList.size() > 0) {
			ohtRoles = roleList.get(0);
		}

		user.setOhtRole(ohtRoles);

		// 默认为空闲状态
//		user.setOrderStatus(0);
		// 如果用户是金普卡业务主任则需要初始化当前状态 及 当日是否有身份！
		if (DIRECTOR_NUM.equals(user.getUser().getJobLevel())) {
			WebSocketServer.getState().addDirector(userId);
			StatusJour statusJour = this.statusJourService.getUserLatestStatusJour(Util.getDateToday(), userId);
			if (Util.isNullorEmpty(statusJour)) {
				user.setIdentity("");
				user.setUserStatus(0);
				user.setOhtStatus(0);
			} else {
				user.setIdentity(statusJour.getIdentity());
//				int ohtFlag = 0;
//				if(Util.isNullorEmpty(statusJour.getIdentity())) ohtFlag = 1;
//				else {
//					String [] identitys = statusJour.getIdentity().split(",");
//					ohtFlag = this.statusTypeService.identityNameOhtFlag(identitys);
//				}
//				// user.setUserStatus(statusJour.getStatusId());
//				// 目前若当日已有状态选择，则刷新后重置状态为 准备中
				user.setOhtStatus(0);
				user.setUserStatus(0);
			}
			user.setStatusTime(Util.getCurrentTimestamp());
			// 相当于首次登陆或者重新登录
			StatusJour record = new StatusJour();
			record.setBegTime(Util.getTime());
			record.setDataDate(Util.getDateToday());
			record.setIp(ip);
			record.setUserId(userId);
			record.setStatusId(user.getUserStatus());
			record.setIdentity(user.getIdentity());
			record.setOhtFlag(0);
			this.statusJourService.insertNewStatusJour(record);
			Map<String, Integer> taskCaseNum = this.caseTaskService.selectTsakCountTodayByUserId(userId);
			// 初始化今日举手案件数据
			user.add("taskCaseNum", taskCaseNum);
		} else {
			// 若无需状态的均设置为-1
			user.setIdentity("-1");
			user.setUserStatus(-1);
		}

		// 确认当前是否有未结案案件。
		Case unfinishCase = new Case();
		if (!Util.isNullorEmpty(ohtRoles.getRoleType())) {
			if (ohtRoles.getRoleType() == 0) {
				unfinishCase = this.caseService.getUnfinishCaseByHelper(userId);
			} else if (ohtRoles.getRoleType() == 1) {
				unfinishCase = this.caseService.getUnfinishCaseByListener(userId);
			}
			if (!Util.isNullorEmpty(unfinishCase)) {
				if (unfinishCase.getBuildId() != null) {
					unfinishCase.setBuildUser(DataCache.getEmployees().get(unfinishCase.getBuildId()));
				}
				if (unfinishCase.getPickId() != null) {
					unfinishCase.setPickUser(DataCache.getEmployees().get(unfinishCase.getPickId()));
				}
				user.setUnfinishCase(unfinishCase);
			}
		}
		Station station = new Station();
		if (!Util.isNullorEmpty(ip)) {
			Station s = this.stationService.selectByIpNoMemo(ip);
			if (Util.isNullorEmpty(s)) {
				station.setExtnIp("");
				station.setExtnNum("");
				station.setFloorNum("");
				station.setPcIp(ip);
			} else {
				station = s;
			}
		}
		user.setStation(station);
		return user;
	}

	public void logOff(InitUser user) {
		if (Util.isNullorEmpty(user) || Util.isNullorEmpty(user.getUser())) {
			return;
		}
		if (DIRECTOR_NUM.equals(user.getUser().getJobLevel())) {
			this.statusJourService.updateLastStatusJour(user.getUserId(), Util.getTime());
		} else {
			// 需要判定当前是否存已发起的求助但尚未结案的求助，若有则需要将案件置为异常断线，切广播该案件删除。
			int count = this.caseService.updateDisconnectCaseStatus(user.getUserId());
//			if (count > 0) {
//				List<Case> waittingCase = this.caseService.getHelpWaitCase();
//				WebSocketServer.boardcastToDirectors(
//						MsgTemplate.success("oht", "command", "help").add("cases", waittingCase), null);
//			}
			LOG.info("用户求助未接起且未取消登出系统！取消{}件案件！",count);
		}
	}
}
