package com.zaiji.starter.sql.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
@ConfigurationProperties("zaiji.sql.search")
public class SqlSearchProperties {

    private boolean enable = true;

    private boolean derby_enable = true;

    private String driver_class_name = "org.apache.derby.jdbc.EmbeddedDriver";

    private String jdbc_url = "jdbc:derby:receiver_log;create=true;";

    private String jdbc_username;

    private String jdbc_password;

    private String service_name = "zaiji-sql-search-spring-boot-starter";
}
