package com.saint.contentcenter.domain.dto.content;

import com.saint.contentcenter.domain.enums.AuditStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-12 6:19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareAuditDTO {

    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;

    /**
     * 审核不通过的原因
     */
    private String reason;

}
