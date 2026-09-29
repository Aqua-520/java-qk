package com.wcy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.CustomerQueryDTO;
import com.wcy.dto.CustomerSaveDTO;
import com.wcy.entity.Customer;
import com.wcy.exception.DataNotFoundException;
import com.wcy.mapper.CustomerMapper;
import com.wcy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

// 将客户实现类给spring容器
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    // 注入Mapper依赖
    private final CustomerMapper customerMapper;

    /**
     * 查询列表分页版
     * @param queryDTO 查询参数封装对象
     * @return 返回的分页对象
     */
    @Override
    public PageResponse<Customer> selectCustomerListByLimit(CustomerQueryDTO queryDTO) {
        Page<Customer> pageInfo = this.customerMapper.selectCustomerListByLimit(
                new Page<Customer>(queryDTO.getPage(),queryDTO.getPageSize()),
                queryDTO
        );

        return new PageResponse<>(pageInfo.getTotal(),pageInfo.getRecords());
    }

    /**
     * 根据id查询客户详情
     * @param customerId 客户id
     * @return 返回客户对象
     */
    @Override
    public Customer selectCustomerById(Integer customerId) {
        Customer customer = this.customerMapper.selectById(customerId);
        if(Objects.isNull(customer)){
            throw new DataNotFoundException("查询客户详情失败");
        }
        return customer;
    }

    /**
     * 将dto封装成表对象保存
     * @param saveDTO 前端dto
     */
    @Override
    public void addCustomer(CustomerSaveDTO saveDTO) {
        // copy对象
        Customer customer = new Customer();
        BeanUtil.copyProperties(saveDTO,customer);

        // 没有需要处理的字段直接保存,mybatis会自动填充时间,数据库id自增
        this.customerMapper.insert(customer);
    }
}
