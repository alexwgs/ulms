package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.EvaluateTemplete;
import com.cmbccd.ulms.college.service.EvaluateTempleteService;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.PublicService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("college/evaluate/templete")
public class EvaluateTemplateController {

    @Resource
    private EvaluateTempleteService evaluateTempleteService;

    @Resource
    private PublicService publicService;

    /**
     * 获取评价模板列表
     *
     * @param params
     * @return
     */
    @GetMapping("list")
    public Msg listGroup (@RequestParam Map<String, String> params) {
        String tempStat = params.get("tempStat");
        List<EvaluateTemplete> list;
        if(Util.isNullorEmpty(tempStat)) {
            list = evaluateTempleteService.group(null);
        }else{
            list = evaluateTempleteService.group(Integer.parseInt(tempStat));
        }
        return Msg.success(list);
    }

    /**
     * 获取评价模板
     *
     * @param tempId
     * @return
     */
    @GetMapping("/{tempId}")
    public Msg listTemplete (@PathVariable String tempId) {
        List<EvaluateTemplete> list = evaluateTempleteService.select(tempId);
        return Msg.success(list);
    }

    /**
     * 新增评价模板
     *
     * @param records
     * @return
     */
    @PostMapping("")
    @SaCheckPermission("college:evaltemp:insert")
    public Msg insert(@RequestBody List<EvaluateTemplete> records) {
        String tempId = publicService.getJourno();
        for (EvaluateTemplete item : records) {
            item.setTempId(tempId);
            evaluateTempleteService.insert(item);
        }
        return Msg.success();
    }

    /**
     * 修改评价模板
     * @param records
     * @return
     */
    @PutMapping("")
    @SaCheckPermission("college:evaltemp:update")
    public Msg update(@RequestBody List<EvaluateTemplete> records) {
        for (EvaluateTemplete item : records) {
            evaluateTempleteService.update(item);
        }
        return Msg.success();
    }
}
