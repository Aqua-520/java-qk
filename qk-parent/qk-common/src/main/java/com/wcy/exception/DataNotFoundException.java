package com.wcy.exception;

// 运行时异常
public class DataNotFoundException extends RuntimeException {
    // 构造器,将消息传给父类
    public DataNotFoundException(String message) {
        super(message);
    }
}
