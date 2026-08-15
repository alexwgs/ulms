package com.cmbccd.ulms.college.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cmbccd.ulms.college.domain.TeachGroup;
import com.cmbccd.ulms.college.service.TeachGroupService;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TeachGroupListener extends AnalysisEventListener<Map<Integer, String>> {


    private static final int BATCH_COUNT = 10;
    List<TeachGroup> list = new ArrayList<TeachGroup>();
    private TeachGroupService teachGroupService;
    private Msg msg;

    private Integer ploNumIndex;
    private Integer typeIndex;

    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        String ploNum = data.get(ploNumIndex);
        String groupName = data.get(typeIndex);
        if( Util.isNullorEmpty(ploNum) ) return;
        Employee user = DataCache.getEmployees().get(ploNum);

        TeachGroup record = new TeachGroup();
        record.setPloNum(ploNum);

        if(Util.isNullorEmpty(user)) {
            this.msg = Msg.error("工号无法识别！"+ ploNum);
            return;
        }
        if(Util.isNullorEmpty(groupName)) {
            this.msg = Msg.error("没有获取到正确的分组名称！" + data.get(typeIndex));
            return;
        } else {
            record.setGroupName(groupName);
        }
        list.add(record);
        if (list.size() >= BATCH_COUNT) {
            teachGroupService.batchInsert(list);
            list.clear();
        }
    }
    public TeachGroupListener(TeachGroupService teachGroupService) {
        this.teachGroupService = teachGroupService;
        msg = new Msg();
    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headMap.forEach((key, value) -> {
            if("员编".equals(value)) {
                ploNumIndex = key;
            }else if("授课对象".equals(value)) {
                typeIndex = key;
            }
        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        teachGroupService.batchInsert(list);
        msg = Msg.success("所有数据解析完成！");
    }
    public Msg getMessage() {
        return msg;
    }

    public TeachGroupService getTeachGroupService() {
        return teachGroupService;
    }

    public void setTeachGroupService(TeachGroupService teachGroupService) {
        this.teachGroupService = teachGroupService;
    }

}
