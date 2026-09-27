package com.wcy.utils;

/**
 * 这个类是工具类,从拦截器中截取出用户id后
 * 存入线程中的临时变量上,在当前生命周期时,其他函数可以使用此数据
 */
public class UserHolder {
    // 创建私有的变量,存储进程对象
    private static final ThreadLocal<Integer> Current_user = new ThreadLocal<>();

    // 定于工具方法,对对象做操作
    // 保存用户id
    public static void saveCurrentUserId(Integer userId){
        Current_user.set(userId);
    }

    // 取出用户id
    public static Integer getUserId(){
        return Current_user.get();
    }

    // 清空当前线程的用户信息
    public static void clearUserId(){
        Current_user.remove();
    }
}
