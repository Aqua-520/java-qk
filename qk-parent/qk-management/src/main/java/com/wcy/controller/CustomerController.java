package com.wcy.controller;

import com.wcy.annotation.WcyLogger;
import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.dto.CustomerQueryDTO;
import com.wcy.dto.CustomerSaveDTO;
import com.wcy.entity.Customer;
import com.wcy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// 客户管理的接口
@RequiredArgsConstructor
@RestController
public class CustomerController {
    // 接收业务层依赖
    private final CustomerService customerService;

    // 查询客户列表
    @WcyLogger
    @GetMapping("/customers")
    public Response selectCustomerListByLimit(CustomerQueryDTO queryDTO){
        PageResponse<Customer> pageResponse = this.customerService.selectCustomerListByLimit(queryDTO);

        return Response.success(pageResponse);
    }

    // 根据id查询客户
    @GetMapping("/customers/{id}")
    public Response selectCustomerById(@PathVariable("id") Integer customerId){
        Customer customer = this.customerService.selectCustomerById(customerId);

        return Response.success(customer);
    }

    // 添加客户
    @PostMapping("/customers")
    public Response addCustomer(@RequestBody CustomerSaveDTO saveDTO){
        this.customerService.addCustomer(saveDTO);

        return Response.success();
    }
}
