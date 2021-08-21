package com.saint.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 限流回调方法类
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-08 8:37
 */
@Slf4j
public class TestControllerFallbackHandler {

    /**
     * sentinel 1.5处理降级
     * sentinel 1.6开始可以处理Throwable，即任何异常
     */
    public static String fallbackTest2(String a, Throwable e) {
        log.warn("限流或者降级了！ fallback2", e);
        return "限流或者降级了！ fallback2";
    }
}
