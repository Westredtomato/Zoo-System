package org.example.rest;

import org.example.controller.UserController;
import org.example.model.UserAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "User", description = "用户与账号管理 API")
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserController contract = new UserController();

    @Operation(summary = "创建用户")
    @PostMapping
    public ResponseEntity<Void> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户对象") @RequestBody UserAccount u) throws SQLException { contract.create(u); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "根据ID获取用户")
    @GetMapping("/{id}")
    public UserAccount get(@Parameter(description = "用户ID") @PathVariable String id) throws SQLException { return contract.getById(id); }

    @Operation(summary = "根据用户名获取用户")
    @GetMapping("/by-username")
    public UserAccount getByUsername(@Parameter(description = "用户名") @RequestParam String username) throws SQLException { return contract.getByUsername(username); }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Parameter(description = "用户ID") @PathVariable String id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户对象") @RequestBody UserAccount u) throws SQLException { u.setUserId(id); contract.update(u); return ResponseEntity.ok().build(); }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "用户ID") @PathVariable String id) throws SQLException { contract.delete(id); return ResponseEntity.noContent().build(); }

    @Operation(summary = "用户认证（JWT）")
    @PostMapping("/auth")
    public ResponseEntity<java.util.Map<String,Object>> auth(@Parameter(description = "用户名") @RequestParam String username, @Parameter(description = "密码") @RequestParam String password) throws SQLException {
        boolean ok = contract.authenticate(username, password, "127.0.0.1", "api");
        if (!ok) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(java.util.Map.of("error","unauthorized","message","用户名或密码错误"));
        org.example.model.UserAccount u = new org.example.service.UserService().getUserByUsername(username);
        String role = u != null ? u.getRole() : "";
        String token = org.example.security.JwtUtil.generateToken(u.getUserId(), role, 3600);
        return ResponseEntity.ok(java.util.Map.of("token", token, "userId", u.getUserId(), "role", role));
    }
}
