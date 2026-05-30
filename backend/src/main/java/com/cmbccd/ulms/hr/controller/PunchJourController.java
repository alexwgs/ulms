package com.cmbccd.ulms.hr.controller;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.domain.PunchJour;
import com.cmbccd.ulms.hr.domain.PunchJourExample;
import com.cmbccd.ulms.hr.domain.PunchJourExample.Criteria;
import com.cmbccd.ulms.hr.service.PunchJourService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hr/punch/jour")
public class PunchJourController {

	@Resource
	private PunchJourService puchJourService;
	
	@GetMapping("/list")
	public Msg listUserPunchJour(@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("登录信息失败！");
		}
		String begDate = params.get("begDate");
		String endDate = params.get("endDate");
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PunchJourExample example = new PunchJourExample();
		if(Util.isNullorEmpty(begDate) || Util.isNullorEmpty(endDate)) {
			endDate = Util.getDateToday();
			begDate = Util.getPastDate(3);
		}
		Criteria criteria = example.createCriteria();
		criteria.andWorkDateBetween(begDate, endDate);
		criteria.andPloNumEqualTo(userId);
		if (Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(" work_date desc,pun_status ");
		}else {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}

		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<PunchJour> list = puchJourService.list(example);
		return Msg.success(new DataPage<PunchJour>(list));
	}
}
