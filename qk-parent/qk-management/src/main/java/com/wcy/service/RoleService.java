package com.wcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.entity.Role;

import java.util.List;

// 里面定义抽象方法,让实现类实现
public interface RoleService extends IService<Role> {
    // List<Role> getAllRoleList();

    PageResponse getRoleListByLimit(String name, String label, Integer page, Integer pageSize);

    // void updateRoleInfo(Role role);
    //
    // void insertRoleInfo(Role role);
    //
    // Role getById(Integer roleId);
    //
    boolean deleteRoleInfoById(Integer roleId);
}
