package com.zaiji.starter.sql.service;


import com.zaiji.starter.sql.properties.SqlSearchProperties;

public class TestServices {
    private SqlSearchProperties sqlSearchProperties;

    public TestServices() {
    }

    public TestServices(SqlSearchProperties sqlSearchProperties) {
        this.sqlSearchProperties = sqlSearchProperties;
    }

    public SqlSearchProperties getSqlSearchProperties() {
        return sqlSearchProperties;
    }

    public void setSqlSearchProperties(SqlSearchProperties sqlSearchProperties) {
        this.sqlSearchProperties = sqlSearchProperties;
    }

    public String print(){
        System.out.println(sqlSearchProperties.toString());
        return sqlSearchProperties.toString();
    }
}
