package org.example.dao;

import org.example.model.SystemConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SystemConfigDAO extends BaseDAO<SystemConfig> {

    public int insert(SystemConfig cfg) throws SQLException {
        String sql = "INSERT INTO system_config (config_key, config_value, config_type, description, is_public, updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                cfg.getConfigKey(),
                cfg.getConfigValue(),
                cfg.getConfigType(),
                cfg.getDescription(),
                cfg.isPublicConfig(),
                cfg.getUpdatedBy()
        );
    }

    public SystemConfig findByKey(String key) throws SQLException {
        String sql = "SELECT * FROM system_config WHERE config_key = ?";
        return queryForObject(sql, key);
    }

    public List<SystemConfig> findAll() throws SQLException {
        String sql = "SELECT * FROM system_config ORDER BY config_key";
        return queryForList(sql);
    }

    public int update(SystemConfig cfg) throws SQLException {
        String sql = "UPDATE system_config SET config_value = ?, config_type = ?, description = ?, is_public = ?, " +
                "updated_by = ?, updated_at = CURRENT_TIMESTAMP WHERE config_key = ?";
        return executeUpdate(sql,
                cfg.getConfigValue(),
                cfg.getConfigType(),
                cfg.getDescription(),
                cfg.isPublicConfig(),
                cfg.getUpdatedBy(),
                cfg.getConfigKey()
        );
    }

    public int delete(String key) throws SQLException {
        String sql = "DELETE FROM system_config WHERE config_key = ?";
        return executeUpdate(sql, key);
    }

    public int countConfigs() throws SQLException {
        String sql = "SELECT COUNT(*) FROM system_config";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected SystemConfig mapResultSetToEntity(ResultSet rs) throws SQLException {
        SystemConfig c = new SystemConfig();
        c.setConfigKey(rs.getString("config_key"));
        c.setConfigValue(rs.getString("config_value"));
        c.setConfigType(rs.getString("config_type"));
        c.setDescription(rs.getString("description"));
        c.setPublicConfig(rs.getBoolean("is_public"));
        c.setUpdatedBy(rs.getString("updated_by"));
        var updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            c.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return c;
    }
}

