package com.saint.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.saint.contentcenter.dao.content.RocketmqTransactionLogMapper;
import com.saint.contentcenter.domain.dto.content.ShareAuditDTO;
import com.saint.contentcenter.domain.entity.content.RocketmqTransactionLog;
import com.saint.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Objects;

/**
 * 执行本地事务
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-13 7:11
 */
@RocketMQTransactionListener(txProducerGroup = "tx-producer-test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    private final ShareService shareService;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    /**
     * 执行本地事务
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) headers.get("share_id"));
        // 当切换为Source发消息之后，此处的o为空，我们需要从headers中去dto
        // headers中拿到的只能是字符串
        String dtoString = (String) headers.get("dto");
        ShareAuditDTO shareAuditDTO = JSON.parseObject(dtoString, ShareAuditDTO.class);
        try {
//            shareService.auditByIdWithRocketMqLog(shareId, (ShareAuditDTO) o, transactionId);
            shareService.auditByIdWithRocketMqLog(shareId, shareAuditDTO, transactionId);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 消息回查
     * 检查本地事务的状态，要有数据源去给RocketMQ检验
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        // 查询本地记录的事务执行日志
        RocketmqTransactionLog rocketmqTransactionLog = rocketmqTransactionLogMapper.selectOne(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .build()
        );
        if (Objects.nonNull(rocketmqTransactionLog)) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
