package com.saint.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 这里的feign name可以随便写
 * @author Saint
 */
@FeignClient(name = "baidu", url="http://www.baidu.com")
public interface TestBaiduFeign {

    @GetMapping("")
    String idnex();
}
