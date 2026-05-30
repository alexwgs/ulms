package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.sys.domain.JobInfo;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.JobInfoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/jobInfo")
public class JobInfoController {

    @Resource
    private JobInfoService jobInfoService;

    @GetMapping("/list")
    public Msg list(@RequestParam Map<String, String> params) {
        List<JobInfo> jobInfos = jobInfoService.list(params);
        return Msg.success(new DataPage<JobInfo>(jobInfos));
    }

    @GetMapping("")
    public Msg listforselective() {
        List<JobInfo> list = DataCache.JOB;
        return Msg.success(list);
    }

    // 修改岗位信息
    @PutMapping("")
    @MyLog(title = "[sys-jobinfo]岗位管理")
    public Msg edit(@RequestBody JobInfo record){
        int count = jobInfoService.update(record);
        if (count < 1) return Msg.error("更新失败");
        return Msg.success();
    }

    // 新增岗位信息
    @PostMapping("")
    @MyLog(title = "[sys-jobinfo]岗位管理")
    public Msg add(@RequestBody JobInfo record){
        int count = jobInfoService.insert(record);
        if (count < 1) return Msg.error("新建失败");
        return Msg.success();
    }

    // 删除岗位信息
    @DeleteMapping("/{id}")
    @MyLog(title = "[sys-jobinfo]岗位管理")
    public Msg delete(@PathVariable("id") String id){
        int count = jobInfoService.delete(id);
        if (count < 1) return Msg.error("删除失败");
        return Msg.success();
    }
}
