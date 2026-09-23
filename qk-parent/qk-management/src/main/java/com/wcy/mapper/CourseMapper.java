package com.wcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.entity.Course;
import org.apache.ibatis.annotations.Mapper;

//  操作数据库的mybatisplus的父接口
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
