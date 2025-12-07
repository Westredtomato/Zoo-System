package org.example.controller;

import org.example.model.Permission;
import org.example.model.RolePermission;
import org.example.service.PermissionService;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller contract for permission management and role-permission links.
 */
public class PermissionController {
    private final PermissionService service = new PermissionService();

    public void create(Permission p) throws SQLException { service.createPermission(p); }
    public void update(Permission p) throws SQLException { service.updatePermission(p); }
    public void delete(String id) throws SQLException { service.deletePermission(id); }
    public Permission get(String id) throws SQLException { return service.getPermission(id); }
    public List<Permission> listAll() throws SQLException { return service.listAllPermissions(); }
    public void assignToRole(String role, String permissionId, String grantedBy) throws SQLException { service.assignPermissionToRole(role, permissionId, grantedBy); }
    public void revokeFromRole(String role, String permissionId) throws SQLException { service.revokePermissionFromRole(role, permissionId); }
    public List<RolePermission> listRolePermissions() throws SQLException { return service.listRolePermissions(); }
}

