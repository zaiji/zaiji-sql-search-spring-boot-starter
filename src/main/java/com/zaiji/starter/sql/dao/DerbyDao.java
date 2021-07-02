package com.zaiji.starter.sql.dao;

import com.zaiji.starter.sql.entity.ReceiverLog;
import com.zaiji.starter.sql.entity.StartLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * derby数据库操作
 *
 * @author zaiji
 * @date 2021/06/18
 */

@Mapper
public interface DerbyDao {

    /**
     * 获得最后一条成功启动的日志信息
     *
     * @return 启动日志信息
     */
    StartLog getLastStartLog();

    /**
     * 插入数据库启动日志
     *
     * @param startLog 启动日志
     */
    @Insert("insert into wisvision_start_log(service_name,ip_port,start_time) values(#{startLog.serviceName},#{startLog.ipPort},#{startLog.startTime})")
    void insertStartLog(@Param("startLog") StartLog startLog);

    /**
     * 执行任意sql，方便直接在页面查询日志信息
     *
     * @param sql sql语句
     * @return 查询结果
     */
    List<Map<String, Object>> doAnySQL(String sql);

    /**
     * 插入接入日志
     *
     * @param receiverLog 接入日志信息
     */
    void insertReceiverLog(@Param("receiverLog") ReceiverLog receiverLog);

    /**
     * 查询接入日志
     *
     * @return 所有的接入日志信息
     */
    List<ReceiverLog> getReceiverLogs();
}
