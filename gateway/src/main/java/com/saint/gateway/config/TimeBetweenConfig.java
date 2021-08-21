package com.saint.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-14 7:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeBetweenConfig {

    private LocalTime start;

    private LocalTime end;
}
