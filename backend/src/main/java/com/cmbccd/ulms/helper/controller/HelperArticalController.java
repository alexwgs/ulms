/**
 * 辅助文章管理控制器
 * 提供辅助文章的查询、创建、更新、删除等操作接口
 */
package com.cmbccd.ulms.helper.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.helper.domain.HelperArtical;
import com.cmbccd.ulms.helper.domain.HelperArticalExample;
import com.cmbccd.ulms.helper.service.HelperArticalService;
import com.cmbccd.ulms.sys.domain.Msg;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("helper/artical")
public class HelperArticalController {

    @Resource
    private HelperArticalService helperArticalService;

    @GetMapping("/list")
    @SaCheckPermission(value = {"helper:artical:list:self", "helper:artical:list"}, mode = SaMode.OR)
    public Msg list(@RequestParam Map<String, String> params) {
        boolean selfOnly = StpUtil.hasPermission("helper:artical:list:self");
        return Msg.success(helperArticalService.listArticalByQuery(params, selfOnly));
    }

    @GetMapping("/font/list")
    public Msg fontList(@RequestParam Map<String, String> params) {
        HelperArticalExample example = helperArticalService.listForFont(params);
        List<HelperArtical> list = helperArticalService.listNoBlob(example);
        return Msg.success(new DataPage<HelperArtical>(list));
    }

    @GetMapping("/{journo}")
    public Msg get(@PathVariable ("journo") String journo){
        HelperArtical artical = helperArticalService.get(journo);
        helperArticalService.increaseReadCount(journo);
        return Msg.success(artical);
    }

    @PostMapping("")
    @SaCheckPermission("helper:artical:add")
    @MyLog(title = "[helper-artical]文章管理")
    public Msg create(@RequestBody HelperArtical record){
        int count = helperArticalService.create(record);
        if (count<=0) return Msg.error();
        return Msg.success();
    }

    @PutMapping("")
    @SaCheckPermission("helper:artical:update")
    @MyLog(title = "[helper-artical]文章管理")
    public Msg Update(@RequestBody HelperArtical record){
        if(!StpUtil.hasPermission("helper:artical:list")) {
            if(!record.getInsertPlo().equals(StpUtil.getLoginIdAsString())) {
                return Msg.error("");
            }
        }
        int count = helperArticalService.update(record);
        if (count<=0) return Msg.error();
        return Msg.success();
    }

    @DeleteMapping("/{journo}")
    @SaCheckPermission("helper:artical:delete")
    @MyLog(title = "[helper-artical]文章管理")
    public Msg delete(@PathVariable ("journo") String journo){
        int count = helperArticalService.delete(journo);
        if (count<=0) return Msg.error();
        return Msg.success();
    }
//
//    @GetMapping("/keyword")
//    public Msg get(@RequestParam ("keyword") String keyword) {
//        HelperArtical artical = helperArticalService.get(journo);
//        return Msg.success().add("data", artical);
//    }
}
