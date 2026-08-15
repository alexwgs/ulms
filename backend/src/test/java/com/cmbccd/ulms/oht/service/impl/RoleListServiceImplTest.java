package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.oht.dao.RoleListMapper;
import com.cmbccd.ulms.oht.domain.RoleList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * {@link RoleListServiceImpl} 单元测试：验证增删改的入参空值保护，
 * 确保非法入参不触达 mapper。
 */
@ExtendWith(MockitoExtension.class)
public class RoleListServiceImplTest {

    @Mock
    private RoleListMapper roleListMapper;

    @InjectMocks
    private RoleListServiceImpl roleListService;

    @Test
    void insertUserRoleRejectsNullPloNum() {
        RoleList record = new RoleList();
        record.setRoleStat(1);

        assertEquals(0, roleListService.insertUserRole(record));
        verify(roleListMapper, never()).insertSelective(record);
    }

    @Test
    void insertUserRoleRejectsNullRoleStat() {
        RoleList record = new RoleList();
        record.setPloNum("1001");

        assertEquals(0, roleListService.insertUserRole(record));
        verify(roleListMapper, never()).insertSelective(record);
    }

    @Test
    void deleteUserRoleRejectsNullPloNum() {
        assertEquals(0, roleListService.deleteUserRole(null));
        verify(roleListMapper, never()).deleteByExample(any());
    }

    @Test
    void updateUserRoleRejectsNullPloNum() {
        RoleList record = new RoleList();
        record.setRoleStat(1);

        assertEquals(0, roleListService.updateUserRole(record));
        verify(roleListMapper, never()).updateByExampleSelective(any(), any());
    }
}
