package org.example.service;

import org.example.dao.PermissionDAO;
import org.example.dao.RolePermissionDAO;
import org.example.dao.UserAccountDAO;
import org.example.model.Permission;
import org.example.model.RolePermission;
import org.example.model.UserAccount;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 授权服务：基于角色与 role_permission 检查权限
 */
public class AuthService {

    private final UserAccountDAO userDAO = new UserAccountDAO();
    private final PermissionDAO permissionDAO = new PermissionDAO();
    private final RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();

    /**
     * 判断用户是否拥有指定 permission_code （以 module 或 code 形式判断）
     */
    public boolean userHasPermission(String userId, String permissionCode) throws SQLException {
        UserAccount user = userDAO.findById(userId);
        if (user == null) return false;
        String role = user.getRole();
        if (role == null) return false;

        // 获取角色的权限 id 列表
        List<RolePermission> rps = rolePermissionDAO.findAll();
        List<String> permIdsForRole = rps.stream()
                .filter(rp -> rp.getRole().equals(role))
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 获取这些 permission 对象并判断 code
        for (String pid : permIdsForRole) {
            Permission p = permissionDAO.findById(pid);
            if (p != null && (permissionCode.equals(p.getPermissionCode()) || permissionCode.equals(p.getModule()))) {
                return true;
            }
        }
        return false;
    }
}

