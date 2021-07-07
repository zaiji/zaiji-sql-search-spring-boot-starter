package com.zaiji.starter.sql.config;

import com.zaiji.starter.sql.properties.SqlSearchProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConditionalOnProperty(value = "zaiji.sql.search.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SqlSearchProperties.class)
public class SqlSearchAutoConfiguration {

    private final SqlSearchProperties sqlSearchProperties;

    public SqlSearchAutoConfiguration(SqlSearchProperties sqlSearchProperties) {
        this.sqlSearchProperties = sqlSearchProperties;
    }
}
