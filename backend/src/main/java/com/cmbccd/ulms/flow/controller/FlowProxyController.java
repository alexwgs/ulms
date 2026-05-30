package com.cmbccd.ulms.flow.controller;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.flow.domain.FlowProxy;
import com.cmbccd.ulms.flow.service.FlowProxyService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flow/proxy")
public class FlowProxyController {
    @Resource
    private FlowProxyService flowProxyService;

    @GetMapping("/list")
    public Msg list(@RequestParam Map<String, String> params){
        List<FlowProxy> list = flowProxyService.list(params);
        list.forEach(e -> {
            e.setPloUser(DataCache.EMPLOYEE.get(e.getPloNum()));
            e.setProxyUser(DataCache.EMPLOYEE.get(e.getProxyNum()));
            e.setHandleUser(DataCache.EMPLOYEE.get(e.getHandlePlo()));
        });
        return Msg.success(new DataPage<FlowProxy>(list));
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
