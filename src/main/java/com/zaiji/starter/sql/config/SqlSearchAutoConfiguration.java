package com.zaiji.starter.sql.config;

import com.zaiji.starter.sql.dao.DerbyDao;
import com.zaiji.starter.sql.init.DBInit;
import com.zaiji.starter.sql.properties.SqlSearchProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Log4j2
@Configuration
@ConditionalOnProperty(value = "zaiji.sql.search.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SqlSearchProperties.class)
public class SqlSearchAutoConfiguration {

    @Bean
    public DBInit dbInit(DerbyDao derbyDao, Environment environment, SqlSearchProperties sqlSearchProperties) {
        final DBInit dbInit = new DBInit(derbyDao, environment, sqlSearchProperties.getService_name());
        try {
            dbInit.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbInit;
    }

    @Bean
    public DerbyDao derbyDao(SqlSearchProperties sqlSearchProperties) {
        return new DerbyDao(sqlSearchProperties);
    }
}
