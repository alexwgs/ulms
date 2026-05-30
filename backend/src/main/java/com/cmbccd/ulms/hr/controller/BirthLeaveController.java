package com.cmbccd.ulms.hr.controller;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.hr.domain.BirthLeave;
import com.cmbccd.ulms.hr.service.BirthLeaveService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hr/birth_leave")
public class BirthLeaveController {

    @Resource
private BirthLeaveService birthLeaveService;
    Logger logger = org.slf4j.LoggerFactory.getLogger(BirthLeaveController.class);

    @GetMapping("/list")
    public Msg list(@RequestParam Map<String, String> params) {
        List<BirthLeave> list = birthLeaveService.list(params);
        return Msg.success(new DataPage<BirthLeave>(list));
    }

    @GetMapping("/get/{ploNum}")
    public Msg getByPloNum(@PathVariable String ploNum) {
        List<BirthLeave> list = birthLeaveService.getByPloNum(ploNum);
        return Msg.success(list);
    }

    @PostMapping("/add")
    public Msg add(@RequestBody List<BirthLeave> records){
        logger.info("{}", records);
        int result = birthLeaveService.add(records);
        return result > 0 ? Msg.success() : Msg.error();
    }

    @PostMapping("/update")
    public Msg update(@RequestBody List<BirthLeave> records){
        logger.info("{}", records);
        int result = birthLeaveService.update(records);
        return result > 0 ? Msg.success() : Msg.error();
    }
}
