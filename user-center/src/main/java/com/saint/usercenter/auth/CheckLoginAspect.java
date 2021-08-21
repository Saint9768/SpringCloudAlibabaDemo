package com.saint.usercenter.auth;

import com.saint.usercenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * checkLogin切面
 *
 * @author 周鑫(玖枭)
 */
@Aspect
@Component
public class CheckLoginAspect {

    @Autowired
    private JwtOperator jwtOperator;

    @Around("@annotation(com.saint.usercenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        try {
            // 1. 从Header中获取Token
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("X-Token");

            // 2.校验校验token是否合法
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new SecurityException("Token不合法");
            }

            // 3. 如果校验成功，那么将用户的信息设置到request的attribute里面
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.getId());
            request.setAttribute("wxNickname", claims.get("wxNickname"));
            request.setAttribute("role", claims.get("wxNickname"));

        } catch (Throwable e) {
            // ignore exception
            throw new SecurityException("Token不合法");
        }
        return point.proceed();
    }
}