package com.cmbccd.ulms.common.component;

import cn.dev33.satoken.stp.StpInterface;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Menu;
import com.cmbccd.ulms.sys.domain.Role;
import com.cmbccd.ulms.sys.domain.UserRole;
import com.cmbccd.ulms.sys.service.MenuService;
import com.cmbccd.ulms.sys.service.RoleService;
import com.cmbccd.ulms.sys.service.UserRoleService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;
    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        UserRole userRole = userRoleService.getUserRolesByPrimaryKey(loginId.toString());
        if (userRole == null || Util.isNullorEmpty(userRole.getRoles())) {
            return new ArrayList<>();
        }
        String[] userRoleIds = userRole.getRoles().split(",");
        List<Menu> menus = menuService.getPermissionByRoleIds(userRoleIds);
        return menus.stream().map(Menu::getResourse).collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        UserRole userRole = userRoleService.getUserRolesByPrimaryKey(loginId.toString());
        if (userRole == null || Util.isNullorEmpty(userRole.getRoles())) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        for (String roleId : userRole.getRoles().split(",")) {
            try {
                Role role = roleService.getRoleById(Integer.parseInt(roleId.trim()));
                if (role != null && !Util.isNullorEmpty(role.getRoleName())) {
                    list.add(role.getRoleName());
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return list;
    }

}