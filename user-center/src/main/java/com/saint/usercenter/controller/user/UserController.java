package com.saint.usercenter.controller.user;

import com.saint.usercenter.auth.CheckLogin;
import com.saint.usercenter.domain.dto.user.JwtTokenRespDTO;
import com.saint.usercenter.domain.dto.user.LoginRespDTO;
import com.saint.usercenter.domain.dto.user.UserLoginDTO;
import com.saint.usercenter.domain.dto.user.UserRespDTO;
import com.saint.usercenter.domain.entity.user.User;
import com.saint.usercenter.serive.user.UserService;
import com.saint.usercenter.util.JwtOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-02 7:03
 */
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtOperator jwtOperator;

    @CheckLogin
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        log.info("我被请求了。。。。");
        return userService.findById(id);
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO userLoginDTO) {
        // 判断用户是否已经注册，没注册就注册
        User user = userService.login(userLoginDTO, "wxid1");

        // 颁发token
        Map<String, Object> userInfo = new HashMap<>(4);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);
        log.info("用户{}登录成功，生成的token为：{}, 有效期到：{}", user.getId(), token, jwtOperator.getExpirationTime());

        // 构建响应
        return LoginRespDTO.builder()
                .user(
                        UserRespDTO.builder()
                                .id(user.getId())
                                .bonus(user.getBonus())
                                .wxNickname(user.getWxNickname())
                                .build()
                )
                .token(JwtTokenRespDTO.builder()
                        .token(token)
                        .expirationTime(jwtOperator.getExpirationTime().getTime())
                        .build())
                .build();
    }
}
