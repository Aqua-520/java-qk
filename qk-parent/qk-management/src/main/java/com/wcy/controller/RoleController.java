package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.entity.Role;
import com.wcy.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 通过构造函数注入业务层依赖
@RequiredArgsConstructor
public class RoleController {
    // 业务层对象
    private final RoleService roleService;

    // 查询所有角色列表
    @GetMapping("/roles/list")
    public Response getAllRoleList(){
        // 调用业务层查询
        List<Role> roleList = this.roleService.getAllRoleList();

        // 返回
        return Response.success(roleList);
    }

    // 分页查询
    @GetMapping("/roles")
    public Response getRoleListByLimit(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String label,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        // 调用业务层拿到分页对象
        PageResponse pageResponse = this.roleService.getRoleListByLimit(name,label,page,pageSize);

        return Response.success(pageResponse);
    }

    // 修改角色详细
    @PutMapping("/roles")
    public Response updateRoleInfo(@RequestBody Role role){
        // 定义请求体接收前端json并包装成对象
        this.roleService.updateRoleInfo(role);
        return Response.success();
    }

    // 新增角色
    @PostMapping("/roles")
    public Response insertRoleInfo(@RequestBody Role role){
        // 定义请求体接收前端json并包装成对象
        this.roleService.insertRoleInfo(role);
        return Response.success();
    }

    // 根据id查询单条
    @GetMapping("/roles/{id}")
    public Response selectRoleInfoById(@PathVariable(value = "id") Integer roleId){
        Role role = this.roleService.getById(roleId);
        return Response.success(role);
    }

    // 根据id删除
    @DeleteMapping("/roles/{id}")
    public Response deleteRoleInfoById(@PathVariable(value = "id") Integer roleId){
        this.roleService.deleteRoleInfoById(roleId);
        return Response.success();
    }
}
