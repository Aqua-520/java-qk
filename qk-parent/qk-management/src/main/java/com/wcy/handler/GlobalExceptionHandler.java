package com.wcy.handler;

import com.wcy.common.Response;
import com.wcy.exception.BusinessException;
import com.wcy.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理类
// 里面写关于异常匹配的方法
@RestControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {
    // 捕获自定义异常
    @ExceptionHandler(DataNotFoundException.class)
    public Response dataNotFoundHandler(DataNotFoundException e){
        e.printStackTrace();
        // 打印日志
        log.error("程序执行出现异常,异常原因:{}", e.getMessage());
        // 输出错误信息给前端响应
        return Response.error(e.getMessage());
    }

    // 捕获key错误
    @ExceptionHandler(DuplicateKeyException.class)
    public Response keyHandler(DuplicateKeyException e){
        // 检测错误信息
        if(e.getMessage().contains("dept.name")){
            // 打印日志
            log.error("部门名称已存在");
            return Response.error("部门名称已存在");
        }

        return Response.error("操作失败,请联系管理员");
    }

    // 打印业务异常
    @ExceptionHandler(BusinessException.class)
    public Response businessHandler(BusinessException e){
        // 打印异常调用栈
        e.printStackTrace();
        // 打印日志
        log.error("业务功能出现异常:{}",e.getMessage());

        return Response.error("业务异常:" + e.getMessage());
    }

    // 打印全局日志
    @ExceptionHandler(Exception.class)
    public Response globalHandler(Exception e){
        // 打印日志
        log.error("服务器出现错误,异常原因:{}",e);

        // 返回错误响应
        return Response.error("服务器异常,等稍后重试");
    }
}
