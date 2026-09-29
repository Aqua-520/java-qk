package com.wcy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.entity.Customer;
import com.wcy.mapper.CustomerMapper;
import com.wcy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 将客户实现类给spring容器
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    // 注入Mapper依赖
    private final CustomerMapper customerMapper;
}
