package com.wcy.common;

import lombok.Data;

/**
 * 后端统一返回结果
 */
@Data
public class Response {

    private Integer code; //编码：1成功，0为失败
    private String msg; //错误信息
    private Object data; //数据

    public static Response success() {
        Response result = new Response();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Response success(Object object) {
        Response result = new Response();
        result.data = object;
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Response error(String msg) {
        Response result = new Response();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}