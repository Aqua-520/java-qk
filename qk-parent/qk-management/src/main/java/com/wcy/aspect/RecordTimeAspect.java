package com.wcy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


// 切片类,记录每个方法的耗时
@Component
@Aspect
@Slf4j
public class RecordTimeAspect {
    // 编写切片方法
    // around注解里面写接管哪一个包下的哪一个类里面的方法,指定接管的返回值和方法参数类型
    // "execution(* com.wcy.controller.*.*(..))" 为切入点表达式,*匹配任意单个元素,..任意参数
    // @Around("execution(* com.wcy.controller.*.*(..))")
    @Around("@annotation(com.wcy.annotation.WcyLogger)")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录方法开始时间
        long startTime = System.currentTimeMillis();

        // 调用joinPoint方法
        // 这里的object就是被切片的方法执行后的返回值
        // proceed就是执行被接管的函数
        Object object = joinPoint.proceed();

        // 结束时间
        long endTime = System.currentTimeMillis();

        log.info("方法执行耗时:{}ms",(endTime - startTime));
        return object;
    }
}
