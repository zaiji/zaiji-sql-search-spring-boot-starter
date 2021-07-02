package com.zaiji.starter.sql.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

/**
 * 接入日志pojo对象
 *
 * @author zaiji
 * @date 2021/06/18
 */

@Data
@Accessors(chain = true)
@Log4j2
public class ReceiverLog {
    public ReceiverLog() {
        this.receiverTime = System.currentTimeMillis();
    }

    /**
     * 接收数据时间戳
     */
    private long receiverTime;

    /**
     * 处理数据共消耗时长
     */
    private long processingTime;

    /**
     * 数据处理完毕时间戳
     */
    private long completionTime;

    /**
     * 接受数据的状态，是否成功接收数据
     */
    private Status receiverStatus;

    /**
     * 数据处理的状态，是否成功处理数据
     */
    private Status handleStatus;

    /**
     * 接收数据的数据源信息（例如数据库，mq信息等）
     */
    private BaseDataSourceInfo dataSourceInfo;

    /**
     * 接入数据源类型
     */
    private DataSourceType dataSourceType;

    /**
     * 接收数据的数据源信息（例如数据库，mq信息等）的json格式字符串
     */
    private String dataSourceInfoJsonText;

    /**
     * 接收到的数据信息
     */
    private String receiverDataContext;

    /**
     * 处理时的信息，是否成功处理
     */
    private String handleInfo;

    /**
     * 其他备注信息
     */
    private String otherInfo;

    public String getDataSourceInfoJsonText() {
        try {
            return new ObjectMapper().writeValueAsString(dataSourceInfo);
        } catch (Exception e) {
            log.error("格式化json字符串失败，{}", e.getMessage());
        }
        return toString();
    }

    public ReceiverLog setDataSourceInfoJsonText(String dataSourceInfoJsonText, Class<? extends BaseDataSourceInfo> clazz) {
        try {
            this.dataSourceInfo = new ObjectMapper().readValue(dataSourceInfoJsonText, clazz);
        } catch (Exception e) {
            log.warn("接入数据源json格式字符串转对象失败：{}", e.getMessage());
        }
        this.dataSourceInfoJsonText = dataSourceInfoJsonText;
        return this;
    }

    @Override
    public String toString() {
        return "ReceiverLog{" +
                "receiverTime=" + receiverTime +
                ", processingTime=" + processingTime +
                ", completionTime=" + completionTime +
                ", receiverStatus=" + receiverStatus +
                ", handleStatus=" + handleStatus +
                ", dataSourceInfo=" + dataSourceInfo +
                ", receiverDataContext='" + receiverDataContext + '\'' +
                ", handleInfo='" + handleInfo + '\'' +
                ", otherInfo='" + otherInfo + '\'' +
                '}';
    }
}
