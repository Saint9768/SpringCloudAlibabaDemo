package com.saint.contentcenter.controller.content;

import com.saint.contentcenter.auth.CheckAuthorization;
import com.saint.contentcenter.auth.CheckLogin;
import com.saint.contentcenter.domain.dto.content.ShareDTO;
import com.saint.contentcenter.service.content.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-02 7:41
 */
@RestController
@RequestMapping("/shares")
public class ShareController {
    @Autowired
    private ShareService shareService;

    /**
     * @RequestHeader("X-Token") String token 从请求头中获取X-Token参数
     */
    @GetMapping("/{id}")
    @CheckLogin
    @CheckAuthorization("admin")
    public ShareDTO findById(@PathVariable Integer id) {
        return shareService.findById(id);
    }
}
