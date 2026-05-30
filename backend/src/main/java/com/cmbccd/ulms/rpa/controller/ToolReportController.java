package com.cmbccd.ulms.rpa.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.rpa.domain.ToolReport;
import com.cmbccd.ulms.rpa.domain.ToolReportVO;
import com.cmbccd.ulms.rpa.service.ToolReportService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/rpa/report")
public class ToolReportController {

    private final ToolReportService toolReportService;

    public ToolReportController(ToolReportService toolReportService) {
        this.toolReportService = toolReportService;
    }

    @GetMapping("/list")
    @SaCheckPermission("rpa:report:list")
    @MyLog(title = "[rpa-report]RPA日志", content = "日志列表")
    public Msg getReportList(@RequestParam Map<String, String> params) {
        List<ToolReport> list = toolReportService.list(params);
        return Msg.success(new DataPage<>(list));
    }

    @PostMapping("/add")
    public Msg addReport(@RequestBody ToolReport toolReport) {
        return toolReportService.add(toolReport) > 0 ? Msg.success() : Msg.error();
    }

    @GetMapping("/cd/{toolId}")
    public Msg getToolCoolDown(@PathVariable String toolId) throws ParseException {
        Long second = toolReportService.getToolCoolDown(toolId);
        return Msg.success(second);
    }

    @GetMapping("/runlist")
    @SaCheckPermission("rpa:report:download")
    @MyLog(title = "[rpa-report]RPA日志", content = "报表下载")
    public void reportCourseLib(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<ToolReportVO> list = toolReportService.download(params);
        String fileName = "RPA工具使用情况报表";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        EasyExcel.write(response.getOutputStream(), ToolReportVO.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("使用记录").doWrite(list);
    }

    @GetMapping("/chart")
    public Msg getReportChart(@RequestParam Map<String, String> params) {
        Map<String,Map<String, Object>> chart = toolReportService.chart(params);
        return Msg.success(chart);
    }


}
