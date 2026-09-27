package com.wcy.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.wcy.utils.JwtUtil;
import com.wcy.utils.UserHolder;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 登录拦截器类,需要实现HandlerInterceptor 拦截器接口
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 前置拦截 : 在控制层接口方法(目标方法)执行之前执行
     *
     * @param request  本次的HTTP请求对象
     * @param response 本次的HTTP响应对象
     * @param handler  拦截的目标方法对象
     * @return true: 放行到目标资源  false : 不放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginInterceptor中的preHandle方法执行了.....");
        // 将token拿出来
        String token = request.getHeader("token");

        // 判断token是否为空
        if(StrUtil.isBlank(token)){
            // 如果为空,抛出401状态码返回给前端,并且做拦截
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }
        // 对token做解密,如果通过则放行
        try {
            // 因为如果失败的话,解密token工具类会抛出异常
            Claims claims = JwtUtil.parseToken(token);
            // 从解密的载荷中获取用户id
            Integer id = claims.get("id",Integer.class);

            // 把id绑到线程上供后续函数使用
            UserHolder.saveCurrentUserId(id);

        }catch (Exception e) {
            log.error("令牌校验失败,原始令牌:{},失败原因:{}", token, e.getMessage());
            // 设置状态码并且拦截
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }

        // 以上都没问题则放行
        return true;
    }

    /**
     * 后置拦截 : 在目标方法执行之后执行
     *
     * @param request      本次的HTTP请求对象
     * @param response     本次的HTTP响应对象
     * @param handler      拦截的目标方法对象
     * @param modelAndView the {@code ModelAndView} that the handler returned
     *                     (can also be {@code null})
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("LoginInterceptor中的postHandle方法执行了.....");
    }

    /**
     * 完成时拦截 : 在服务器响应完毕之后执行
     *
     * @param request  本次的HTTP请求对象
     * @param response 本次的HTTP响应对象
     * @param handler  拦截的目标方法对象
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("LoginInterceptor中的afterCompletion方法执行了.....");
        // 请求结束后,从线程池中删除用户id,避免挤占内存空间
        UserHolder.clearUserId();
    }
}
