package org.csu.jpetstore.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.annotation.Authorization;
import org.csu.jpetstore.service.AccountService;
import org.csu.jpetstore.service.JwtService;
import org.csu.jpetstore.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * token过滤器 验证token有效性
 */
public class JwtAuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    JwtService jwtService;
    @Autowired
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String authorization = request.getHeader("authorization");
        //如果jwt有效
        if (authorization != null && !authorization.isEmpty() && jwtService.isJwtValid(authorization)) {
            String username = JwtUtil.decode(authorization, JwtUtil.SECRET).get("name").asString();
            //将username加入request
            request.setAttribute("username", username);
            return true;
        }

        //有 @Authorization 注解,且jwt无效
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject res = new JSONObject();
            res.put("status", false);
            res.put("msg", "token无效");
            res.put("data", null);
            PrintWriter out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();
            return false;
        }
        return true;
    }
}
