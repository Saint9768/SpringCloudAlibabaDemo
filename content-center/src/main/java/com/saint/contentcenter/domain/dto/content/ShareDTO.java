package com.saint.contentcenter.domain.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-02 7:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareDTO {

    private Integer id;

    private String sharecol;

    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private Boolean isOriginal;

    /**
     * 作者
     */
    private String author;

    /**
     * 封面
     */
    private String cover;

    /**
     * 概述
     */
    private String summary;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 购买次数
     */
    private Integer buyCount;

    private Boolean showFlag;

    private String auditStatus;

    private String reason;

    private String wxNickName;


}
