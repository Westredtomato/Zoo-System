package org.example.rest;

import org.example.controller.PermissionController;
import org.example.model.Permission;
import org.example.model.RolePermission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Permission", description = "权限管理 API")
@RestController
@RequestMapping("/api/permissions")
public class PermissionRestController {
    private final PermissionController contract = new PermissionController();

    @Operation(summary = "列出所有权限")
    @GetMapping
    public List<Permission> listAll() throws SQLException { return contract.listAll(); }

    @Operation(summary = "根据ID获取权限")
    @GetMapping("/{id}")
    public Permission get(@Parameter(description = "权限ID") @PathVariable String id) throws SQLException { return contract.get(id); }

    @Operation(summary = "创建权限")
    @PostMapping
    public ResponseEntity<Void> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "权限对象") @RequestBody Permission p) throws SQLException { contract.create(p); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "删除权限")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "权限ID") @PathVariable String id) throws SQLException { contract.delete(id); return ResponseEntity.noContent().build(); }

    @Operation(summary = "分配权限到角色")
    @PostMapping("/assign")
    public ResponseEntity<Void> assign(@Parameter(description = "角色名") @RequestParam String role, @Parameter(description = "权限ID") @RequestParam String permissionId, @Parameter(description = "分配人ID") @RequestParam String grantedBy) throws SQLException { contract.assignToRole(role, permissionId, grantedBy); return ResponseEntity.ok().build(); }

    @Operation(summary = "从角色回收权限")
    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(@Parameter(description = "角色名") @RequestParam String role, @Parameter(description = "权限ID") @RequestParam String permissionId) throws SQLException { contract.revokeFromRole(role, permissionId); return ResponseEntity.ok().build(); }

    @Operation(summary = "列出角色-权限映射")
    @GetMapping("/role-permissions")
    public List<RolePermission> listRolePermissions() throws SQLException { return contract.listRolePermissions(); }
}
