package com.cmbccd.ulms.flow.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.domain.PerfUnit;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.Msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PerfUnitListener extends AnalysisEventListener<Map<Integer, String>> {
    private Msg msg;
    List<PerfUnit> list = new ArrayList<PerfUnit>();

    private Integer ploNumIndex;
    private Integer ploNameIndex;
    private Integer perfUnitIndex;
    private Integer ifPerfIndex;
    private Integer begMonthIndex;
    private Integer endMonthIndex;
    private Integer memoIndex;

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        String ploNum = data.get(ploNumIndex);
        String ploName = data.get(ploNameIndex);
        String perfUnit = data.get(perfUnitIndex);
        String ifPerf = data.get(ifPerfIndex);
        String begMonth = data.get(begMonthIndex);
        String endMonth = data.get(endMonthIndex);
        String memo = data.get(memoIndex);

        if(ploNum == null || ploNum.equals("")) {
            this.msg = Msg.error("员工编号不能为空！");
            return;
        }
        if(!DataCache.EMPLOYEE.get(ploNum).getPloName().equals(ploName)) {
            this.msg = Msg.error("员工编号与姓名不匹配！");
            return;
        }
        // 获取单元名称
        Dictionary dict = DataCache.Dict.stream().filter(item -> "bpms_perf_unit".equals(item.getName()) && item.getCodeval().equals(perfUnit)).findFirst().get();
        if(Util.isNullorEmpty(dict)) {
            this.msg = Msg.error("考核单元名称不正确！");
            return;
        }

        PerfUnit record = new PerfUnit();
        record.setPloNum(ploNum);
        record.setPerfUnit(Integer.parseInt(dict.getCode()));
        record.setIfPerf(Short.parseShort(ifPerf));
        record.setBegMonth(begMonth);
        record.setEndMonth(endMonth);
        record.setMemo(memo);
        list.add(record);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headMap.forEach((key, value) -> {
            switch (value) {
                case "员编":
                    ploNumIndex = key;
                    break;
                case "姓名":
                    ploNameIndex = key;
                    break;
                case "考核单元":
                    perfUnitIndex = key;
                    break;
                case "是否考核":
                    ifPerfIndex = key;
                    break;
                case "开始月份":
                    begMonthIndex = key;
                    break;
                case "结束月份":
                    endMonthIndex = key;
                    break;
                case "备注":
                    memoIndex = key;
                    break;
                default:
                    break;
            }
        });
    }

}
