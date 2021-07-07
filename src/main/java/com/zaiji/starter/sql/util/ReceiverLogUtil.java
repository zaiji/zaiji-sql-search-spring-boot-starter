package com.zaiji.starter.sql.util;


import com.zaiji.starter.sql.dao.DerbyDao;
import com.zaiji.starter.sql.entity.ReceiverLog;
import com.zaiji.starter.sql.entity.Status;

/**
 * 接收日志工具的工具类
 *
 * @author zaiji
 * @date 2021/07/07
 */

public final class ReceiverLogUtil {

    private static DerbyDao derbyDao = SpringUtils.getBean(DerbyDao.class);

    public static void setReceiverLogInfo(ReceiverLog receiverLog, String info) {
        receiverLog.setHandleInfo(info);
    }

    public static void setReceiverLogInfo(ReceiverLog receiverLog, Status status) {
        receiverLog.setHandleStatus(status);
    }

    public static void setReceiverLogInfo(ReceiverLog receiverLog, Status status, String info) {
        receiverLog.setHandleStatus(status);
        receiverLog.setHandleInfo(info);
    }

    public static void insertReceiverLog(ReceiverLog receiverLog) throws Exception {
        derbyDao.insertReceiverLog(receiverLog);
    }
}
