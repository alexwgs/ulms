package com.cmbccd.ulms.edu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.QuesScore;
import com.cmbccd.ulms.edu.service.QuesScoreService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.Map;


public class QuesScoreListener extends AnalysisEventListener<Map<Integer, String>> {
		
//	private static final int BATCH_COUNT = 1;
	private int count=0;
	
	private QuesScoreService quesScoreService;
	private Msg msg;
	
	private Integer ploNumIndex;
	private Integer typeIndex;
	private String examCode;
	
	public void invoke(Map<Integer, String> data, AnalysisContext context) {
//        LOG.info("解析到一条数据：{}, currentRowHolder: {}", data.toString(), context.readRowHolder().getRowIndex());
        String ploNum = data.get(ploNumIndex);
        if( Util.isNullorEmpty(ploNum) ) return;
        Employee user = DataCache.getEmployees().get(ploNum);
	    if(Util.isNullorEmpty(user)) {
	    	this.msg = Msg.error("工号无法识别！"+ ploNum);
	    	return;
	    }
        QuesScore record = new QuesScore();
	    record.setPloNum(ploNum);
	    record.setExamCode(examCode);
	    record.setCompStat(0);
	    record.setUserScore(0.0);
	    record.setDeptNum(user.getDeptNum());
	    record.setDeptGroup(user.getDeptGroup());
	    
	    if(!Util.isNullorEmpty(data.get(typeIndex)) && "免考".equals(data.get(typeIndex))) {
	    	record.setCompStat(3);
	    	record.setUserScore(100.00);
	    }
       count +=  quesScoreService.create(record);

	}
    public QuesScoreListener(QuesScoreService quesScoreService, String examCode) {
        this.quesScoreService = quesScoreService;
        this.examCode = examCode;
        msg = new Msg();
    }
    
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    	headMap.forEach((key, value) -> {
    		if("员编".equals(value)) {
    			ploNumIndex = key;
    		}else if("考试类型".equals(value)) {
    			typeIndex = key;
    		}
    	});
    }
    
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
		msg = Msg.success("所有数据解析完成！成功导入【"+count+"】条数据！");
	}

	
    /**
     * 加上存储数据库
     */
//    private Msg saveData() {
//        LOG.info("{}条数据，开始存储数据库！,ztId:{}  ", list.size());
//        return Msg.success();
//    }
    
    public Msg getMessage() {
        return msg;
    }

    public QuesScoreService getQuesScoreService() {
        return quesScoreService;
    }

    public void setQuesScoreService(QuesScoreService quesScoreService) {
        this.quesScoreService = quesScoreService;
    }
}
