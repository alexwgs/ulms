package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.OperLog;
import com.cmbccd.ulms.sys.domain.OperLogExample;
import com.cmbccd.ulms.sys.service.OperLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/operLog")
public class OperLogController {

    @Resource
    private OperLogService operLogService;

    @GetMapping(value = "list")
	@SaCheckPermission("sys:operlog:list")
    public Msg getOperLogList(@RequestParam Map<String, String> params) {

        Map<String, Integer> pageParams = Util.innitTablePages(params);

        OperLogExample example = new OperLogExample();
        OperLogExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("status"))) {
            criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
        }
        if (!Util.isNullorEmpty(params.get("begDate")) && !Util.isNullorEmpty(params.get("endDate"))) {
            criteria.andOperTimeBetween(params.get("begDate") + " 00:00:00", params.get("endDate") + " 23:59:59");
        } else {
            criteria.andOperTimeBetween(Util.getDateToday() + " 00:00:00", Util.getDateToday() + " 23:59:59");
        }

        if (!Util.isNullorEmpty(params.get("query"))) {
            if("ploNum".equals(params.get("queryType"))) {
                criteria.andPloNumLike('%' + params.get("query") + '%');
            }else if("title".equals(params.get("queryType"))){
                criteria.andTitleLike('%' + params.get("query") + '%');
            }
        }
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<OperLog> list = operLogService.list(example);

        return Msg.success(new DataPage<OperLog>(list));
    }
    // 报表下载
    @GetMapping("/report")
    @SaCheckPermission("sys:operlog:report")
    @MyLog(title = "[sys-operlog]应用操作日志", content = "报表下载")
    public void report(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // Map<String, Integer> pageParams = Util.innitTablePages(params);

        OperLogExample example = new OperLogExample();
        OperLogExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("status"))) {
            criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
        }
        if (!Util.isNullorEmpty(params.get("begDate")) && !Util.isNullorEmpty(params.get("endDate"))) {
            criteria.andOperTimeBetween(params.get("begDate") + " 00:00:00", params.get("endDate") + " 23:59:59");
        } else {
            criteria.andOperTimeBetween(Util.getDateToday() + " 00:00:00", Util.getDateToday() + " 23:59:59");
        }

        if (!Util.isNullorEmpty(params.get("query"))) {
            if("ploNum".equals(params.get("queryType"))) {
                criteria.andPloNumLike('%' + params.get("query") + '%');
            }else if("title".equals(params.get("queryType"))){
                criteria.andTitleLike('%' + params.get("query") + '%');
            }
        }
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
        }
        List<OperLog> list = operLogService.list(example);
        String fileName = "应用操作日志";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(response.getOutputStream(), OperLog.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("应用操作日志").doWrite(list);
    }

}
