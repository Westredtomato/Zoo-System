package org.example.service;

import org.example.dao.LoginLogDAO;
import org.example.dao.UserAccountDAO;
import org.example.model.LoginLog;
import org.example.model.UserAccount;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * 用户账号管理：创建、更新、认证、登录失败计数与锁定
 */
public class UserService {

    private final UserAccountDAO userDAO = new UserAccountDAO();
    private final LoginLogDAO loginLogDAO = new LoginLogDAO();

    private final int MAX_FAILED_ATTEMPTS = 3; // 锁定阈值

    public void createUser(UserAccount user) throws SQLException {
        // 检查 username 唯一
        UserAccount existing = userDAO.findByUsername(user.getUsername());
        if (existing != null) {
            throw new IllegalArgumentException("用户名已存在: " + user.getUsername());
        }
        userDAO.insert(user);
    }

    public void updateUser(UserAccount user) throws SQLException {
        userDAO.update(user);
    }

    public void deleteUser(String userId) throws SQLException {
        userDAO.delete(userId);
    }

    public UserAccount getUserById(String userId) throws SQLException {
        return userDAO.findById(userId);
    }

    public UserAccount getUserByUsername(String username) throws SQLException {
        return userDAO.findByUsername(username);
    }

    /**
     * 简单认证：这里假设 passwordHash 存储的是明文或已知方式，业务中请替换为真实哈希验证
     * 登录成功会重置 failedAttempts，并记录 login_log
     * 登录失败会增加 failedAttempts，超过阈值则锁定账号
     */
    public boolean authenticate(String username, String password, String ip, String userAgent) throws SQLException {
        UserAccount user = userDAO.findByUsername(username);
        if (user == null) {
            // 记录失败日志（匿名）
            LoginLog log = new LoginLog();
            log.setUserId(username);
            log.setLoginTime(LocalDateTime.now());
            log.setIpAddress(ip);
            log.setUserAgent(userAgent);
            log.setLoginStatus("failed");
            log.setFailureReason("用户不存在");
            loginLogDAO.insert(log);
            return false;
        }

        if (user.isLocked()) {
            // 记录被锁定尝试
            LoginLog log = new LoginLog();
            log.setUserId(user.getUserId());
            log.setLoginTime(LocalDateTime.now());
            log.setIpAddress(ip);
            log.setUserAgent(userAgent);
            log.setLoginStatus("locked");
            log.setFailureReason("账号已锁定");
            loginLogDAO.insert(log);
            return false;
        }

        // 简单比较密码字段
        boolean ok = password != null && password.equals(user.getPasswordHash());
        if (ok) {
            // 登录成功
            user.setFailedAttempts(0);
            user.setLastLogin(LocalDateTime.now());
            userDAO.update(user);

            LoginLog log = new LoginLog();
            log.setUserId(user.getUserId());
            log.setLoginTime(LocalDateTime.now());
            log.setIpAddress(ip);
            log.setUserAgent(userAgent);
            log.setLoginStatus("success");
            loginLogDAO.insert(log);
            return true;
        } else {
            // 登录失败，增加计数
            int failed = user.getFailedAttempts() + 1;
            user.setFailedAttempts(failed);
            if (failed >= MAX_FAILED_ATTEMPTS) {
                user.setLocked(true);
                user.setLockedUntil(LocalDateTime.now().plusHours(1));
            }
            userDAO.update(user);

            LoginLog log = new LoginLog();
            log.setUserId(user.getUserId());
            log.setLoginTime(LocalDateTime.now());
            log.setIpAddress(ip);
            log.setUserAgent(userAgent);
            log.setLoginStatus("failed");
            log.setFailureReason("密码错误");
            loginLogDAO.insert(log);
            return false;
        }
    }

    public void unlockUser(String userId) throws SQLException {
        UserAccount user = userDAO.findById(userId);
        if (user == null) return;
        user.setLocked(false);
        user.setFailedAttempts(0);
        user.setLockedUntil(null);
        userDAO.update(user);
    }
}

