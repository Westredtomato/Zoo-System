package org.example.dao;

import org.example.model.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserAccountDAO extends BaseDAO<UserAccount> {

    public int insert(UserAccount user) throws SQLException {
        String sql = "INSERT INTO user_account (user_id, username, password_hash, employee_id, role, email, " +
                "phone, avatar_url, is_active, is_locked, failed_attempts, locked_until, created_at, last_login, password_changed_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";
        return executeUpdate(sql,
                user.getUserId(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getEmployeeId(),
                user.getRole(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarUrl(),
                user.isActive(),
                user.isLocked(),
                user.getFailedAttempts(),
                user.getLockedUntil(),
                user.getLastLogin(),
                user.getPasswordChangedAt()
        );
    }

    public UserAccount findById(String userId) throws SQLException {
        String sql = "SELECT * FROM user_account WHERE user_id = ?";
        return queryForObject(sql, userId);
    }

    public UserAccount findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user_account WHERE username = ?";
        return queryForObject(sql, username);
    }

    public List<UserAccount> findAll() throws SQLException {
        String sql = "SELECT * FROM user_account ORDER BY user_id";
        return queryForList(sql);
    }

    public int update(UserAccount user) throws SQLException {
        String sql = "UPDATE user_account SET username = ?, password_hash = ?, employee_id = ?, role = ?, email = ?, " +
                "phone = ?, avatar_url = ?, is_active = ?, is_locked = ?, failed_attempts = ?, locked_until = ?, " +
                "last_login = ?, password_changed_at = ? WHERE user_id = ?";
        return executeUpdate(sql,
                user.getUsername(),
                user.getPasswordHash(),
                user.getEmployeeId(),
                user.getRole(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarUrl(),
                user.isActive(),
                user.isLocked(),
                user.getFailedAttempts(),
                user.getLockedUntil(),
                user.getLastLogin(),
                user.getPasswordChangedAt(),
                user.getUserId()
        );
    }

    public int delete(String userId) throws SQLException {
        String sql = "DELETE FROM user_account WHERE user_id = ?";
        return executeUpdate(sql, userId);
    }

    public int countUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM user_account";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected UserAccount mapResultSetToEntity(ResultSet rs) throws SQLException {
        UserAccount u = new UserAccount();
        u.setUserId(rs.getString("user_id"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setEmployeeId(rs.getString("employee_id"));
        u.setRole(rs.getString("role"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setAvatarUrl(rs.getString("avatar_url"));
        u.setActive(rs.getBoolean("is_active"));
        u.setLocked(rs.getBoolean("is_locked"));
        u.setFailedAttempts(rs.getInt("failed_attempts"));
        var lockedUntil = rs.getTimestamp("locked_until");
        if (lockedUntil != null) {
            u.setLockedUntil(lockedUntil.toLocalDateTime());
        }
        var createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            u.setCreatedAt(createdAt.toLocalDateTime());
        }
        var lastLogin = rs.getTimestamp("last_login");
        if (lastLogin != null) {
            u.setLastLogin(lastLogin.toLocalDateTime());
        }
        var pwdChanged = rs.getTimestamp("password_changed_at");
        if (pwdChanged != null) {
            u.setPasswordChangedAt(pwdChanged.toLocalDateTime());
        }
        return u;
    }
}

