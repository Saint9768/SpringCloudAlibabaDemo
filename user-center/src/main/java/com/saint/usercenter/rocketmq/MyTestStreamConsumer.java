package com.saint.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-13 8:17
 */
@Service
@Slf4j
public class MyTestStreamConsumer {

//    @StreamListener(MySink.MY_INPUT)
//    public void receive(String massageBody) {
//        log.info("自定义接口消费，通过stream收到了消息: messageBody={}", massageBody);
//        throw new IllegalArgumentException("抛异常");
//    }

    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        log.error("发生异常: {}", errorMessage);
    }

}
