package com.cmbccd.ulms.flow.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.flow.domain.FlowInfo;
import com.cmbccd.ulms.flow.excel.FlowCaseExcel;
import com.cmbccd.ulms.flow.service.FlowCaseService;
import com.cmbccd.ulms.flow.service.FlowInfoService;
import com.cmbccd.ulms.flow.service.FlowReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/flow/report")
public class FlowReportController {
    @Resource
    private FlowReportService flowReportService;

    @Resource
    private FlowCaseService flowCaseService;

    @Resource
    private FlowInfoService flowInfoService;

    @GetMapping("")
    public void report(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String flowId = params.get("flowId");
        FlowInfo flowInfo = flowInfoService.get(flowId);
        String fileName = flowInfo.getFlowName() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" +  URLEncoder.encode(fileName,"utf-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        List<FlowCaseExcel> flowCaseExcels = flowCaseService.report(params);
        List<String> ids = java.util.Arrays.asList(flowCaseExcels.stream().map(FlowCaseExcel::getId).toArray(String[]::new));
        List<List<Object>> detailResult = flowReportService.reportFlowData(flowInfo, ids);
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        WriteSheet mainSheet = EasyExcel.writerSheet(0, "电子流列表").head(FlowCaseExcel.class).registerWriteHandler(horizontalCellStyleStrategy).build();
        excelWriter.write(flowCaseExcels,  mainSheet);
        WriteSheet detailSheet = EasyExcel.writerSheet(1, "明细表").needHead(Boolean.FALSE).build();
        WriteTable writeTable = EasyExcel.writerTable(0).needHead(Boolean.TRUE).registerWriteHandler(horizontalCellStyleStrategy).build();
        excelWriter.write(detailResult,  detailSheet, writeTable);
        excelWriter.finish();
    }
}
