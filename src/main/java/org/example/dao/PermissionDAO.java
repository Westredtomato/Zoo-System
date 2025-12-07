package org.example.dao;

import org.example.model.Permission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PermissionDAO extends BaseDAO<Permission> {

    public int insert(Permission p) throws SQLException {
        String sql = "INSERT INTO permission (permission_id, permission_code, permission_name, description, module) " +
                "VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                p.getPermissionId(),
                p.getPermissionCode(),
                p.getPermissionName(),
                p.getDescription(),
                p.getModule()
        );
    }

    public Permission findById(String permissionId) throws SQLException {
        String sql = "SELECT * FROM permission WHERE permission_id = ?";
        return queryForObject(sql, permissionId);
    }

    public List<Permission> findAll() throws SQLException {
        String sql = "SELECT * FROM permission ORDER BY permission_id";
        return queryForList(sql);
    }

    public int update(Permission p) throws SQLException {
        String sql = "UPDATE permission SET permission_code = ?, permission_name = ?, description = ?, module = ? " +
                "WHERE permission_id = ?";
        return executeUpdate(sql,
                p.getPermissionCode(),
                p.getPermissionName(),
                p.getDescription(),
                p.getModule(),
                p.getPermissionId()
        );
    }

    public int delete(String permissionId) throws SQLException {
        String sql = "DELETE FROM permission WHERE permission_id = ?";
        return executeUpdate(sql, permissionId);
    }

    public int countPermissions() throws SQLException {
        String sql = "SELECT COUNT(*) FROM permission";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected Permission mapResultSetToEntity(ResultSet rs) throws SQLException {
        Permission p = new Permission();
        p.setPermissionId(rs.getString("permission_id"));
        p.setPermissionCode(rs.getString("permission_code"));
        p.setPermissionName(rs.getString("permission_name"));
        p.setDescription(rs.getString("description"));
        p.setModule(rs.getString("module"));
        return p;
    }
}

