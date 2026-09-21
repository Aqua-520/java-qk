package com.wcy.service;

import com.wcy.common.PageResponse;
import com.wcy.entity.Dept;


public interface DeptService {

    void insertDept(Dept dept);

    PageResponse selectDeptListByLimit(String name, Integer status, Integer page, Integer pageSize);

    Dept selectDeptById(Integer deptId);

    void updateDeptById(Dept dept);

    void deleteDeptById(Integer deptId);
}
