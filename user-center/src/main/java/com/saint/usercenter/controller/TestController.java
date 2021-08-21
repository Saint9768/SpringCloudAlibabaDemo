package com.saint.usercenter.controller;

import com.saint.usercenter.dao.user.UserMapper;
import com.saint.usercenter.domain.entity.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-06-30 7:13
 */
@RestController
public class TestController {

    @Resource
    private UserMapper userMapper;


    @GetMapping("test")
    public User testInsert() {
        User user = new User();
        user.setAvatarUrl("xxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);
        return user;
    }

    @GetMapping("/q")
    public User query(User user) {
        return user;
    }
}
