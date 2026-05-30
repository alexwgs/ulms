package com.cmbccd.ulms.helper.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.helper.domain.HelperTree;
import com.cmbccd.ulms.helper.service.HelperTreeService;
import com.cmbccd.ulms.sys.domain.Msg;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(("/helper/tree"))
public class HelperTreeController {


    @Resource
    private HelperTreeService helperTreeService;

    /**
     * 获取辅助树结构
     * @return 包含辅助树结构列表的Msg对象
     */
    @GetMapping("/tree")
    public Msg getCourseTypeTree() {
        List<HelperTree> list = helperTreeService.helperTree((short)1);
        return Msg.success(list);
    }

    /**
     * 更新辅助树节点信息
     * @param record 要更新的辅助树节点对象
     * @return 操作结果的Msg对象
     */
    @PutMapping("")
    @SaCheckPermission("helper:tree:update")
    @MyLog(title = "[helper-tree]路径管理")
    public Msg update(@RequestBody HelperTree record){
        int count =helperTreeService.update(record);
        if (count<1) return Msg.error();
        return Msg.success();
    }


    /**
     * 插入新的辅助树节点
     * @param record 要插入的辅助树节点对象
     * @return 操作结果的Msg对象
     */
    @PostMapping("")
    @SaCheckPermission("helper:tree:insert")
    @MyLog(title = "[helper-tree]路径管理")
    public Msg insert(@RequestBody HelperTree record){
        int count =helperTreeService.insert(record);
        if (count<1) return Msg.error();
        return Msg.success();
    }

    /**
     * 删除指定ID的辅助树节点
     * @param id 要删除的节点ID
     * @return 操作结果的Msg对象
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("helper:tree:delete")
    @MyLog(title = "[helper-tree]路径管理")
    public Msg delete(@PathVariable Integer id){
        int count =helperTreeService.delete(id);
        if (count<1) return Msg.error();
        return Msg.success();
    }

    /**
     * 删除指定ID的辅助树节点及其所有子节点
     * @param id 要删除的节点ID
     * @return 操作结果的Msg对象
     */
    @DeleteMapping("/withchildren/{id}")
    @SaCheckPermission("helper:tree:delete")
    @MyLog(title = "[helper-tree]路径管理")
    public Msg deleteByIdWithChildren(@PathVariable Integer id){
        int count =helperTreeService.deleteByIdWithChildren(id);
        if (count<1) return Msg.error();
        return Msg.success();
    }
}
