package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.AgentMapper;
import com.cmbccd.ulms.sys.domain.Agent;
import com.cmbccd.ulms.sys.domain.AgentExample;
import com.cmbccd.ulms.sys.domain.AgentExample.Criteria;
import com.cmbccd.ulms.sys.service.AgentService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月28日 下午6:59:43
 * @Version 1.0
 * @Description:
 */
@Service
public class AgentServiceImpl implements AgentService {

	@Resource
	private AgentMapper agentMapper;

	@Override
	public String getCurrentAgentNum(String ploNum) {
		String agentNum = "";
		AgentExample example = new AgentExample();
		Criteria criteria = example.createCriteria();
		criteria.andPloNumEqualTo(ploNum);
		// 用户AGENT生效中
		criteria.andAgentStatusEqualTo("1");
		List<Agent> agent = agentMapper.selectByExample(example);
		if (agent.size() == 0) {
			agentNum = "";
		} else {
			agentNum = agent.get(0).getAgentNum();
		}
		return agentNum;
	}

	@Override
	public String getPloNumByAgent(String agentNum) {
		String ploNum = "";
		AgentExample example = new AgentExample();
		Criteria criteria = example.createCriteria();
		criteria.andAgentNumEqualTo(agentNum);
		// 用户AGENT生效中
		criteria.andAgentStatusEqualTo("1");
		List<Agent> agent = agentMapper.selectByExample(example);
		if (agent.size() == 0) {
			ploNum = "";
		} else {
			ploNum = agent.get(0).getPloNum();
		}
		return ploNum;
	}

}
