package com.saint.contentcenter.service.content;

import com.alibaba.fastjson.JSONObject;
import com.saint.contentcenter.dao.content.RocketmqTransactionLogMapper;
import com.saint.contentcenter.dao.content.ShareMapper;
import com.saint.contentcenter.domain.dto.content.ShareAuditDTO;
import com.saint.contentcenter.domain.dto.content.ShareDTO;
import com.saint.contentcenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.saint.contentcenter.domain.dto.user.UserDTO;
import com.saint.contentcenter.domain.entity.content.RocketmqTransactionLog;
import com.saint.contentcenter.domain.entity.content.Share;
import com.saint.contentcenter.domain.enums.AuditStatusEnum;
import com.saint.contentcenter.feignclient.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-02 7:12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    private final UserCenterFeignClient userCenterFeignClient;
    private final RocketMQTemplate rocketMQTemplate;
    private final RestTemplate restTemplate;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;
    private final Source source;

    public ShareDTO findById(Integer id) {
        Share share = shareMapper.selectByPrimaryKey(id);
        // 获取发布人ID
        Integer userId = share.getUserId();

        // 方式一：自己实现负载均衡
        // 用户中心所有实例的信息
//        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
//        List<String> targetURLs = instances.stream().map(instance -> instance.getUri().toString() + "/users/{id}")
//                .collect(Collectors.toList());
//        int i = ThreadLocalRandom.current().nextInt(targetURLs.size());
//        String targetURL = targetURLs.get(i);
//        log.info("targetURL: {}", targetURL);

        // 方式二：使用Ribbon实现负载均衡
        // 问题：
        // 1. 代码不可读
        // 2. 复杂URL难以维护
        // 3.难以相应需求的变化，变化很难受
        // 4.编程体验不统一。
//        UserDTO user = restTemplate.getForObject("http://user-center/users/{id}", UserDTO.class, userId);

        // 方式三：使用Feign实现负载均衡
        UserDTO user = userCenterFeignClient.findById(userId);

        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickName(user.getWxNickname());
        return shareDTO;
    }

    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 查询share是否存在，audit_status == not_yet
        Share share = shareMapper.selectByPrimaryKey(id);
        if (Objects.isNull(share)) {
            throw new IllegalArgumentException("参数非法，该分享不存在！");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法，该分享已审核！");
        }
        // 如果是PASS，那么发送消息给RocketMQ，让用户中心去消费，并为发布人
        if (Objects.equals(AuditStatusEnum.PASS, auditDTO.getAuditStatusEnum())) {

            // 发送半消息
            String transactionId = UUID.randomUUID().toString();
//            source.output().send(MessageBuilder.withPayload(
//                    UserAddBonusMsgDTO.builder().bonus(50)
//                            .userId(share.getUserId())
//                            .build())
//                    // header也有妙用
//                    .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
//                    .setHeader("share_id", id)
//                    .setHeader("dto", JSONObject.toJSONString(auditDTO))
//                    .build());

            this.rocketMQTemplate.sendMessageInTransaction("tx-producer-test", "add-bonus", MessageBuilder.withPayload(
                    UserAddBonusMsgDTO.builder().bonus(50)
                            .userId(share.getUserId())
                            .build())
                            // header也有妙用
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("share_id", id)
                            .setHeader("dto", JSONObject.toJSONString(auditDTO))
                            .build(),
                    // 此处的arg有大用处
                    auditDTO);
        } else {
            this.auditById(id, auditDTO);
        }

        return share;
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder().id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason())
                .build();
        shareMapper.updateByPrimaryKeySelective(share);

        // 最后写Redis缓存
    }

    /**
     * 修改数据并记录日志
     *
     * @param id            文章ID
     * @param auditDTO      文章内容
     * @param transactionId 事务ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
        auditByIdInDB(id, auditDTO);
        rocketmqTransactionLogMapper.insertSelective(RocketmqTransactionLog.builder()
                .transactionId(transactionId)
                .log("审核分享.....").build());
    }
}
