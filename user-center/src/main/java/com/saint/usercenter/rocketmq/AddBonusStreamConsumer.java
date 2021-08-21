//package com.saint.usercenter.rocketmq;
//
//import com.saint.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
//import com.saint.usercenter.serive.user.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.stereotype.Service;
//
///**
// * spring cloud stream 消费消息的方式
// * @author Saint
// * @version 1.0
// * @createTime 2021-07-13 8:17
// */
//@Service
//@Slf4j
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//public class AddBonusStreamConsumer {
//
//    private final UserService userService;
//
//    @StreamListener(Sink.INPUT)
//    public void receive(UserAddBonusMsgDTO message) {
//        // 当收到消息的时候，执行的业务
//        userService.addBonus(message);
//    }
//}
