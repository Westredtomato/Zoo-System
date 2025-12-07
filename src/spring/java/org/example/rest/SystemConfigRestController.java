package org.example.rest;

import org.example.controller.SystemConfigController;
import org.example.model.SystemConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "SystemConfig", description = "系统配置管理 API")
@RestController
@RequestMapping("/api/configs")
public class SystemConfigRestController {
    private final SystemConfigController contract = new SystemConfigController();

    @Operation(summary = "根据键获取配置")
    @GetMapping("/{key}")
    public SystemConfig get(@Parameter(description = "配置键") @PathVariable String key) throws SQLException { return contract.get(key); }

    @Operation(summary = "列出所有配置")
    @GetMapping
    public List<SystemConfig> listAll() throws SQLException { return contract.listAll(); }

    @Operation(summary = "设置配置")
    @PostMapping
    public ResponseEntity<Void> set(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "配置对象") @RequestBody SystemConfig cfg) throws SQLException { contract.set(cfg); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "删除配置")
    @DeleteMapping("/{key}")
    public ResponseEntity<Void> delete(@Parameter(description = "配置键") @PathVariable String key) throws SQLException { contract.delete(key); return ResponseEntity.noContent().build(); }
}
