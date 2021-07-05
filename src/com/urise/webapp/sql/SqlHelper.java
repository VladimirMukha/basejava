package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        getConnection(sql, PreparedStatement::execute);
    }

    public <T> T getConnection(String sql, SqlExecutes<T> executes) {
        try {
            Connection connection = connectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            return executes.execute(ps);
        } catch (SQLException e) {
            throw new ExceptionSql().getConvertException(e);
        }
    }
}