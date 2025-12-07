package org.example.service;

import org.example.dao.LoginLogDAO;
import org.example.dao.OperationLogDAO;
import org.example.dao.UserAccountDAO;
import org.example.model.LoginLog;
import org.example.model.OperationLog;

import java.sql.SQLException;
import java.util.List;

/**
 * 日志服务：登录日志与操作日志
 */
public class LogService {

    private final LoginLogDAO loginLogDAO = new LoginLogDAO();
    private final OperationLogDAO operationLogDAO = new OperationLogDAO();
    private final UserAccountDAO userAccountDAO = new UserAccountDAO();

    public void logLogin(LoginLog log) throws SQLException {
        // 避免外键约束失败：如果 userId 不存在，则写入 null（匿名）
        if (log.getUserId() != null) {
            try {
                if (userAccountDAO.findById(log.getUserId()) == null) {
                    log.setUserId(null);
                }
            } catch (SQLException e) {
                // 若查询报错，继续尝试插入（可能会触发 FK 错误），由上层处理
            }
        }
        loginLogDAO.insert(log);
    }

    public void logOperation(OperationLog log) throws SQLException {
        operationLogDAO.insert(log);
    }

    public LoginLog getLoginLog(int id) throws SQLException {
        return loginLogDAO.findById(id);
    }

    public List<LoginLog> listLoginLogs() throws SQLException {
        return loginLogDAO.findAll();
    }

    public OperationLog getOperationLog(int id) throws SQLException {
        return operationLogDAO.findById(id);
    }

    public List<OperationLog> listOperationLogs() throws SQLException {
        return operationLogDAO.findAll();
    }
}
