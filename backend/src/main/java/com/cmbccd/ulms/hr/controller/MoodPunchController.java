package com.cmbccd.ulms.hr.controller;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.domain.MoodConfig;
import com.cmbccd.ulms.hr.domain.MoodRecord;
import com.cmbccd.ulms.hr.service.MoodConfigService;
import com.cmbccd.ulms.hr.service.MoodRecordService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("hr/mood")
public class MoodPunchController {

    @Resource
    private MoodConfigService moodConfigService;
    @Resource
    private MoodRecordService moodRecordService;
    @GetMapping("/pic")
    public Msg listMoodPic(){
        Short status = (short)1 ;
        List<MoodConfig> list = moodConfigService.list(status);
        return Msg.success(list);
    }


    @PostMapping("/submit")
    public Msg insertMoodRecord(@RequestBody MoodRecord record){
        // 服务端确认当前登录用户，防止客户端伪造 userId 替他人打卡
        record.setUserId(Util.userIdByShiro());
        int count = moodRecordService.create(record);
        return  Msg.success("成功提交【"+count+"】条信息！");
    }

}
