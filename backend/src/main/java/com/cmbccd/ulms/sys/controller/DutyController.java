package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Duty;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.DutyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@RequestMapping("duty")
@RestController
public class DutyController {

	@Resource
	private DutyService dutyService;

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public Msg getCurentDutyByPloNum() {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取到用户信息！");
		}

		List<Duty> duty = dutyService.getDutyByPloNum(Util.getDateToday().substring(0, 7), userId);
		if (duty == null || duty.size() == 0) {
			return Msg.error("没有获取到班表信息！");
		}
		return Msg.success(duty);
	}

	@RequestMapping(value = "/my/{dateDate}", method = RequestMethod.GET)
	public Msg getDutyByPloNum(@PathVariable("dateDate") String dateDate) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取到用户信息！");
		}

		List<Duty> duty = dutyService.getDutyByPloNum(dateDate, userId);
		if (duty == null || duty.size() == 0) {
			return Msg.error("没有获取到班表信息！");
		}
		return Msg.success(duty);
	}

}
