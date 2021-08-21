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
public class TestControllerBlockHandler {

    /**
     * 限流回调方法
     */
    public static String blockTest2(String a, BlockException e) {
        log.warn("限流或者降级了！ block2", e);
        return "限流或者降级了！ block2";
    }
}
