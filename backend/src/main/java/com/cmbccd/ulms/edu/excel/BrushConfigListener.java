package com.cmbccd.ulms.edu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.DailyGroup;
import com.cmbccd.ulms.edu.service.DailyGroupService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BrushConfigListener extends AnalysisEventListener<Map<Integer, String>> {
		
	private static final int BATCH_COUNT = 128;
	List<DailyGroup> list = new ArrayList<DailyGroup>();
	List<BrushConfig> brushConfigList = new ArrayList<BrushConfig>();
	private DailyGroupService dailyGroupService;
	private Msg msg;
	
	private Integer ploNumIndex;
	private Integer typeIndex;


	public void invoke(Map<Integer, String> data, AnalysisContext context) {
        String ploNum = data.get(ploNumIndex);
        if( Util.isNullorEmpty(ploNum) ) return;
        Employee user = DataCache.getEmployees().get(ploNum);
        List<BrushConfig> groupData = brushConfigList.stream().filter(e -> e.getGroupName().equals(data.get(typeIndex))).collect(Collectors.toList());
        
    	DailyGroup record = new DailyGroup();
	    record.setPloNum(ploNum);

	    if(Util.isNullorEmpty(user)) {
	    	this.msg = Msg.error("工号无法识别！"+ ploNum);
	    	return;
	    }else {
	    	record.setPloName(user.getPloName());
	    }
        if(groupData.size() < 1) {
        	this.msg = Msg.error("没有获取到正确的分组名称！" + data.get(typeIndex));
        	return;
        } else {
        	record.setGroupId(groupData.get(0).getGroupId());
        }
	    list.add(record);
        if (list.size() >= BATCH_COUNT) {
        	dailyGroupService.batchInsert(list);
            list.clear();
        }
	}
    public BrushConfigListener(DailyGroupService dailyGroupService, List<BrushConfig> brushConfigList) {
        this.dailyGroupService = dailyGroupService;
        this.brushConfigList = brushConfigList;
        msg = new Msg();
    }
    
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    	headMap.forEach((key, value) -> {
    		if("员编".equals(value)) {
    			ploNumIndex = key;
    		}else if("分组".equals(value)) {
    			typeIndex = key;
    		}
    	});
    }
    
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
        // 审计修复：解析过程中有错误行（工号无法识别/分组不存在）时保留错误信息，
        // 不再无条件覆盖为成功；仅无错误时落库并返回成功
        Object code = msg.get("code");
        if (code != null && !Integer.valueOf(200).equals(code)) {
            return;
        }
        dailyGroupService.batchInsert(list);
		msg = Msg.success("所有数据解析完成！");
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

    public DailyGroupService getDailyGroupService() {
        return dailyGroupService;
    }

    public void setDailyGroupService(DailyGroupService gzdrService) {
        this.dailyGroupService = gzdrService;
    }
}
