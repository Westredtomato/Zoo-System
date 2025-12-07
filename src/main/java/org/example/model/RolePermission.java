package org.example.model;

import java.time.LocalDateTime;

public class RolePermission {
    private String role;           // role
    private String permissionId;   // permission_id
    private LocalDateTime grantedAt; // granted_at
    private String grantedBy;      // granted_by

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPermissionId() { return permissionId; }
    public void setPermissionId(String permissionId) { this.permissionId = permissionId; }
    public LocalDateTime getGrantedAt() { return grantedAt; }
    public void setGrantedAt(LocalDateTime grantedAt) { this.grantedAt = grantedAt; }
    public String getGrantedBy() { return grantedBy; }
    public void setGrantedBy(String grantedBy) { this.grantedBy = grantedBy; }
}

