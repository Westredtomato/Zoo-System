package org.example.dao;

import org.example.model.OperationLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OperationLogDAO extends BaseDAO<OperationLog> {

    public int insert(OperationLog log) throws SQLException {
        String sql = "INSERT INTO operation_log (user_id, operation_time, module, operation_type, table_name, record_id, " +
                "old_values, new_values, ip_address, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                log.getUserId(),
                log.getOperationTime(),
                log.getModule(),
                log.getOperationType(),
                log.getTableName(),
                log.getRecordId(),
                log.getOldValues(),
                log.getNewValues(),
                log.getIpAddress(),
                log.getDescription()
        );
    }

    public OperationLog findById(int logId) throws SQLException {
        String sql = "SELECT * FROM operation_log WHERE log_id = ?";
        return queryForObject(sql, logId);
    }

    public List<OperationLog> findAll() throws SQLException {
        String sql = "SELECT * FROM operation_log ORDER BY log_id";
        return queryForList(sql);
    }

    public int delete(int logId) throws SQLException {
        String sql = "DELETE FROM operation_log WHERE log_id = ?";
        return executeUpdate(sql, logId);
    }

    public int countLogs() throws SQLException {
        String sql = "SELECT COUNT(*) FROM operation_log";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected OperationLog mapResultSetToEntity(ResultSet rs) throws SQLException {
        OperationLog l = new OperationLog();
        l.setLogId(rs.getInt("log_id"));
        l.setUserId(rs.getString("user_id"));
        var opTime = rs.getTimestamp("operation_time");
        if (opTime != null) {
            l.setOperationTime(opTime.toLocalDateTime());
        }
        l.setModule(rs.getString("module"));
        l.setOperationType(rs.getString("operation_type"));
        l.setTableName(rs.getString("table_name"));
        l.setRecordId(rs.getString("record_id"));
        l.setOldValues(rs.getString("old_values"));
        l.setNewValues(rs.getString("new_values"));
        l.setIpAddress(rs.getString("ip_address"));
        l.setDescription(rs.getString("description"));
        return l;
    }
}

