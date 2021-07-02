package com.zaiji.starter.sql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 服务启动日志
 *
 * @author zaiji
 * @date 2021/06/18
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StartLog {

    /**
     * 启动时间
     */
    private Long startTime;

    /**
     * 服务所在ip端口
     */
    private String ipPort;

    /**
     * 服务名
     */
    private String serviceName;

    public LocalDateTime getStartTimeToLocalDateTime() {
        return LocalDateTime.ofEpochSecond(this.startTime / 1000, 0, ZoneOffset.ofHours(8));
    }
}
