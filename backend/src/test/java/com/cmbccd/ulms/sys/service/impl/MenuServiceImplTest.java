package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.domain.Menu;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link MenuServiceImpl#findMenuPermissionChild} 单元测试：
 * 覆盖 Integer 引用比较修复、状态过滤、三级菜单 children 装配。
 */
public class MenuServiceImplTest {

    private Menu createMenu(Integer id, Integer pid, Short status, Short menuType, Integer sort) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setPid(pid);
        menu.setStatus(status);
        menu.setMenuType(menuType);
        menu.setSort(sort);
        return menu;
    }

    @Test
    void findMenuPermissionChildMatchesIdAbove127() {
        // 验证 Integer==Integer 引用比较修复：id=128 超出 -128~127 缓存范围，原 == 比较会失效
        MenuServiceImpl service = new MenuServiceImpl();
        List<Menu> rootMenus = new ArrayList<>();
        rootMenus.add(createMenu(128, 127, (short) 1, (short) 0, 1));
        rootMenus.add(createMenu(129, 128, (short) 1, (short) 0, 2));

        List<Menu> children = service.findMenuPermissionChild(128, rootMenus, false);

        assertEquals(1, children.size());
        assertEquals(Integer.valueOf(129), children.get(0).getId());
    }

    @Test
    void findMenuPermissionChildFiltersByStatusWhenAuth() {
        MenuServiceImpl service = new MenuServiceImpl();
        List<Menu> rootMenus = new ArrayList<>();
        rootMenus.add(createMenu(1, 0, (short) 1, (short) 0, 1));
        rootMenus.add(createMenu(2, 0, (short) 0, (short) 0, 2)); // status=0 应被过滤

        List<Menu> children = service.findMenuPermissionChild(0, rootMenus, true);

        assertEquals(1, children.size());
        assertEquals(Integer.valueOf(1), children.get(0).getId());
    }

    @Test
    void findMenuPermissionChildKeepsDisabledWhenNotAuth() {
        MenuServiceImpl service = new MenuServiceImpl();
        List<Menu> rootMenus = new ArrayList<>();
        rootMenus.add(createMenu(1, 0, (short) 0, (short) 0, 1));

        // authFlag=false 时不按 status 过滤
        List<Menu> children = service.findMenuPermissionChild(0, rootMenus, false);

        assertEquals(1, children.size());
    }

    @Test
    void findMenuPermissionChildSetsGrandChildren() {
        MenuServiceImpl service = new MenuServiceImpl();
        List<Menu> rootMenus = new ArrayList<>();
        rootMenus.add(createMenu(1, 0, (short) 1, (short) 0, 1)); // 二级菜单
        rootMenus.add(createMenu(11, 1, (short) 1, (short) 1, 1)); // 三级菜单（menuType=1）

        List<Menu> children = service.findMenuPermissionChild(0, rootMenus, false);

        assertEquals(1, children.size());
        assertEquals(1, children.get(0).getChildren().size());
        assertEquals(Integer.valueOf(11), children.get(0).getChildren().get(0).getId());
    }
}
