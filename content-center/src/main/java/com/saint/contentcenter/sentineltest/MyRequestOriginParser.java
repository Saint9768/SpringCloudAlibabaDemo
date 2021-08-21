package com.saint.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 针对来源进行限流等。相当于Spring MVC的拦截器
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-11 22:08
 */
//@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        // 从请求中获取名为origin 的参数并返回
        // 如果获取不到origin参数，那么就抛异常
        String origin = httpServletRequest.getParameter("origin");
        if(StringUtils.isBlank(origin)) {
            throw new IllegalArgumentException("origin must be specified");
        }
        return origin;
    }
}
