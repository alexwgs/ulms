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
import com.cmbccd.ulms.helper.domain.HelperArticle;
import com.cmbccd.ulms.helper.domain.HelperArticleExample;
import com.cmbccd.ulms.helper.service.HelperArticleService;
import com.cmbccd.ulms.sys.domain.Msg;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("helper/article")
public class HelperArticleController {

    @Resource
    private HelperArticleService helperArticleService;

    @GetMapping("/list")
    @SaCheckPermission(value = {"helper:artical:list:self", "helper:artical:list"}, mode = SaMode.OR)
    public Msg list(@RequestParam Map<String, String> params) {
        boolean selfOnly = StpUtil.hasPermission("helper:artical:list:self");
        return Msg.success(helperArticleService.listArticleByQuery(params, selfOnly));
    }

    @GetMapping("/font/list")
    public Msg fontList(@RequestParam Map<String, String> params) {
        HelperArticleExample example = helperArticleService.listForFont(params);
        List<HelperArticle> list = helperArticleService.listNoBlob(example);
        return Msg.success(new DataPage<HelperArticle>(list));
    }

    @GetMapping("/{journo}")
    public Msg get(@PathVariable ("journo") String journo){
        HelperArticle article = helperArticleService.get(journo);
        helperArticleService.increaseReadCount(journo);
        return Msg.success(article);
    }

    @PostMapping("")
    @SaCheckPermission("helper:artical:add")
    @MyLog(title = "[helper-article]文章管理")
    public Msg create(@RequestBody HelperArticle record){
        int count = helperArticleService.create(record);
        if (count<=0) return Msg.error();
        return Msg.success();
    }

    @PutMapping("")
    @SaCheckPermission("helper:artical:update")
    @MyLog(title = "[helper-article]文章管理")
    public Msg Update(@RequestBody HelperArticle record){
        if(!StpUtil.hasPermission("helper:artical:list")) {
            if(!record.getInsertPlo().equals(StpUtil.getLoginIdAsString())) {
                return Msg.error("");
            }
        }
        int count = helperArticleService.update(record);
        if (count<=0) return Msg.error();
        return Msg.success();
    }

    @DeleteMapping("/{journo}")
    @SaCheckPermission("helper:artical:delete")
    @MyLog(title = "[helper-article]文章管理")
    public Msg delete(@PathVariable ("journo") String journo){
        int count = helperArticleService.delete(journo);
        if (count<=0) return Msg.error();
        return Msg.success();
    }
//
//    @GetMapping("/keyword")
//    public Msg get(@RequestParam ("keyword") String keyword) {
//        HelperArticle article = helperArticleService.get(journo);
//        return Msg.success().add("data", article);
//    }
}
