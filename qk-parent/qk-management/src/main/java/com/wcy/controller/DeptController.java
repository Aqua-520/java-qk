package com.wcy.controller;

import com.wcy.annotation.WcyLogger;
import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.entity.Dept;
import com.wcy.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 通过构造函数注入依赖的注解
@RequiredArgsConstructor
public class DeptController {
    // 服务层依赖
    private final DeptService deptService;
    // 注入日志对象
    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    // 新增部门的方法
    @PostMapping("/depts")
    @WcyLogger
    Response insertDept(@RequestBody Dept dept){
        // 新增日志
        log.info("新增部门,部门名称是:{}",dept.getName());
        // 需要加注解
        // 通过参数直接接收前端传入的json,转成java对象

        // 调用业务层方法
        this.deptService.insertDept(dept);


        return Response.success();
    }

    // 查询部门列表的方法,可能会有条件筛选,并且包含分页功能
    @GetMapping("/depts")
    Response selectDeptListByLimit(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "status" , required = false)Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        // 查询部门列表日志
        log.info("分页查询部门, 参数: name={}, status={}, page={}, pageSize={}", name, status, page, pageSize);

        //  调用业务层
        // 拿到一个分页对象
        PageResponse pageResponse = this.deptService.selectDeptListByLimit(
                name,status,page,pageSize
        );

        // 返回包装后的响应对象
        return Response.success(pageResponse);
    }

    // 根据id单条数据回显
    @GetMapping("/depts/{id}")
    Response selectDeptById(@PathVariable("id") Integer deptId){
        // 调用业务层完成查询回显
        Dept dept = this.deptService.selectDeptById(deptId);

        return Response.success(dept);
    }

    // 更新单条数据
    @PutMapping("/depts")
    @WcyLogger
    Response updateDeptById(@RequestBody Dept dept){
        // 将对象传给业务层做处理
        this.deptService.updateDeptById(dept);

        return Response.success();
    }

    // 根据id删除
    @DeleteMapping("/depts/{id}")
    @WcyLogger
    Response deleteDeptById(@PathVariable("id") Integer deptId){
        // 将对象传给业务层做处理
        this.deptService.deleteDeptById(deptId);

        return Response.success();
    }

    // 查询全部部门列表
    @GetMapping("/depts/list")
    Response selectAllDeptList(){
        // 直接拿到全部的列表,包装到对象中返回
        List<Dept> deptList  = this.deptService.selectAllDeptList();
        return Response.success(deptList);
    }
}
