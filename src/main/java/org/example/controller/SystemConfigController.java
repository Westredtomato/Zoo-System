package org.example.controller;

import org.example.model.SystemConfig;
import org.example.service.SystemConfigService;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller contract for system configuration endpoints.
 */
public class SystemConfigController {
    private final SystemConfigService service = new SystemConfigService();

    public SystemConfig get(String key) throws SQLException { return service.getConfig(key); }
    public List<SystemConfig> listAll() throws SQLException { return service.listAllConfigs(); }
    public void set(SystemConfig cfg) throws SQLException { service.setConfig(cfg); }
    public void delete(String key) throws SQLException { service.deleteConfig(key); }
}

