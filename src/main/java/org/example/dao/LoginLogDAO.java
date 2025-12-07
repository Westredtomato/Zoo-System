package org.example.dao;

import org.example.model.LoginLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginLogDAO extends BaseDAO<LoginLog> {

    public int insert(LoginLog log) throws SQLException {
        String sql = "INSERT INTO login_log (user_id, login_time, ip_address, user_agent, login_status, failure_reason) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                log.getUserId(),
                log.getLoginTime(),
                log.getIpAddress(),
                log.getUserAgent(),
                log.getLoginStatus(),
                log.getFailureReason()
        );
    }

    public LoginLog findById(int logId) throws SQLException {
        String sql = "SELECT * FROM login_log WHERE log_id = ?";
        return queryForObject(sql, logId);
    }

    public List<LoginLog> findAll() throws SQLException {
        String sql = "SELECT * FROM login_log ORDER BY log_id";
        return queryForList(sql);
    }

    public int delete(int logId) throws SQLException {
        String sql = "DELETE FROM login_log WHERE log_id = ?";
        return executeUpdate(sql, logId);
    }

    public int countLogs() throws SQLException {
        String sql = "SELECT COUNT(*) FROM login_log";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected LoginLog mapResultSetToEntity(ResultSet rs) throws SQLException {
        LoginLog l = new LoginLog();
        l.setLogId(rs.getInt("log_id"));
        l.setUserId(rs.getString("user_id"));
        var loginTime = rs.getTimestamp("login_time");
        if (loginTime != null) {
            l.setLoginTime(loginTime.toLocalDateTime());
        }
        l.setIpAddress(rs.getString("ip_address"));
        l.setUserAgent(rs.getString("user_agent"));
        l.setLoginStatus(rs.getString("login_status"));
        l.setFailureReason(rs.getString("failure_reason"));
        return l;
    }
}

