package com.zaiji.starter.sql.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class DaoUtil {

    public static final <T> List<T> executeSearch(Connection connection, String sql, Class<? extends T> targetClass) throws Exception {
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);

        final ResultSet resultSet = preparedStatement.executeQuery();

        final LinkedList<T> result = new LinkedList<>();

        while (resultSet.next()){

        }

        return null;
    }

}
