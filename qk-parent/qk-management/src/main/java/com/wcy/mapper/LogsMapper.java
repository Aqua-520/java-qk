package com.wcy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.entity.OperateLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogsMapper extends BaseMapper<OperateLog> {
    Page<OperateLog> selectLogPage(Page<OperateLog> page);
}
