package org.example.controller;

import org.example.service.AuthService;

import java.sql.SQLException;

/**
 * Controller contract for authorization checks.
 * Example mapping:
 * - GET /api/auth/check?userId=...&perm=...
 */
public class AuthController {
    private final AuthService authService = new AuthService();

    public boolean hasPermission(String userId, String permissionCode) throws SQLException {
        return authService.userHasPermission(userId, permissionCode);
    }
}

