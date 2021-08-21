package com.saint.usercenter.rocketmq;

import com.saint.usercenter.dao.user.BonusEventLogMapper;
import com.saint.usercenter.dao.user.UserMapper;
import com.saint.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.saint.usercenter.domain.entity.user.BonusEventLog;
import com.saint.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-12 21:15
 */
@Service
@RocketMQMessageListener(consumerGroup = "saint-consumer", topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
//public class AddBonusListener {
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMsgDTO message) {
        // 当收到消息的时候，执行的业务
        // 1. 为用户加积分
        Integer userId = message.getUserId();
        Integer bonus = message.getBonus();
        User user = userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + bonus);
        userMapper.updateByPrimaryKey(user);

        // 2.记录日志到bonus_event_log表里面
        bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(String.valueOf(userId))
                .value(bonus)
                .event("contribute")
                .description("投稿。。。 加积分")
                .createTime(new Date())
                .build());
        log.info("积分添加完毕");
    }
}
