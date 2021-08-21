package com.saint.usercenter.serive.user;

import com.saint.usercenter.dao.user.BonusEventLogMapper;
import com.saint.usercenter.dao.user.UserMapper;
import com.saint.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.saint.usercenter.domain.dto.user.UserLoginDTO;
import com.saint.usercenter.domain.entity.user.BonusEventLog;
import com.saint.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-02 7:02
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO message) {
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

    public User login(UserLoginDTO loginDTO, String wxId) {
        User user = userMapper.selectOne(
                User.builder()
                        .wxId(wxId)
                        .build()
        );
        if (Objects.isNull(user)) {
            User userToSave = User.builder()
                    .wxId(wxId)
                    .bonus(300)
                    .wxNickname(loginDTO.getWxNickname())
                    .avatarUrl(loginDTO.getAvatarUrl())
                    .roles("users")
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            userMapper.insertSelective(userToSave);
            return userToSave;
        }
        return user;
    }
}
