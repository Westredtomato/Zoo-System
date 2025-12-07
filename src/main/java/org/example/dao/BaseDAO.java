package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
    protected Connection connection;

    public BaseDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // 执行查询并返回结果集
    protected ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, params);
        return stmt.executeQuery();
    }

    // 执行更新（INSERT, UPDATE, DELETE）
    protected int executeUpdate(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, params);
        return stmt.executeUpdate();
    }

    // PostgreSQL特定的方法：执行插入并返回生成的ID
    protected int executeInsertWithReturn(String sql, Object... params) throws SQLException {
        // PostgreSQL使用RETURNING子句
        String returningSql = sql + " RETURNING *";
        try (PreparedStatement stmt = connection.prepareStatement(returningSql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // 返回第一个列，通常是ID
            }
            return -1;
        }
    }

    // 执行查询并返回单个值
    protected Object executeScalar(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = prepareStatement(sql, params);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        }
    }

    // 准备语句并设置参数
    protected PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        setParameters(stmt, params);
        return stmt;
    }

    // 设置参数到PreparedStatement
    protected void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            if (param == null) {
                stmt.setNull(i + 1, Types.NULL);
            } else if (param instanceof Integer) {
                stmt.setInt(i + 1, (Integer) param);
            } else if (param instanceof String) {
                stmt.setString(i + 1, (String) param);
            } else if (param instanceof Boolean) {
                stmt.setBoolean(i + 1, (Boolean) param);
            } else if (param instanceof Double) {
                stmt.setDouble(i + 1, (Double) param);
            } else if (param instanceof Float) {
                stmt.setFloat(i + 1, (Float) param);
            } else if (param instanceof Long) {
                stmt.setLong(i + 1, (Long) param);
            } else if (param instanceof Short) {
                stmt.setShort(i + 1, (Short) param);
            } else if (param instanceof Byte) {
                stmt.setByte(i + 1, (Byte) param);
            } else if (param instanceof Date) {
                stmt.setDate(i + 1, (Date) param);
            } else if (param instanceof Time) {
                stmt.setTime(i + 1, (Time) param);
            } else if (param instanceof Timestamp) {
                stmt.setTimestamp(i + 1, (Timestamp) param);
            } else if (param instanceof java.util.Date) {
                stmt.setDate(i + 1, new Date(((java.util.Date) param).getTime()));
            } else if (param instanceof java.math.BigDecimal) {
                stmt.setBigDecimal(i + 1, (java.math.BigDecimal) param);
            } else if (param instanceof Enum) {
                // 处理枚举类型
                stmt.setString(i + 1, ((Enum<?>) param).name());
            } else {
                // 其他类型使用通用方法
                stmt.setObject(i + 1, param);
            }
        }
    }

    // 从ResultSet创建实体对象（抽象方法，子类实现）
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    // 通用查询方法
    protected List<T> queryForList(String sql, Object... params) throws SQLException {
        List<T> list = new ArrayList<>();
        try (PreparedStatement stmt = prepareStatement(sql, params);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        }
        return list;
    }

    // 通用查询单个对象方法
    protected T queryForObject(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = prepareStatement(sql, params);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
            return null;
        }
    }

    // 批量执行
    protected void executeBatch(String sql, List<Object[]> paramsList) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (Object[] params : paramsList) {
                    setParameters(stmt, params);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    // 分页查询
    protected List<T> queryForPage(String sql, int page, int pageSize, Object... params) throws SQLException {
        // PostgreSQL分页语法
        String pagedSql = sql + " LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;

        Object[] newParams = new Object[params.length + 2];
        System.arraycopy(params, 0, newParams, 0, params.length);
        newParams[params.length] = pageSize;
        newParams[params.length + 1] = offset;

        return queryForList(pagedSql, newParams);
    }

    // 获取记录总数
    protected int getTotalCount(String tableName, String whereClause, Object... params) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName);
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sql.append(" WHERE ").append(whereClause);
        }

        Object result = executeScalar(sql.toString(), params);
        return result != null ? ((Number) result).intValue() : 0;
    }

    // 检查记录是否存在
    protected boolean exists(String tableName, String whereClause, Object... params) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT 1 FROM ").append(tableName);
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sql.append(" WHERE ").append(whereClause);
        }
        sql.append(" LIMIT 1");

        Object result = executeScalar(sql.toString(), params);
        return result != null;
    }

    // 关闭资源的方法
    protected void closeResources(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("关闭ResultSet时出错: " + e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("关闭Statement时出错: " + e.getMessage());
            }
        }
    }
}