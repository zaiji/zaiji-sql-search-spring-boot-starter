package com.zaiji.starter.sql.dao;

import com.sun.org.apache.bcel.internal.generic.Select;
import com.zaiji.starter.sql.entity.DataSourceType;
import com.zaiji.starter.sql.entity.ReceiverLog;
import com.zaiji.starter.sql.entity.StartLog;
import com.zaiji.starter.sql.entity.Status;
import com.zaiji.starter.sql.properties.SqlSearchProperties;
import lombok.extern.log4j.Log4j2;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * derby数据库操作
 *
 * @author zaiji
 * @date 2021/06/18
 */

@Log4j2
@Component
public class DerbyDao {

    private final SqlSearchProperties sqlSearchProperties;

    public Connection getConnection() throws Exception {
        Class.forName(sqlSearchProperties.getDriver_class_name());
        final Connection connection = DriverManager.getConnection(sqlSearchProperties.getJdbc_url());
        return connection;
    }

    public DerbyDao(SqlSearchProperties sqlSearchProperties) {
        this.sqlSearchProperties = sqlSearchProperties;
    }

    /**
     * 获得最后一条成功启动的日志信息
     *
     * @return 启动日志信息
     */
    public StartLog getLastStartLog() throws Exception {
        try (Connection connection = getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("select SERVICE_NAME, IP_PORT, START_TIME from wisvision_start_log order by start_time desc FETCH FIRST ROW ONLY");
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<StartLog> startLogs = new LinkedList<>();
            while (resultSet.next()) {
                StartLog startLog = new StartLog();
                final String serviceName = resultSet.getString("SERVICE_NAME");
                final String ipPort = resultSet.getString("IP_PORT");
                final Long startTime = resultSet.getLong("START_TIME");
                startLog.setServiceName(serviceName)
                        .setIpPort(ipPort)
                        .setStartTime(startTime);
                startLogs.add(startLog);
            }
            return startLogs.isEmpty() ? null : startLogs.get(0);
        }
    }

    /**
     * 插入数据库启动日志
     *
     * @param startLog 启动日志
     */
    public void insertStartLog(StartLog startLog) throws Exception {
        try (final Connection connection = getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("insert into wisvision_start_log(service_name,ip_port,start_time) values(?,?,?)");
            preparedStatement.setString(1, startLog.getServiceName());
            preparedStatement.setString(2, startLog.getIpPort());
            preparedStatement.setLong(3, startLog.getStartTime());
            final boolean execute = preparedStatement.execute();
        }
    }

    /**
     * 执行任意sql，方便直接在页面查询日志信息
     *
     * @param sql sql语句
     * @return 查询结果
     */
    public List<Map<String, Object>> doAnySQL(String sql) throws Exception {

        if (StringUtils.isEmpty(sql) || sql.length() <= 8) {
            return null;
        }

        try (final Connection connection = getConnection()) {
            sql = sql.trim();
            if ("select".equalsIgnoreCase(sql.substring(0, 6))) {
                final PreparedStatement preparedStatement = connection.prepareStatement(sql);
                final ResultSet resultSet = preparedStatement.executeQuery();

                List<Map<String, Object>> results = new LinkedList<>();

                while (resultSet.next()) {
                    final ResultSetMetaData metaData = resultSet.getMetaData();
                    final int columnCount = metaData.getColumnCount();

                    Map<String, Object> map = new HashMap<>(columnCount / 3 * 4);

                    for (int i = 0; i < columnCount; i++) {
                        final Object object = resultSet.getObject(i);
                        final String columnName = metaData.getColumnName(i);
                        map.put(columnName, object);
                    }
                    results.add(map);
                }
                return results;
            } else {
                final PreparedStatement preparedStatement = connection.prepareStatement(sql);
                final Integer i = preparedStatement.executeUpdate();
                Map<String, Object> param = new HashMap<String, Object>(2) {{
                    put("影响行数", i);
                }};
                return Collections.singletonList(param);
            }
        }
    }

    /**
     * 插入接入日志
     *
     * @param receiverLog 接入日志信息
     */
    public void insertReceiverLog(ReceiverLog receiverLog) throws Exception {
        try (final Connection connection = getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("insert into wisvision_receiver_log (receiver_time, processing_time, completion_time, receiver_status,\n" +
                    "                                            handle_status, data_source_type, data_source_info_json_text,\n" +
                    "                                            receiver_data_context, handle_info, other_info) values (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setLong(1, receiverLog.getReceiverTime());
            preparedStatement.setLong(2, receiverLog.getProcessingTime());
            preparedStatement.setLong(3, receiverLog.getCompletionTime());
            preparedStatement.setString(4, receiverLog.getReceiverStatus().toString());
            preparedStatement.setString(5, receiverLog.getHandleStatus().toString());
            preparedStatement.setString(6, receiverLog.getDataSourceType().toString());
            preparedStatement.setString(7, receiverLog.getDataSourceInfoJsonText());
            preparedStatement.setString(8, receiverLog.getReceiverDataContext());
            preparedStatement.setString(9, receiverLog.getHandleInfo());
            preparedStatement.setString(10, receiverLog.getOtherInfo());
        }
    }

    /**
     * 查询接入日志
     *
     * @return 所有的接入日志信息
     */
    public List<ReceiverLog> getReceiverLogs() throws Exception {
        try (final Connection connection = getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("select receiver_time,\n" +
                    "               processing_time,\n" +
                    "               completion_time,\n" +
                    "               receiver_status,\n" +
                    "               handle_status,\n" +
                    "               data_source_type,\n" +
                    "               data_source_info_json_text,\n" +
                    "               receiver_data_context,\n" +
                    "               handle_info,\n" +
                    "               other_info\n" +
                    "        from wisvision_receiver_log");

            final ResultSet resultSet = preparedStatement.executeQuery();

            List<ReceiverLog> results = new LinkedList<>();

            while (resultSet.next()) {
                final ReceiverLog receiverLog = new ReceiverLog();
                final long receiverTime = resultSet.getLong("RECEIVER_TIME");
                final long processingTime = resultSet.getLong("PROCESSING_TIME");
                final long completionTime = resultSet.getLong("COMPLETION_TIME");
                final String receiverStatus = resultSet.getString("RECEIVER_STATUS");
                final String handleStatus = resultSet.getString("HANDLE_STATUS");
                final String dataSourceType = resultSet.getString("DATA_SOURCE_TYPE");
                final String dataSourceInfoJsonText = resultSet.getString("DATA_SOURCE_INFO_JSON_TEXT");
                final String receiverDataContext = resultSet.getString("RECEIVER_DATA_CONTEXT");
                final String handleInfo = resultSet.getString("HANDLE_INFO");
                final String otherInfo = resultSet.getString("OTHER_INFO");
                receiverLog.setReceiverTime(receiverTime)
                        .setProcessingTime(processingTime)
                        .setCompletionTime(completionTime)
                        .setReceiverStatus(Status.getStatus(receiverStatus))
                        .setHandleStatus(Status.getStatus(handleStatus))
                        .setDataSourceType(DataSourceType.getDataSourceType(dataSourceType))
                        .setDataSourceInfoJsonText(dataSourceInfoJsonText)
                        .setReceiverDataContext(receiverDataContext)
                        .setHandleInfo(handleInfo)
                        .setOtherInfo(otherInfo);

                results.add(receiverLog);
            }
            return results;
        }
    }
}