package org.example.dao;

import org.example.model.RolePermission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RolePermissionDAO extends BaseDAO<RolePermission> {

    public int insert(RolePermission rp) throws SQLException {
        String sql = "INSERT INTO role_permission (role, permission_id, granted_at, granted_by) VALUES (?, ?, ?, ?)";
        return executeUpdate(sql,
                rp.getRole(),
                rp.getPermissionId(),
                rp.getGrantedAt(),
                rp.getGrantedBy()
        );
    }

    public RolePermission findByPk(String role, String permissionId) throws SQLException {
        String sql = "SELECT * FROM role_permission WHERE role = ? AND permission_id = ?";
        return queryForObject(sql, role, permissionId);
    }

    public List<RolePermission> findAll() throws SQLException {
        String sql = "SELECT * FROM role_permission ORDER BY role, permission_id";
        return queryForList(sql);
    }

    public int delete(String role, String permissionId) throws SQLException {
        String sql = "DELETE FROM role_permission WHERE role = ? AND permission_id = ?";
        return executeUpdate(sql, role, permissionId);
    }

    public int countRolePermissions() throws SQLException {
        String sql = "SELECT COUNT(*) FROM role_permission";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected RolePermission mapResultSetToEntity(ResultSet rs) throws SQLException {
        RolePermission rp = new RolePermission();
        rp.setRole(rs.getString("role"));
        rp.setPermissionId(rs.getString("permission_id"));
        var grantedAt = rs.getTimestamp("granted_at");
        if (grantedAt != null) {
            rp.setGrantedAt(grantedAt.toLocalDateTime());
        }
        rp.setGrantedBy(rs.getString("granted_by"));
        return rp;
    }
}

