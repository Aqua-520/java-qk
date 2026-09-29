package com.wcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.dto.CustomerQueryDTO;
import com.wcy.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    Page<Customer> selectCustomerListByLimit(Page<Customer> customerPage, CustomerQueryDTO queryDTO);
}
