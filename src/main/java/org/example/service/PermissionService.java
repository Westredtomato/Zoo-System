package org.example.service;

import org.example.dao.PermissionDAO;
import org.example.dao.RolePermissionDAO;
import org.example.model.Permission;
import org.example.model.RolePermission;

import java.sql.SQLException;
import java.util.List;

/**
 * 权限与角色-权限管理
 */
public class PermissionService {

    private final PermissionDAO permissionDAO = new PermissionDAO();
    private final RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();

    public void createPermission(Permission p) throws SQLException {
        permissionDAO.insert(p);
    }

    public void updatePermission(Permission p) throws SQLException {
        permissionDAO.update(p);
    }

    public void deletePermission(String permissionId) throws SQLException {
        permissionDAO.delete(permissionId);
    }

    public Permission getPermission(String permissionId) throws SQLException {
        return permissionDAO.findById(permissionId);
    }

    public List<Permission> listAllPermissions() throws SQLException {
        return permissionDAO.findAll();
    }

    public void assignPermissionToRole(String role, String permissionId, String grantedBy) throws SQLException {
        RolePermission rp = new RolePermission();
        rp.setRole(role);
        rp.setPermissionId(permissionId);
        rp.setGrantedBy(grantedBy);
        rolePermissionDAO.insert(rp);
    }

    public void revokePermissionFromRole(String role, String permissionId) throws SQLException {
        rolePermissionDAO.delete(role, permissionId);
    }

    public List<RolePermission> listRolePermissions() throws SQLException {
        return rolePermissionDAO.findAll();
    }
}

