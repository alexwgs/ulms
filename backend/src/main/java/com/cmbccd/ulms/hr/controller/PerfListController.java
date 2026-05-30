package com.cmbccd.ulms.hr.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.domain.PerfList;
import com.cmbccd.ulms.hr.service.PerfListService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hr/perf_list")
public class PerfListController {

    @Resource
    private PerfListService perfListService;

    @GetMapping("/init/{perfMonth}")
    @MyLog(title = "[perf-list]人事名单", content = "初始化名单")
    public Msg init(@PathVariable String perfMonth) {
        perfListService.delete(perfMonth);
        int result = perfListService.insertInit(perfMonth);
        return result > 0 ? Msg.success() : Msg.error();
    }

    @GetMapping("/update/{perfMonth}")
    @MyLog(title = "[perf-list]人事名单", content = "更新名单")
    public Msg updateListInfo(@PathVariable String perfMonth) {
        int result = perfListService.updatePerfUnit(perfMonth);
        return result > 0 ? Msg.success() : Msg.error();
    }

    @GetMapping("/list")
    public Msg list(@RequestParam Map<String, String> params) {
        List<PerfList> list = perfListService.list(params);
        return Msg.success(new DataPage<PerfList>(list));
    }

    @GetMapping("/team/{perfMonth}")
    public Msg getMyTeam(@PathVariable ("perfMonth") String perfMonth) {
        if (Util.isNullorEmpty(perfMonth)) perfMonth = Util.getDateToday().substring(0,7);
        List<PerfList> list = perfListService.listMyTeam(perfMonth);
        return Msg.success(list);
    }

    @GetMapping("/report")
    @MyLog(title = "[perf-list]人事名单", content = "报表下载")
    public void report(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<PerfList> list = perfListService.report(params);
        String fileName = "人事名单";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(response.getOutputStream(), PerfList.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("人事名单").doWrite(list);
    }


}
