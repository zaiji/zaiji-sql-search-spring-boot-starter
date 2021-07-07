package com.zaiji.starter.sql.entity;


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
