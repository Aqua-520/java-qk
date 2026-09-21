package com.wcy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.common.PageResponse;
import com.wcy.entity.Dept;
import com.wcy.mapper.DeptMapper;
import com.wcy.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {
    // 注入数据层依赖
    private final DeptMapper deptMapper;

    @Override
    public void insertDept(Dept dept) {
        // 给对象新增两个时间属性赋值
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        // 调用数据层
        this.deptMapper.insert(dept);
    }

    @Override
    public PageResponse selectDeptListByLimit(String name, Integer status, Integer page, Integer pageSize) {
        // 编写查询条件
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = Wrappers.lambdaQuery(Dept.class)
                // 如果字符串不为空则模糊匹配
                .like(StrUtil.isNotBlank(name), Dept::getName, name)
                .eq(status != null, Dept::getStatus, status);

        Page<Dept> selected = this.deptMapper.selectPage(new Page<>(page, pageSize), lambdaQueryWrapper);

        return new PageResponse<>(
                selected.getTotal(),
                selected.getRecords()
        );
    }

    @Override
    public Dept selectDeptById(Integer deptId) {
        // 根据id回显单条数据
        return this.deptMapper.selectById(deptId);
    }

    @Override
    public void updateDeptById(Dept dept) {
        // 先检测有没有id
        if (dept.getId() == null){
            throw new RuntimeException("更新部门信息,参数缺失");
        }

        // 将对象丢入
        this.deptMapper.updateById(dept);
    }

    @Override
    public void deleteDeptById(Integer deptId) {
        // 根据id做删除
        Dept dept = this.deptMapper.selectById(deptId);
        if (dept == null){
            // 如果找不到删个毛啊
            throw new RuntimeException("删除失败,部门不存在");
        }
        //  删除
        this.deptMapper.deleteById(deptId);
    }
}
