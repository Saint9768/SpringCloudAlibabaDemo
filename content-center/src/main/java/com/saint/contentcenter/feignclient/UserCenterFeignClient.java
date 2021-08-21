package com.saint.contentcenter.feignclient;

import com.saint.contentcenter.configuration.UserCenterFeignConf;
import com.saint.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-06 6:32
 */
@FeignClient(name = "user-center", configuration = UserCenterFeignConf.class)
//@FeignClient(name = "user-center", fallback = UserCenterFallback.class)
//@FeignClient(name = "user-center", fallbackFactory = UserCenterFallbackFactory.class)
public interface UserCenterFeignClient {

//    @GetMapping("/users/{id}")
//    UserDTO findById(@PathVariable("id") Integer id, @RequestHeader("X-Token") String token);

    /**
     * http://user-center/users/{id}
     *  feign调用传输token
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable("id") Integer id);
}
