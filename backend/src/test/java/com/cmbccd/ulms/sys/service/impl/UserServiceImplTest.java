package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.UserMapper;
import com.cmbccd.ulms.sys.domain.Menu;
import com.cmbccd.ulms.sys.domain.Role;
import com.cmbccd.ulms.sys.domain.User;
import com.cmbccd.ulms.sys.domain.UserRole;
import com.cmbccd.ulms.sys.service.RoleService;
import com.cmbccd.ulms.sys.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * {@link UserServiceImpl#getWithMenuList} 单元测试：
 * 覆盖权限装配核心逻辑（用户不存在/无角色/菜单与权限码分流）。
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRoleService userRoleService;
    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl service;

    private Menu menu(Integer id, String resourse) {
        Menu m = new Menu();
        m.setId(id);
        m.setResourse(resourse);
        return m;
    }

    private Role role(Menu... menus) {
        Role r = new Role();
        r.setMenus(Arrays.asList(menus));
        return r;
    }

    @Test
    void returnsNullWhenUserNotFound() {
        assertNull(service.getWithMenuList("NOT_EXIST"));
    }

    @Test
    void returnsUserWhenNoRoleAssigned() {
        User user = new User();
        user.setCzyCode("655012");
        when(userMapper.selectByPrimaryKey("655012")).thenReturn(user);
        when(userRoleService.getUserRolesByPrimaryKey("655012")).thenReturn(null);

        User result = service.getWithMenuList("655012");

        assertNotNull(result);
        assertNull(result.getMenuList());
    }

    @Test
    void splitsMenusAndPermissionsByResourse() {
        User user = new User();
        user.setCzyCode("655012");
        when(userMapper.selectByPrimaryKey("655012")).thenReturn(user);
        UserRole userRole = new UserRole();
        userRole.setRoles("1,2");
        when(userRoleService.getUserRolesByPrimaryKey("655012")).thenReturn(userRole);

        // 角色1：菜单 id=1（无权限码）+ 权限码 sys:user:list；角色2：菜单 id=2（无权限码）
        Role role1 = role(menu(1, null), menu(null, "sys:user:list"));
        Role role2 = role(menu(2, null));
        when(roleService.getRoleByIdsForPermission("1,2")).thenReturn(Arrays.asList(role1, role2));

        User result = service.getWithMenuList("655012");

        assertNotNull(result);
        assertEquals(2, result.getMenuList().size());
        assertTrue(result.getMenuList().contains(1));
        assertTrue(result.getMenuList().contains(2));
    }

    @Test
    void permissionOnlyMenusNotAddedToMenuList() {
        User user = new User();
        user.setCzyCode("655012");
        when(userMapper.selectByPrimaryKey("655012")).thenReturn(user);
        UserRole userRole = new UserRole();
        userRole.setRoles("1");
        when(userRoleService.getUserRolesByPrimaryKey("655012")).thenReturn(userRole);

        Role role1 = role(menu(null, "sys:user:delete"), menu(null, "sys:user:add"));
        when(roleService.getRoleByIdsForPermission("1")).thenReturn(Collections.singletonList(role1));

        User result = service.getWithMenuList("655012");

        assertNotNull(result);
        assertNotNull(result.getMenuList());
        assertTrue(result.getMenuList().isEmpty());
    }
}
