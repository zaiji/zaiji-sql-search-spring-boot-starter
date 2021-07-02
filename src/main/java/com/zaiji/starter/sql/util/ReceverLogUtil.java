package com.zaiji.starter.sql.util;


import com.zaiji.starter.sql.entity.ReceiverLog;
import com.zaiji.starter.sql.entity.Status;

public class ReceverLogUtil {
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
}
