package com.cmbccd.ulms.college.controller;

import com.alibaba.excel.EasyExcel;
import com.cmbccd.ulms.college.domain.TeachGroup;
import com.cmbccd.ulms.college.domain.TeachGroupExample;
import com.cmbccd.ulms.college.excel.TeachGroupListener;
import com.cmbccd.ulms.college.service.TeachGroupService;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("college/group/")
public class TeachGroupController {
    @Resource
    private TeachGroupService teachGroupService;
    @Resource
    private UlmsConfig ulmsConfig;
    @GetMapping("list")
    public Msg listTeacGroup() {
        List<TeachGroup> list = teachGroupService.listGroupName();
        return Msg.success(list);
    }

    @GetMapping("list/user")
    public Msg listTeacGroupUser(@RequestParam Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String groupName = params.get("groupName");
        String query = params.get("query");
        String queryType = params.get("queryType");
        if(Util.isNullorEmpty(groupName)) return Msg.error();
        TeachGroupExample example = new TeachGroupExample();
        TeachGroupExample.Criteria criteria = example.createCriteria();
        criteria.andGroupNameEqualTo(groupName);
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if(!Util.isNullorEmpty(query)) {
            if("ploNum".equals(queryType)) {
                criteria.andPloNumEqualTo(query);
            }else if("ploName".equals(queryType)) {
                List<String> ploNums = DataCache.getEmployees().values().stream().filter(e -> e.getPloName().indexOf(query)>-1 ).map(Employee::getPloNum).collect(Collectors.toList());
                if(ploNums.size()<1) criteria.andPloNumIsNull();
                else criteria.andPloNumIn(ploNums);
            }
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<TeachGroup> list = teachGroupService.listUserByGroup(example);
        return Msg.success(new DataPage<TeachGroup>(list));
    }

    @PostMapping("add")
    @SaCheckPermission("college:group:add")
    @MyLog(title = "[college-teacher]讲师管理")
    public Msg insert(@RequestBody TeachGroup record) {
        int count = 0;
        count = teachGroupService.add(record);
        if(count<1) return Msg.error();
        return Msg.success();
    }

    @PostMapping("delete")
    @SaCheckPermission("college:group:delete")
    @MyLog(title = "[college-teacher]讲师管理")
    public Msg delete(@RequestBody TeachGroup record){
        int count = 0 ;
        count = teachGroupService.delete(record);

        if(count<1) return Msg.error();
        return Msg.success();
    }

    @PostMapping("/upload/{fileName}")
    @SaCheckPermission("college:group:upload")
    @MyLog(title = "[college-teacher]讲师管理")
    public Msg processDailyGroupExcel(@PathVariable ("fileName") String fileName) {
        String file = ulmsConfig.getUploadPath() + "excel" + File.separator + fileName;
        teachGroupService.deleteAll();
        TeachGroupListener listener = new TeachGroupListener(teachGroupService);
        EasyExcel.read(file, listener).sheet(0).doReadSync();
        Msg msg = listener.getMessage();
        return msg;
    }
}
