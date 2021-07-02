package com.zaiji.starter.sql.controller;

import com.zaiji.starter.sql.dao.DerbyDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Reader;
import java.sql.Clob;
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
    public Map<String, List<String>> getAllDBInfo() {
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
    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, Object>> doAnySQL(String sql) throws Exception {
        final List<Map<String, Object>> maps = derbyDao.doAnySQL(sql);

        for (Map<String, Object> map : maps) {
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                final String key = stringObjectEntry.getKey();
                final Object value = stringObjectEntry.getValue();
                if (value instanceof Clob) {
                    final Clob clob = (Clob) value;
                    final Reader characterStream = clob.getCharacterStream();
                    char[] tempChar = new char[1024];
                    int i = 0;
                    final StringBuffer stringBuffer = new StringBuffer();
                    while ((i = characterStream.read(tempChar)) != -1) {
                        stringBuffer.append(tempChar, 0, i);
                    }
                    map.put(key, stringBuffer.toString());
                }
            }
        }
        return maps;
    }
}
