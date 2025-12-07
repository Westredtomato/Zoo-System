package org.example.rest;

import org.example.controller.EnclosureController;
import org.example.model.Enclosure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Enclosure", description = "展区管理相关 API")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/enclosures")
public class EnclosureRestController {
    private final EnclosureController contract = new EnclosureController();

    @Operation(summary = "列出所有展区")
    @GetMapping
    public List<Enclosure> listAll() throws SQLException {
        return contract.listAll();
    }

    @Operation(summary = "根据 ID 获取展区")
    @GetMapping("/{id}")
    public Enclosure get(@Parameter(description = "展区ID") @PathVariable String id) throws SQLException {
        return contract.get(id);
    }

    @Operation(summary = "创建展区")
    @PostMapping
    public ResponseEntity<Void> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "展区对象") @RequestBody Enclosure e) throws SQLException {
        contract.create(e);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "更新展区")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Parameter(description = "展区ID") @PathVariable String id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "展区对象") @RequestBody Enclosure e) throws SQLException {
        e.setEnclosureId(id);
        contract.update(e);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "删除展区")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "展区ID") @PathVariable String id) throws SQLException {
        contract.delete(id);
        return ResponseEntity.noContent().build();
    }
}
