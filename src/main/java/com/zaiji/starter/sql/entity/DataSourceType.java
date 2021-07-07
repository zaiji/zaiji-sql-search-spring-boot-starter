package com.zaiji.starter.sql.entity;

import lombok.extern.log4j.Log4j2;

/**
 * 接入数据源类型
 *
 * @author zaiji
 * @date 2021/06/19
 */
public enum DataSourceType {

    /**
     * activeMQ
     */
    activemq;

    public static DataSourceType getDataSourceType(String dataSourceType) {
        try {
            return DataSourceType.valueOf(dataSourceType);
        } catch (Exception e) {
            return null;
        }
    }
}
