package com.zaiji.starter.sql.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties("zaiji.sql.search")
public class SqlSearchProperties {

    private boolean enable = true;

    private boolean derby_enable = true;

    private String driver_class_name;

    private String jdbc_url;

    private String jdbc_username;

    private String jdbc_password;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isDerby_enable() {
        return derby_enable;
    }

    public void setDerby_enable(boolean derby_enable) {
        this.derby_enable = derby_enable;
    }

    public String getDriver_class_name() {
        return driver_class_name;
    }

    public void setDriver_class_name(String driver_class_name) {
        this.driver_class_name = driver_class_name;
    }

    public String getJdbc_url() {
        return jdbc_url;
    }

    public void setJdbc_url(String jdbc_url) {
        this.jdbc_url = jdbc_url;
    }

    public String getJdbc_username() {
        return jdbc_username;
    }

    public void setJdbc_username(String jdbc_username) {
        this.jdbc_username = jdbc_username;
    }

    public String getJdbc_password() {
        return jdbc_password;
    }

    public void setJdbc_password(String jdbc_password) {
        this.jdbc_password = jdbc_password;
    }

    @Override
    public String toString() {
        return "SqlSearchProperties{" +
                "enable=" + enable +
                ", derby_enable=" + derby_enable +
                ", driver_class_name='" + driver_class_name + '\'' +
                ", jdbc_url='" + jdbc_url + '\'' +
                ", jdbc_username='" + jdbc_username + '\'' +
                ", jdbc_password='" + jdbc_password + '\'' +
                '}';
    }
}
