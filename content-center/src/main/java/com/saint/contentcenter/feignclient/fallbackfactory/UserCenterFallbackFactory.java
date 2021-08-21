//package com.saint.contentcenter.feignclient.fallbackfactory;
//
//import com.saint.contentcenter.domain.dto.user.UserDTO;
//import com.saint.contentcenter.feignclient.UserCenterFeignClient;
//import feign.hystrix.FallbackFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * @author Saint
// * @version 1.0
// * @createTime 2021-07-10 11:49
// */
//@Component
//@Slf4j
//public class UserCenterFallbackFactory implements FallbackFactory<UserCenterFeignClient> {
//
//    @Override
//    public UserCenterFeignClient create(Throwable throwable) {
//        return id -> {
//            log.warn("远程调用user-center被限流/降级了", throwable);
//            UserDTO userDTO = new UserDTO();
//            userDTO.setWxNickname("fallback");
//            return userDTO;
//        };
//    }
//}
