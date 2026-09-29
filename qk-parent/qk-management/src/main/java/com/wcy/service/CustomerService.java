package com.wcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.dto.CustomerQueryDTO;
import com.wcy.dto.CustomerSaveDTO;
import com.wcy.entity.Customer;

// 接口继承service接口的方法
public interface CustomerService extends IService<Customer> {
    PageResponse<Customer> selectCustomerListByLimit(CustomerQueryDTO queryDTO);

    Customer selectCustomerById(Integer customerId);

    void addCustomer(CustomerSaveDTO saveDTO);
}
