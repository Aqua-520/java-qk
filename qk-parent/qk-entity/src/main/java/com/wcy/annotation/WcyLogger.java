package com.wcy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解模块
 */
// 只能在方法上加注解
@Target({ElementType.METHOD})
// 运行时触发注解
@Retention(RetentionPolicy.RUNTIME)
public @interface WcyLogger {
}