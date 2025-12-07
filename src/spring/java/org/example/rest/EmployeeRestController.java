package org.example.rest;

import org.example.controller.EmployeeController;
import org.example.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Employee", description = "员工管理相关 API")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/employees")
public class EmployeeRestController {
    private final EmployeeController contract = new EmployeeController();

    @Operation(summary = "列出所有员工")
    @GetMapping
    public List<Employee> listAll() throws SQLException { return contract.listAll(); }

    @Operation(summary = "根据 ID 获取员工")
    @GetMapping("/{id}")
    public Employee get(@Parameter(description = "员工ID") @PathVariable String id) throws SQLException { return contract.get(id); }

    @Operation(summary = "创建员工")
    @PostMapping
    public ResponseEntity<Void> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "员工对象") @RequestBody Employee e) throws SQLException { contract.create(e); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "更新员工")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Parameter(description = "员工ID") @PathVariable String id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "员工对象") @RequestBody Employee e) throws SQLException { e.setEmployeeId(id); contract.update(e); return ResponseEntity.ok().build(); }

    @Operation(summary = "删除员工")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "员工ID") @PathVariable String id) throws SQLException { contract.delete(id); return ResponseEntity.noContent().build(); }

    @Operation(summary = "为员工分配展区")
    @PostMapping("/{id}/assign-enclosure")
    public ResponseEntity<Void> assignEnclosure(@Parameter(description = "员工ID") @PathVariable String id, @Parameter(description = "展区ID") @RequestParam String enclosureId) throws SQLException { contract.assignEnclosure(id, enclosureId); return ResponseEntity.ok().build(); }
}
