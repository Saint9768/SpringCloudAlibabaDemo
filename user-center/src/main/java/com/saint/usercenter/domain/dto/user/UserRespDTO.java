package com.saint.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-16 7:51
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRespDTO {

    private Integer id;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 积分
     */
    private Integer bonus;

    /**
     * 微信名称
     */
    private String wxNickname;
}
