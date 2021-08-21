package com.saint.contentcenter.domain.dto.user;

import lombok.*;

import java.util.Date;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-02 7:17
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;

    /**
     * 微信ID
     */
    private String wxId;

    /**
     * 微信昵称
     */
    private String wxNickname;

    /**
     * 角色
     */
    private String roles;

    /**
     * 头像地址
     */
    private String avatarUrl;

    private Date createTime;

    private Date updateTime;

    private Integer bonus;
}
