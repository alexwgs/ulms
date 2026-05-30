/**
 * RPA工具列表管理控制器
 * 提供RPA工具的增删改查功能
 */
package com.cmbccd.ulms.rpa.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.rpa.domain.ToolList;
import com.cmbccd.ulms.rpa.service.ToolListService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rpa/tool")
public class ToolListController {
    @Autowired
    private ToolListService toolListService;

    /**
     * 获取所有RPA工具列表
     * @param params 查询参数
     * @return 包含RPA工具列表的分页数据
     */
    @GetMapping("/list")
//    @SaCheckPermission("rpa:tool:list")
    @MyLog(title = "[rpa-list]RPA工具管理")
    public Msg getAllToolList(@RequestParam Map<String, String> params) {
        List<ToolList> list = toolListService.getAllToolList(params);
        return Msg.success(new DataPage<ToolList>(list));
    }

    /**
     * 更新RPA工具信息
     * @param toolList RPA工具对象
     * @return 操作结果信息
     */
    @PutMapping("/update")
    @SaCheckPermission("rpa:tool:list:update")
    @MyLog(title = "[rpa-list]RPA工具管理")
    public Msg updateToolList(@RequestBody ToolList toolList) {
        int count = toolListService.updateToolList(toolList);
        return count==0? Msg.error():Msg.success();
    }

    /**
     * 新增RPA工具
     * @param toolList RPA工具对象
     * @return 操作结果信息
     */
    @PostMapping("/add")
    @SaCheckPermission("rpa:tool:list:add")
    @MyLog(title = "[rpa-list]RPA工具管理")
    public Msg addToolList(@RequestBody ToolList toolList) {
        int count = toolListService.addToolList(toolList);
        return count==0? Msg.error():Msg.success();
    }

    /**
     * 删除RPA工具
     * @param id 工具ID
     * @return 操作结果信息
     */
    @DeleteMapping("/delete/{id}")
    @SaCheckPermission("rpa:tool:list:delete")
    @MyLog(title = "[rpa-list]RPA工具管理")
    public Msg deleteToolList(@PathVariable("id") String id) {
        int count = toolListService.deleteToolList(id);
        return count==0? Msg.error():Msg.success();
    }

    /**
     * 前端获取工具列表
     * @param params 查询参数
     * @return RPA工具列表
     */
    @GetMapping("")
    public Msg getToolList(@RequestParam Map<String, String> params) {
        List<ToolList> list = toolListService.getUserToolList(params);
        return Msg.success(list);
    }

    /**
     * 用于过滤查询工具使用情况，提供联想工具名字
     * @param name 工具名称关键字
     * @return Msg
     */
    @GetMapping("/get/name")
    public Msg getToolName(@RequestParam String name) {
        List<String> list = toolListService.searchToolListName(name);
        return Msg.success(list);
    }
}
