package org.example.controller;

import org.example.model.LoginLog;
import org.example.model.OperationLog;
import org.example.service.LogService;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller contract for logging endpoints.
 */
public class LogController {
    private final LogService service = new LogService();

    public void logLogin(LoginLog l) throws SQLException { service.logLogin(l); }
    public void logOperation(OperationLog o) throws SQLException { service.logOperation(o); }
    public List<LoginLog> listLoginLogs() throws SQLException { return service.listLoginLogs(); }
    public List<OperationLog> listOperationLogs() throws SQLException { return service.listOperationLogs(); }
}

