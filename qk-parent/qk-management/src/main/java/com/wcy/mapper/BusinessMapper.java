package com.wcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.dto.BusinessQueryDTO;
import com.wcy.entity.Business;
import com.wcy.vo.BusinessVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessMapper extends BaseMapper<Business> {
    Page<Business> selectBusinessListByLimit(IPage<Business> objectPage, BusinessQueryDTO businessQueryDTO);

    // 去xml中实现
    BusinessVO selectBusinessById(Integer businessId);
}
