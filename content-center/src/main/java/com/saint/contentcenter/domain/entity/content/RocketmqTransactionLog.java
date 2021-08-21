package com.saint.contentcenter.domain.entity.content;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@ToString
@Table(name = "rocketmq_transaction_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketmqTransactionLog {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 事务ID
     */
    @Column(name = "transaction_Id")
    private String transactionId;

    /**
     * 日志
     */
    private String log;
}