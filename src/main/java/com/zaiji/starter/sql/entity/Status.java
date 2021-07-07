package com.zaiji.starter.sql.entity;

/**
 * 状态枚举类，用于标志动作状态
 *
 * @author zaiji
 * @date 2021/06/18
 */

public enum Status {
    /**
     * 成功
     */
    success,

    /**
     * 警告
     */
    warn,

    /**
     * 失败
     */
    error,

    /**
     * 其他
     */
    other;

    public static Status getStatus(String status) {
        try {
            return Status.valueOf(status);
        } catch (Exception e) {
            return null;
        }
    }
}
