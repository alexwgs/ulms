package com.cmbccd.ulms.rpa.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.rpa.domain.ToolTemplete;
import com.cmbccd.ulms.rpa.service.ToolTempleteService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rpa/templete")
public class ToolTempleteController {

    private final ToolTempleteService toolTempleteService;

    public ToolTempleteController(ToolTempleteService toolTempleteService) {
        this.toolTempleteService = toolTempleteService;
    }

    @GetMapping("/{templeteId}")
    public Msg getToolTemplete(@PathVariable String templeteId) {
        List<ToolTemplete> list = toolTempleteService.getToolTemplete(templeteId);
        return Msg.success(list);
    }

    @PutMapping("/update")
    @SaCheckPermission("rpa:tool:templete:update")
    @MyLog(title = "[rpa-templete]RPA模版管理")
    public Msg updateToolTemplete(@RequestBody ToolTemplete toolTemplete) {
        toolTempleteService.updateToolTemplete(toolTemplete);
        return Msg.success();
    }

    @PostMapping("/add")
    @SaCheckPermission("rpa:tool:templete:add")
    @MyLog(title = "[rpa-templete]RPA模版管理")
    public Msg addToolTemplete(@RequestBody ToolTemplete toolTemplete) {
        toolTempleteService.addToolTemplete(toolTemplete);
        return Msg.success();
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("rpa:tool:templete:delete")
    @MyLog(title = "[rpa-templete]RPA模版管理")
    public Msg deleteToolTemplete(@PathVariable String id) {
        toolTempleteService.deleteToolTemplete(id);
        return Msg.success();
    }
}