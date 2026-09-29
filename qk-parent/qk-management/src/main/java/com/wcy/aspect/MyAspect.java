package com.wcy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// 切面类
@Component
// @Aspect
@Slf4j
public class MyAspect {

    // 定义切面方法
    @Before("execution(* com.wcy.controller.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        log.info("前置通知执行了");
    }

    // 后置方法
    @After("execution(* com.wcy.controller.*.*(..))")
    public void afterAdvice(JoinPoint point){
        log.info("后置通知执行了");
    }

    //返回后通知：此方法的代码在目标方法正常执行之后执行
    @AfterReturning("execution(* com.wcy.controller.*.*(..))")
    public void afterReturning(JoinPoint joinPoint) {
        log.info("方法返回后执行");
    }

    //异常通知：此方法的代码在目标方法发生异常时执行
    @AfterThrowing("execution(* com.wcy.controller.*.*(..))")
    public void afterThrowing(JoinPoint joinPoint) {
        log.info("异常后执行");
    }
}
