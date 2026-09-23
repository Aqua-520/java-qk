package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.entity.Course;
import com.wcy.service.CourseSerivice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 通过构造函数注入依赖的注解
@RequiredArgsConstructor
public class CourseController {
    private final CourseSerivice courseSerivice;

    // 编写课程管理接口
    // 查询课程列表的接口
    @GetMapping("/courses")
    Response selectCourseListByLimit(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "subject",required = false) Integer subject,
            @RequestParam(value = "target",required = false) Integer target,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        // 将参数直接传给业务层,业务层返回封装好的分页列表对象
        PageResponse<Course> pageData  = this.courseSerivice.selectCourseListByLimit(name,subject
            ,target,page,pageSize
        );
        return Response.success(pageData);
    }

    // 查询所有课程
    @GetMapping("/courses/list")
    Response selectAllCourseList(){
        List<Course> courseList = this.courseSerivice.selectAllCourseList();

        return Response.success(courseList);
    }

    // 根据id查询课程
    @GetMapping("/courses/{id}")
    Response selectCourseById(@PathVariable("id") Integer courseId){
        // 拿到课程对象
        Course course = this.courseSerivice.selectCourseById(courseId);

        return Response.success(course);

    }

    // 修改
    @PutMapping("/courses")
    Response updateCourse(@RequestBody Course course){
        // 传给业务层做修改
        this.courseSerivice.updateCourse(course);

        return Response.success();
    }

    // 新增
    @PostMapping("/courses")
    Response insertCourse(@RequestBody Course course){
        // 接收请求体对象
        // 传给业务层做新增
        this.courseSerivice.insertCourse(course);

        return Response.success();
    }

    // 删除课程
    @DeleteMapping("/courses/{id}")
    Response deleteCourseById(@PathVariable("id") Integer courseId){
        // id传给业务层作删除
        this.courseSerivice.deleteCourseById(courseId);

        return Response.success();
    }

    // 根据学科查询
    @GetMapping("/courses/subject/{subject}")
    Response selectBySubject(@PathVariable("subject") Integer subjectId){
        // 根据学科id查询
        List<Course> courseList = this.courseSerivice.selectBySubject(subjectId);
        // 返回
        return Response.success(courseList);
    }
}
