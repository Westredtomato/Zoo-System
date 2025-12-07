package org.example.rest;

import org.example.controller.AnimalController;
import org.example.model.Animal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Animal", description = "动物管理相关 API")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/animals")
public class AnimalRestController {
    private final AnimalController contract = new AnimalController();

    @Operation(summary = "列出所有动物")
    @GetMapping
    public List<Animal> listAll() throws SQLException { return contract.listAll(); }

    @Operation(summary = "根据 ID 获取动物")
    @GetMapping("/{id}")
    public Animal get(@Parameter(description = "动物ID") @PathVariable String id) throws SQLException { return contract.get(id); }

    @Operation(summary = "创建动物")
    @PostMapping
    public ResponseEntity<Void> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "动物对象") @RequestBody Animal a) throws SQLException { contract.create(a); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "更新动物")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Parameter(description = "动物ID") @PathVariable String id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "动物对象") @RequestBody Animal a) throws SQLException { a.setAnimalId(id); contract.update(a); return ResponseEntity.ok().build(); }

    @Operation(summary = "删除动物")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "动物ID") @PathVariable String id) throws SQLException { contract.delete(id); return ResponseEntity.noContent().build(); }

    @Operation(summary = "转移动物到展区")
    @PostMapping("/{id}/transfer")
    public ResponseEntity<Void> transfer(@Parameter(description = "动物ID") @PathVariable String id, @Parameter(description = "目标展区ID") @RequestParam String targetEnclosureId) throws SQLException { contract.transfer(id, targetEnclosureId); return ResponseEntity.ok().build(); }
}
