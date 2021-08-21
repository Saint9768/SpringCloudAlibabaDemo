//package com.saint.usercenter.rocketmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.stereotype.Service;
//
///**
// * @author Saint
// * @version 1.0
// * @createTime 2021-07-13 8:17
// */
//@Service
//@Slf4j
//public class TestStreamConsumer {
//
//    @StreamListener(Sink.INPUT)
//    public void receive(String massageBody) {
//        log.info("通过stream收到了消息: messageBody={}", massageBody);
//    }
//}
