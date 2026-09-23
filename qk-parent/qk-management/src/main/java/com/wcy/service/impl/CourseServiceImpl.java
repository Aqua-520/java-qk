package com.wcy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.common.PageResponse;
import com.wcy.entity.Course;
import com.wcy.mapper.CourseMapper;
import com.wcy.mapper.DeptMapper;
import com.wcy.service.CourseSerivice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseSerivice {
    // 注入数据库访问层对象
    private final CourseMapper courseMapper;

    @Override
    public PageResponse<Course> selectCourseListByLimit(String name, Integer subject, Integer target, Integer page, Integer pageSize) {
        // 根据传入的条件编写sql
        LambdaQueryWrapper<Course> lambdaQueryWrapper = Wrappers.lambdaQuery(Course.class)
                .like(StrUtil.isNotBlank(name), Course::getName, name)
                .eq(subject != null, Course::getSubject, subject)
                .eq(target != null, Course::getSubject, target);

        // 分页查询
        Page<Course> coursePage = this.courseMapper.selectPage(
                new Page<>(page, pageSize),
                lambdaQueryWrapper
        );

        // 封装分页对象
        // 3. 组装 PageResponse 返回
        return new PageResponse<>(
                coursePage.getTotal(),
                coursePage.getRecords()
        );

    }

    @Override
    public List<Course> selectAllCourseList() {
        // 查询所有数据
        List<Course> list = this.courseMapper.selectList(Wrappers.emptyWrapper());
        return list;
    }

    @Override
    public Course selectCourseById(Integer courseId) {
        // 根据id查询单条课程
        Course course = this.courseMapper.selectById(courseId);
        return course;
    }

    @Override
    public void updateCourse(Course course) {
        // 判断有没有id
        if (course.getId() == null){
            // 如果没有id则抛异常
            throw new RuntimeException("没有查询到对应的课程id,更新失败");
        }
        // 修改更新时间
        course.setUpdateTime(LocalDateTime.now());
        // 将对象写入覆盖即可
        this.courseMapper.updateById(course);
    }

    @Override
    public void insertCourse(Course course) {
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        course.setCreateTime(now);
        course.setUpdateTime(now);
        // 做新增操作
        this.courseMapper.insert(course);

    }

    @Override
    public void deleteCourseById(Integer courseId) {
        // 根据id删除
        this.courseMapper.deleteById(courseId);
    }

    @Override
    public List<Course> selectBySubject(Integer subjectId) {
        if (subjectId == null) {
            throw new IllegalArgumentException("subjectId 不能为空");
        }
        return this.courseMapper.selectList(
                Wrappers.lambdaQuery(Course.class)
                        .eq(Course::getSubject, subjectId)
                        .orderByAsc(Course::getId)
        );
    }
}
