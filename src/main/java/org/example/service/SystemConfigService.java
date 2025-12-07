package org.example.service;

import org.example.dao.SystemConfigDAO;
import org.example.model.SystemConfig;

import java.sql.SQLException;
import java.util.List;

public class SystemConfigService {

    private final SystemConfigDAO configDAO = new SystemConfigDAO();

    public SystemConfig getConfig(String key) throws SQLException {
        return configDAO.findByKey(key);
    }

    public List<SystemConfig> listAllConfigs() throws SQLException {
        return configDAO.findAll();
    }

    public void setConfig(SystemConfig cfg) throws SQLException {
        if (configDAO.findByKey(cfg.getConfigKey()) == null) {
            configDAO.insert(cfg);
        } else {
            configDAO.update(cfg);
        }
    }

    public void deleteConfig(String key) throws SQLException {
        configDAO.delete(key);
    }
}

