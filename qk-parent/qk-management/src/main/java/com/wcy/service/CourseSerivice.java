package com.wcy.service;

import com.wcy.common.PageResponse;
import com.wcy.entity.Course;

import java.util.List;

public interface CourseSerivice {
    PageResponse<Course> selectCourseListByLimit(String name, Integer subject, Integer target, Integer page, Integer pageSize);

    List<Course> selectAllCourseList();

    Course selectCourseById(Integer courseId);

    void updateCourse(Course course);

    void insertCourse(Course course);

    void deleteCourseById(Integer courseId);

    List<Course> selectBySubject(Integer subjectId);
}
