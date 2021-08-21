//package com.saint.contentcenter.feignclient.fallback;
//
//import com.saint.contentcenter.domain.dto.user.UserDTO;
//import com.saint.contentcenter.feignclient.UserCenterFeignClient;
//import org.springframework.stereotype.Component;
//
///**
// * @author Saint
// * @version 1.0
// * @createTime 2021-07-10 11:30
// */
//@Component
//public class UserCenterFallback implements UserCenterFeignClient {
//    @Override
//    public UserDTO findById(Integer id) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setWxNickname("fallback");
//        return userDTO;
//    }
//}
