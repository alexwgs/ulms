package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.UserPoint;
import com.cmbccd.ulms.college.service.UserPointService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/college/point")
public class UserPointsController {

    @Resource
    private UserPointService userPointService;

    /**
     * 获取用户积分
     * @return
     */
    @GetMapping("")
    public Msg getUserPoints() {
        List<UserPoint> list = userPointService.listUserPointRank(20);
        UserPoint userPoints = userPointService.getMyPoint();
        return Msg.success().put("rank",list).put("point",userPoints);
    }
}
