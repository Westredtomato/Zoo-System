package org.example.rest;

import org.example.controller.LogController;
import org.example.model.LoginLog;
import org.example.model.OperationLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Log", description = "日志管理 API")
@RestController
@RequestMapping("/api/logs")
public class LogRestController {
    private final LogController contract = new LogController();

    @Operation(summary = "记录登录日志")
    @PostMapping("/login")
    public ResponseEntity<Void> logLogin(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "登录日志对象") @RequestBody LoginLog l) throws SQLException { contract.logLogin(l); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "记录操作日志")
    @PostMapping("/operation")
    public ResponseEntity<Void> logOperation(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "操作日志对象") @RequestBody OperationLog o) throws SQLException { contract.logOperation(o); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "列出登录日志")
    @GetMapping("/login")
    public List<LoginLog> listLoginLogs() throws SQLException { return contract.listLoginLogs(); }

    @Operation(summary = "列出操作日志")
    @GetMapping("/operation")
    public List<OperationLog> listOperationLogs() throws SQLException { return contract.listOperationLogs(); }
}
