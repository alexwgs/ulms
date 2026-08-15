package com.cmbccd.ulms.flow.controller;

import com.cmbccd.ulms.flow.domain.FlowProxy;
import com.cmbccd.ulms.flow.service.FlowProxyService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/flow/proxy")
public class FlowProxyController {
    @Resource
    private FlowProxyService flowProxyService;

    @GetMapping("/list")
    public Msg list(@RequestParam Map<String, String> params){
        return Msg.success(flowProxyService.list(params));
    }

    @PostMapping("/add")
    public Msg add(@RequestBody FlowProxy flowProxy){
        Msg msg = flowProxyService.insert(flowProxy);
        return msg;
    }

    @PutMapping("/stop/{id}")
    public Msg stop(@PathVariable ("id") String id) throws Exception {
        Msg msg = flowProxyService.stopProxy(id);
        return msg;
    }
    @DeleteMapping("/delete/{id}")
    public Msg delete(@PathVariable ("id") String id){
        int i = flowProxyService.delete(id);
        if (i == 1){
            return Msg.success();
        }else {
            return Msg.error();
        }
    }
}
