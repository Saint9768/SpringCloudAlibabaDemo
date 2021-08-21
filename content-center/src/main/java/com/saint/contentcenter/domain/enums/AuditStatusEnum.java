package com.saint.contentcenter.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-12 6:21
 */
@Getter
@AllArgsConstructor
public enum  AuditStatusEnum {

    /**
     * 待审核
     */
    NOT_YET,

    /**
     * 审核通过
     */
    PASS,

    /**
     * 审核不通过
     */
    REJECT;
}
