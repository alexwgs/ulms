package com.cmbccd.ulms.flow.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FlowGqsqListener extends AnalysisEventListener<Map<Integer, String>> {

    private Msg msg;
//    List<FlowGqsq> list = new ArrayList<FlowGqsq>();

    List<Map<String, String>> list = new ArrayList<>();
    private Integer ploNumIndex;
    private Integer ploNameIndex;
    private Integer dataDateIndex;
    private Integer begTimeIndex;
    private Integer endTimeIndex;
    private Integer excTimeIndex;
    private Integer auxReasonIndex;
    private Integer auxMemoIndex;

    // 定义日期和时间格式
    private static final ThreadLocal<SimpleDateFormat> INPUT_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy/M/d"));
    private static final ThreadLocal<SimpleDateFormat> OUTPUT_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    private static final ThreadLocal<SimpleDateFormat> TIME_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm"));

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        String ploNum = data.get(ploNumIndex);
        String ploName = data.get(ploNameIndex);
        String dataDateStr = data.get(dataDateIndex);
        String begTimeStr = data.get(begTimeIndex);
        String endTimeStr = data.get(endTimeIndex);
        String excTime = data.get(excTimeIndex);
        String auxReason = data.get(auxReasonIndex);
        String auxMemo = data.get(auxMemoIndex);

        if(ploNum == null || ploNum.equals("")) {
            this.msg = Msg.error("员工编号不能为空！");
            return;
        }
        Employee user = DataCache.EMPLOYEE.get(ploNum);
        if(Util.isNullorEmpty(user)) {
            this.msg = Msg.error("没有查询到此用户！");
            return;
        }
        if(!user.getPloName().equals(ploName)) {
            this.msg = Msg.error("员工编号与姓名不匹配！");
            return;
        }

        // 获取单元名称
        Dictionary dict = DataCache.Dict.stream().filter(item -> "aux_reason".equals(item.getName()) && item.getCodeval().equals(auxReason)).findFirst().get();
        if(Util.isNullorEmpty(dict)) {
            this.msg = Msg.error("公勤原因不正确，请按指定名称填写！");
            return;
        }
        Date dataDate = null;
        Date begTime = null;
        Date endTime = null;
        String avgTime = "";
        try {
            dataDate = INPUT_DATE_FORMAT.get().parse(dataDateStr);
            begTime = new Date(TIME_FORMAT.get().parse(begTimeStr).getTime() + 60000*6);
            endTime = new Date(TIME_FORMAT.get().parse(endTimeStr).getTime() + 60000*6);
            int excTimeInt = new BigDecimal(excTime).intValue();

            // 计算时间差（以毫秒为单位）
            int timeDifferenceInMinite =(int) ((endTime.getTime() - begTime.getTime())/ 60000);
            avgTime = (timeDifferenceInMinite - excTimeInt)+"";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String formattedDataDate = OUTPUT_DATE_FORMAT.get().format(dataDate);
        String formattedBegTime = TIME_FORMAT.get().format(begTime).replace(":","");
        String formattedEndTime = TIME_FORMAT.get().format(endTime).replace(":","");

        Map<String, String> record = new HashMap<>();
        record.put("ploNum", ploNum);
        record.put("ploName", user.getPloName());
        record.put("deptName",user.getDeptName());
        record.put("groupName",user.getGroupName());
        record.put("dataDate", formattedDataDate);
        record.put("begTime", formattedBegTime);
        record.put("endTime", formattedEndTime);
        record.put("excTime", excTime);
        record.put("avgTime", avgTime);
        record.put("auxReason", dict.getCode());
        record.put("auxMemo", auxMemo);

        list.add(record);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headMap.forEach((key, value) -> {
            if(Util.isNullorEmpty(value)) return;
            switch (value) {
                case "员编":
                    ploNumIndex = key;
                    break;
                case "姓名":
                    ploNameIndex = key;
                    break;
                case "公勤日期":
                    dataDateIndex = key;
                    break;
                case "开始时间":
                    begTimeIndex = key;
                    break;
                case "结束时间":
                    endTimeIndex = key;
                    break;
                case "扣除时间":
                    excTimeIndex = key;
                    break;
                case "公勤原因":
                    auxReasonIndex = key;
                    break;
                case "备注":
                    auxMemoIndex = key;
                    break;
                default:
                    break;
            }
        });
    }

    public List<Map<String, String>> getList() {
        return list;
    }

}
