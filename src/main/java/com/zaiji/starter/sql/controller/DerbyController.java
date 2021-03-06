package com.zaiji.starter.sql.controller;

import com.sun.org.apache.bcel.internal.ExceptionConst;
import com.zaiji.starter.sql.dao.DerbyDao;
import com.zaiji.starter.sql.entity.ReceiverLog;
import com.zaiji.starter.sql.entity.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Reader;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库查询相关controller
 *
 * @author zaiji
 * @date 2021/06/21
 */

@RestController
@RequestMapping("/derby/")
public class DerbyController {
    private final DerbyDao derbyDao;

    public DerbyController(DerbyDao derbyDao) {
        this.derbyDao = derbyDao;
    }

    /**
     * 获得所有数据库信息，表-字段
     */
    @GetMapping("allDBInfo")
    public Map<String, List<String>> getAllDBInfo() throws Exception {
        final List<Map<String, Object>> maps = derbyDao.doAnySQL("SELECT\n" +
                "t.tablename as stablename,c.columnname as scolumnname\n" +
                "FROM sys.systables t , sys.syscolumns c, sys.sysschemas s\n" +
                "where t.schemaid=s.schemaid\n" +
                "AND t.tableid = c.referenceid\n" +
                "and t.tabletype = 'T'");

        final Map<String, List<String>> collect = maps.stream().collect(Collectors.groupingBy(e -> String.valueOf(e.get("STABLENAME")), Collectors.mapping(e -> String.valueOf(e.get("SCOLUMNNAME")), Collectors.toList())));

        return collect;
    }

    @GetMapping("/doAnySQL")
    public List<Map<String, Object>> doAnySQL(String sql) throws Exception {
        final List<Map<String, Object>> maps = derbyDao.doAnySQL(sql);
        return maps;
    }

    @GetMapping("/getRececiverLog")
    @ResponseBody
    public Map<String, Object> getReceiverLog(Status handleStatus, Status receiverStatus,
                                              String messageContext, Date receiverStartTime,
                                              Date receiverEndTime, Long handleStartTime,
                                              Long handleEndTime,
                                              Date completeStartTime, Date completeEndTime,
                                              Integer pageNum, Integer pageSize) throws Exception {
        final List<ReceiverLog> receiverLogs = derbyDao.getReceiverLogs(handleStatus, receiverStatus, messageContext, receiverStartTime, receiverEndTime, handleStartTime, handleEndTime, completeStartTime, completeEndTime, pageNum, pageSize);
        final int count = derbyDao.getCount(handleStatus, receiverStatus, messageContext, receiverStartTime, receiverEndTime, handleStartTime, handleEndTime, completeStartTime, completeEndTime);
        return new HashMap<String, Object>(4) {{
            put("count", count);
            put("data", receiverLogs);
        }};
    }
}
