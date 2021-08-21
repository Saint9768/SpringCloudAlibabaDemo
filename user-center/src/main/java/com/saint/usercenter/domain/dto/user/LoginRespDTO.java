package com.saint.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录返回信息类
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-16 7:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginRespDTO {

    private JwtTokenRespDTO token;

    private UserRespDTO user;
}
