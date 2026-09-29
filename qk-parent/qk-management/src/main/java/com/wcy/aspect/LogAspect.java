package com.wcy.aspect;

import cn.hutool.json.JSONUtil;
import com.wcy.entity.OperateLog;
import com.wcy.mapper.OperateLogMapper;
import com.wcy.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// 自定义切片类
// 我们通过这个类来实现对指定业务的切片操作
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LogAspect {
    private final OperateLogMapper operateLogMapper;

    // 监控接口的执行情况
    @Around("@annotation(com.wcy.annotation.WcyLogger)")
    public Object controllerLogAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 收集日志需要的字段信息

        // 操作人id
        Integer userId = UserHolder.getUserId();
        // 操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        // 被操作的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 被执行的方法签名
        String methodName = joinPoint.getSignature().getName();

        // 被执行的方法参数
        Object[] args = joinPoint.getArgs();
        // 转字符串
        String methodArgs = JSONUtil.toJsonStr(args);
        
        // 执行目标方法
        // 执行开始时间
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        // 执行结束时间
        long endTime = System.currentTimeMillis();

        // 将函数执行的返回结果json化一下
        String returnValue = JSONUtil.toJsonStr(result);

        // 封装数据库对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateUserId(userId);
        operateLog.setOperateTime(operateTime);
        operateLog.setClassName(className);
        operateLog.setMethodName(methodName);
        operateLog.setMethodParams(methodArgs);
        operateLog.setReturnValue(returnValue);
        // 设置执行耗时
        operateLog.setCostTime(endTime - startTime);

        // 保存到数据库
        this.operateLogMapper.insert(operateLog);
        return result;
    }
}
