package com.cmbccd.ulms.edu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.QuesBankMapper;
import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.domain.QuesBankExample;
import com.cmbccd.ulms.edu.domain.QuesBankExample.Criteria;
import com.cmbccd.ulms.edu.service.QuesBankService;
import com.cmbccd.ulms.sys.service.PublicService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class QuesBankServiceImpl implements QuesBankService{

	private static final Set<String> ALLOWED_ORDER_COLUMNS = new HashSet<>(Arrays.asList(
		"quesCode", "quesStem", "category", "handleDate", "libCode", "quesStat"
	));

	@Resource
	private QuesBankMapper quesBankMapper;

	@Resource
	private PublicService publicService;

	@Override
	public List<QuesBank> list(Map<String, String> params, Boolean isPageHelper) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		String libCode = params.get("libCode");
		String quesStat = params.get("quesStat");
		String query = params.get("query");
		String querytype = params.get("querytype");
		QuesBankExample example = new QuesBankExample();

		if (!Util.isNullorEmpty(params.get("order")) && ALLOWED_ORDER_COLUMNS.contains(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(libCode)) criteria.andLibCodeEqualTo(libCode);

		if (!Util.isNullorEmpty(quesStat)) {
			criteria.andQuesStatEqualTo(Integer.parseInt(quesStat));
		}
		// 确认是否有查询全部列表的权限
		if(!StpUtil.hasPermission("edu:question:list")) {
			criteria.andHandlePloEqualTo(Util.userIdByShiro());
		}
		if (!Util.isNullorEmpty(query)) {
			if (querytype.equals("quesStem"))
				criteria.andQuesStemLike("%" + query + "%");
			else if (querytype.equals("quesCode"))
				criteria.andQuesCodeEqualTo(query);
			else if (querytype.equals("category"))
				criteria.andCategoryLike("%" + query + "%");
		}
		if(isPageHelper)	PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<QuesBank> list = quesBankMapper.selectByExample(example);
		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateByQuesCodeWithNew(QuesBank record) {
		String userId = Util.userIdByShiro();

		// 先获取原始题目
		QuesBank oQuesBank = this.get(record.getQuesCode());
		//	确认本次更新题目是否涉及到题目的题干及选项，若涉及则需新建一题，若不涉及直接更新
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		oQuesBank.setHandlePlo(userId);
		oQuesBank.setHandleDate(Util.currentDateTime());
		if(oQuesBank.getQuesStem().equals(record.getQuesStem()) && oQuesBank.getAnswer().equals(record.getAnswer())) {
			oQuesBank = record;
		} else {
			oQuesBank.setQuesCode(record.getQuesCode());
			oQuesBank.setQuesStat(0);
			record.setQuesCode(publicService.getJourno());
			this.create(record);
		}
		int count = quesBankMapper.updateByPrimaryKey(oQuesBank);
		this.updateQuesNumber();
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteByQuesCode(String quesCode) {
		int count = quesBankMapper.deleteByPrimaryKey(quesCode);
		this.updateQuesNumber();
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int create(QuesBank record) {
		String userId = Util.userIdByShiro();
		record.setQuesCode(publicService.getJourno());
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		int count = quesBankMapper.insertSelective(record);
		this.updateQuesNumber();
		return count;
	}

	@Override
	public List<String> getQuestion(List<String> libCodes, String userId) {
		Map<String, Object> libsAndPloNum =new HashMap<String, Object>();
		libsAndPloNum.put("libCodes", libCodes);
		libsAndPloNum.put("ploNum", userId);
		return quesBankMapper.selectQuestionByLibs(libsAndPloNum);
	}

	@Override
	public QuesBank get(String quesCode) {
		return quesBankMapper.selectByPrimaryKey(quesCode);
	}

	@Override
	public QuesBank getNoSensitive(String quesCode) {
		QuesBankExample example = new QuesBankExample();
		Criteria criteria = example.createCriteria();
		criteria.andQuesCodeEqualTo(quesCode);
		List<QuesBank> list = quesBankMapper.selectNoSensitive(example);
		if(list.size()<1) return null;
		return list.get(0);
	}

	@Override
	public List<QuesBank> listNoSensitive(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		String libCode = params.get("libCode");
		String quesStat = params.get("quesStat");
		String query = params.get("query");
		String querytype = params.get("querytype");
		QuesBankExample example = new QuesBankExample();

		if (!Util.isNullorEmpty(params.get("order")) && ALLOWED_ORDER_COLUMNS.contains(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(libCode)) criteria.andLibCodeEqualTo(libCode);

		if (!Util.isNullorEmpty(quesStat)) {
			criteria.andQuesStatEqualTo(Integer.parseInt(quesStat));
		}
		if (!Util.isNullorEmpty(query)) {
			if (querytype.equals("quesStem"))
				criteria.andQuesStemLike("%" + query + "%");
			else if (querytype.equals("quesCode"))
				criteria.andQuesCodeEqualTo(query);
			else if (querytype.equals("category"))
				criteria.andCategoryLike("%" + query + "%");
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		return quesBankMapper.selectNoSensitive(example);
	}

	@Override
	public int checkUserAnswer(QuesBank question, String userAnswer) {
		if(Util.isNullorEmpty(userAnswer)) return 0;
		Integer passFlag = 1;
		if(question.getQuesType() == 2) { // 多选的情况
			String [] uAnswers = userAnswer.split(",");
			List<String> answers = Arrays.asList(question.getAnswer().split(","));
			if(uAnswers.length == answers.size()) {
				for(String uAnswer : uAnswers) {
					if(!answers.contains(uAnswer))  return 0;
				}
			}else {
				passFlag = 0;
			}
		}else { // 剩余单选的情况
			passFlag = question.getAnswer().equals(userAnswer)?1:0;
		}
		return passFlag;
	}

	@Override
	public int update(QuesBank record) {
		int count = quesBankMapper.updateByPrimaryKey(record);
		this.updateQuesNumber();
		return count;
	}

	public int updateQuesNumber() {
		return quesBankMapper.updateQuesNumber();
	}

	@Override
	public int invalidAllQuestionByQuesLib(String libCode) {
		String userId = Util.userIdByShiro();
		return quesBankMapper.invalidAllQuestionByQuesLib(userId, libCode);
	}

	@Override
	public int updateBySelective(QuesBank record) {
		int count = quesBankMapper.updateByPrimaryKeySelective(record);
		this.updateQuesNumber();
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int transferQuestions(String libCode, String[] questCodes) {
		if (questCodes == null || questCodes.length < 1) {
			return 0;
		}
		for (String quesCode : questCodes) {
			QuesBank record = new QuesBank();
			record.setQuesCode(quesCode);
			record.setLibCode(libCode);
			this.updateBySelective(record);
		}
		return questCodes.length;
	}
}

