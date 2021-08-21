package com.saint.usercenter.domain.entity.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "bonus_event_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusEventLog {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    private Integer value;

    private String event;

    @Column(name = "create_time")
    private Date createTime;

    private String description;
}