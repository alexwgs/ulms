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
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.helper.domain.HelperArtical;
import com.cmbccd.ulms.helper.domain.HelperArticalExample;
import com.cmbccd.ulms.helper.service.HelperArticalService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;

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
        String status = params.get("status");
        String routeId = params.get("routeId");
        String query = params.get("query");
        String queryType = params.get("queryType");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        HelperArticalExample example = new HelperArticalExample();
        HelperArticalExample.Criteria criteria = example.createCriteria();
        HelperArticalExample.Criteria criteria2 = example.createCriteria();
        if (StpUtil.hasPermission("helper:artical:list:self")) {
            criteria.andInsertPloEqualTo(Util.userIdByShiro());
            criteria2.andUpdatePloEqualTo(Util.userIdByShiro());
        }
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if(!Util.isNullorEmpty(routeId)) {
            criteria.andRouteIdEqualTo(Integer.parseInt(routeId));
            criteria2.andRouteIdEqualTo(Integer.parseInt(routeId));
        }
        if(!Util.isNullorEmpty(status)) {
            criteria.andStatusEqualTo(Integer.parseInt(status));
            criteria2.andStatusEqualTo(Integer.parseInt(status));
        }
        if(!Util.isNullorEmpty(query)){
            if(Util.isNullorEmpty(queryType) || queryType.equals("keyword")){
                criteria2.andTitleLike("%"+query+"%");
                criteria.andKeyWordLike("%"+query+"%");
            }else if(queryType.equals("content")) {
                criteria2.andTitleLike("%"+query+"%");
                criteria.andContentLike("%"+query+"%");
            }
            example.or(criteria2);
        }

        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<HelperArtical> list = helperArticalService.listNoBlob(example);
        return Msg.success(new DataPage<HelperArtical>(list));
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
