package com.wcy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.common.PageResponse;
import com.wcy.entity.Role;
import com.wcy.exception.DataNotFoundException;
import com.wcy.exception.ParamsException;
import com.wcy.mapper.RoleMapper;
import com.wcy.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 标注类为业务层,给spring容器管理
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    // 注入数据层依赖
    private final RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoleList() {
        // 打印日志
        log.info("查询全部角色列表信息");
        // 查询列表返回
        List<Role> roleList = this.roleMapper.selectList(Wrappers.emptyWrapper());
        if (roleList == null){
            throw new DataNotFoundException("找不到角色列表信息,请联系数据库管理员");
        }
        return roleList;
    }

    @Override
    public PageResponse getRoleListByLimit(String name, String label, Integer page, Integer pageSize) {
        // 打印日志
        log.info("查询角色列表,分页版");
        // 筛选条件
        LambdaQueryWrapper<Role> lambdaQueryWrapper = Wrappers.lambdaQuery(Role.class)
                .like(StrUtil.isNotBlank(name), Role::getName, name)
                .like(StrUtil.isNotBlank(label), Role::getLabel, label)
                .orderByDesc(Role::getCreateTime);
        // 查询数据库
        Page<Role> rolePage = this.roleMapper.selectPage(new Page<>(page, pageSize), lambdaQueryWrapper);
        if (rolePage == null){
            throw new DataNotFoundException("角色列表,分页查询失败,请询问数据库管理员");
        }
        // 封装响应结果
        // 封装响应结果
        PageResponse<Role> pageResponse = new PageResponse<>(
                rolePage.getTotal(),
                rolePage.getRecords()
        );
        return pageResponse;
    }

    @Override
    public void updateRoleInfo(Role role) {
        // 打印日志
        log.info("更新角色信息, id={}", role.getId());
        // 1. 参数校验
        if (role.getId() == null) {
            throw new ParamsException("角色id不能为空");
        }

        // 2. 先查库，确认角色存在
        Role existing = this.roleMapper.selectById(role.getId());
        if (existing == null) {
            throw new DataNotFoundException("角色不存在，id=" + role.getId());
        }

        // 更新四个字段
        existing.setName(role.getName());
        existing.setLabel(role.getLabel());
        existing.setRemark(role.getRemark());
        existing.setUpdateTime(LocalDateTime.now());

        // 更新行数
        int rows = this.roleMapper.updateById(existing);
        if (rows == 0) {
            throw new RuntimeException("更新角色失败，请重试");
        }
    }

    @Override
    public void insertRoleInfo(Role role) {
        // 打印日志
        log.info("新增角色信息");

        // name和label非空校验
        if (StrUtil.isBlank(role.getName()) || StrUtil.isBlank(role.getLabel())) {
            throw new ParamsException("角色名称不能为空");
        }

        // 补充字段
        LocalDateTime now = LocalDateTime.now();
        role.setCreateTime(now);
        role.setUpdateTime(now);

        // 入库
        int rows = this.roleMapper.insert(role);
        if (rows == 0) {
            throw new RuntimeException("新增角色失败，请重试");
        }
    }

    @Override
    public Role getById(Integer roleId) {
        // 通过id查询
        log.info("通过id查询角色详情");

        // 查询对象
        Role role = this.roleMapper.selectById(roleId);
        if (role == null){
            throw new DataNotFoundException("找不到角色详情");
        }
        return role;
    }

    @Override
    public void deleteRoleInfoById(Integer roleId) {
        // 根据id删除
        // 打印日志
        log.info("删除角色信息, id={}", roleId);

        // 先查库，确认角色存在
        Role existing = this.roleMapper.selectById(roleId);
        if (existing == null) {
            throw new DataNotFoundException("角色不存在，id=" + roleId);
        }

        // 删除
        this.roleMapper.deleteById(roleId);
    }
}
