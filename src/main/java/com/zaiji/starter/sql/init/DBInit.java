package com.zaiji.starter.sql.init;

import com.zaiji.starter.sql.dao.DerbyDao;
import com.zaiji.starter.sql.entity.StartLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Objects;

/**
 * 数据库初始化
 *
 * @author zaiji
 * @date 2021/06/17
 */
@Log4j2
public final class DBInit {

    /**
     * 系统启动日志表
     */
    private final String createStartLogTableSql = "\n" +
            "CREATE TABLE wisvision_start_log  (\n" +
            "  start_time bigint not null,\n" +
            "  ip_port varchar(100) not null,\n" +
            "  service_name varchar(255) not null\n" +
            ")";

    /**
     * 接入数据日志表
     */
    private String createReceiverLogTableSql = "\n" +
            "create table wisvision_receiver_log(\n" +
            "  receiver_time bigint not null,\n" +
            "  processing_time bigint,\n" +
            "  completion_time bigint,\n" +
            "  receiver_status varchar(20) not null,\n" +
            "  handle_status varchar(20),\n" +
            "  data_source_type varchar(20),\n" +
            "  data_source_info_json_text clob,\n" +
            "  receiver_data_context clob,\n" +
            "  handle_info clob,\n" +
            "  other_info clob\n" +
            ")";

    /**
     * dao层
     */
    private final DerbyDao derbyDao;

    /**
     * 端口号
     */
    private String port;

    private String serviceName;

    private final Environment environment;

    /**
     * ip地址
     */
    private String ip = "unknown";


    {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.error("获取ip失败,{}", e.getMessage());
        }
    }

    /**
     * 构造函数
     */
    public DBInit(DerbyDao derbyDao, Environment environment, String serviceName) {
        this.derbyDao = derbyDao;
        this.environment = environment;
        this.serviceName = serviceName;
        port = environment.getProperty("server.port");
    }

    public void run() throws Exception {
        log.info("****************************************************************************************************************************");

        StartLog lastStartLog = null;
        try {
            //查询最后一条启动记录
            lastStartLog = derbyDao.getLastStartLog();
        } catch (Exception e) {
            //查询失败，表不存在，数据库初始化
            initDB();
        }

        if (Objects.nonNull(lastStartLog)) {
            log.info("上次启动信息：【启动时间：{}； 启动ip端口：{}； 启动服务名：{}】", lastStartLog.getStartTimeToLocalDateTime(), lastStartLog.getIpPort(), lastStartLog.getServiceName());
        } else {
            log.info("本次是第一次启动本服务，欢迎使用！");
        }
        StartLog startLog = new StartLog(System.currentTimeMillis(), ip + ":" + port, serviceName);
        //插入本次启动日志
        derbyDao.insertStartLog(startLog);

        log.info("本次启动信息：【启动时间：{}； 启动ip端口：{}； 启动服务名：{}】", startLog.getStartTimeToLocalDateTime(), startLog.getIpPort(), startLog.getServiceName());
        log.info("****************************************************************************************************************************");
    }

    /**
     * 初始化数据库，刷表
     */
    private void initDB() throws Exception {
        log.info("首次启动服务，初始化数据库！");
        derbyDao.doAnySQL(createStartLogTableSql);
        derbyDao.doAnySQL(createReceiverLogTableSql);
        log.info("初始化数据库成功！");
    }
}
