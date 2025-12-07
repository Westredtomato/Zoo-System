package org.example.rest;

import org.example.controller.AuthController;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Auth", description = "鉴权检查 API")
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final AuthController contract = new AuthController();

    @Operation(summary = "检查用户是否拥有指定权限")
    @GetMapping("/check")
    public boolean hasPermission(@Parameter(description = "用户ID") @RequestParam String userId, @Parameter(description = "权限编码") @RequestParam String permissionCode) throws SQLException {
        return contract.hasPermission(userId, permissionCode);
    }
}
