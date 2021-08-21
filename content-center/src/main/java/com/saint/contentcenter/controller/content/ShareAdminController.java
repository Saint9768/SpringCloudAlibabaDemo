package com.saint.contentcenter.controller.content;

import com.saint.contentcenter.domain.dto.content.ShareAuditDTO;
import com.saint.contentcenter.domain.entity.content.Share;
import com.saint.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-12 6:18
 */
@RestController
@RequestMapping("/admin/shares")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareAdminController {

    private final ShareService shareService;

    /**
     * 审核文章
     *
     * @param id       文章ID
     * @param auditDTO 审核信息
     * @return
     */
    @PutMapping("/audit/{id}")
    public Share auditById(@PathVariable("id") Integer id, @RequestBody ShareAuditDTO auditDTO) {
        // TODO 认证、授权
        return shareService.auditById(id, auditDTO);
    }
}
