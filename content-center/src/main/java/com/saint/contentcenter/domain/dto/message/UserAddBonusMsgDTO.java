package com.saint.contentcenter.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-12 7:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddBonusMsgDTO {

    private Integer userId;

    /**
     * 积分
     */
    private Integer bonus;
}
