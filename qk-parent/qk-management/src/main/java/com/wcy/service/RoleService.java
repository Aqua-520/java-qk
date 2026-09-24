package com.wcy.service;

import com.wcy.common.PageResponse;
import com.wcy.entity.Role;

import java.util.List;

// 里面定义抽象方法,让实现类实现
public interface RoleService {
    List<Role> getAllRoleList();

    PageResponse getRoleListByLimit(String name, String label, Integer page, Integer pageSize);

    void updateRoleInfo(Role role);

    void insertRoleInfo(Role role);

    Role getById(Integer roleId);

    void deleteRoleInfoById(Integer roleId);
}
