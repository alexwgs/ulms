package com.cmbccd.ulms.hr.controller;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.hr.domain.SubsidyUnit;
import com.cmbccd.ulms.hr.service.SubsidyUnitService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hr/subsidy_unit")
public class SubsidyUnitController {
    @Resource
    private SubsidyUnitService subsidyUnitService;

    @GetMapping("/list")
    public Msg list(@RequestParam Map<String, String> params) {
        List<SubsidyUnit> list = subsidyUnitService.list(params);
        return Msg.success(new DataPage<SubsidyUnit>(list));
    }

    @GetMapping("/get/{id}")
    public Msg get(@PathVariable String id) {
        SubsidyUnit result = subsidyUnitService.get(id);
        return Msg.success(result);
    }

    @PostMapping("/add")
    public Msg add(@RequestBody SubsidyUnit record) {
        int result = subsidyUnitService.add(record);
        return result > 0 ? Msg.success() : Msg.error();
    }

    @DeleteMapping("/delete/{id}")
    public Msg delete(@PathVariable String id) {
        int result = subsidyUnitService.delete(id);
        return result > 0 ? Msg.success() : Msg.error();
    }

    @PutMapping("/update")
    public Msg update(@RequestBody SubsidyUnit record) {
        int result = subsidyUnitService.update(record);
        return result > 0 ? Msg.success() : Msg.error();
    }
}
