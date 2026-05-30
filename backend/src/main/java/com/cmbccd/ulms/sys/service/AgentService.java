package com.cmbccd.ulms.sys.service;

/** 
* @Author WeiGenSheng
* @Time 2020年4月28日 下午6:59:26 
* @Version 1.0
* @Description:
*/
public interface AgentService {
	public String getCurrentAgentNum(String ploNum);
	
	public String getPloNumByAgent(String agentNum);
}
