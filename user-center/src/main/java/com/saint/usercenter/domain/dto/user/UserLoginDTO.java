package com.saint.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-16 7:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {

    private String code;

    private String avatarUrl;

    private String wxNickname;
}
