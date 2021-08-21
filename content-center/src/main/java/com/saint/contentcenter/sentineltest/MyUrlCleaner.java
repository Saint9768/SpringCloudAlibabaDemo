package com.saint.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 支持RESTFul-URL
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-11 22:19
 */
@Slf4j
@Component
public class MyUrlCleaner implements UrlCleaner {
    @Override
    public String clean(String originUrl) {
        // 让 /shares/1与/shares/2的返回值相同
        // 返回/shares/{number}
        String[] split = originUrl.split("/");
        Arrays.stream(split)
                .map(string -> {
                    if(NumberUtils.isNumber(string)) {
                        return "{number}";
                    }
                    return string;
                })
                .reduce((a, b) -> a + "/" + b)
                .orElse("");

        return originUrl;
    }
}
