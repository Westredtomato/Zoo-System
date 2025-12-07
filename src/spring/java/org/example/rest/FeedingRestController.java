package org.example.rest;

import org.example.controller.FeedingController;
import org.example.model.FeedingRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Feeding", description = "投喂记录管理 API")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/feedings")
public class FeedingRestController {
    private final FeedingController contract = new FeedingController();

    @Operation(summary = "列出所有投喂记录")
    @GetMapping
    public List<FeedingRecord> listAll() throws SQLException { return contract.listAll(); }

    @Operation(summary = "根据 ID 获取投喂记录")
    @GetMapping("/{id}")
    public FeedingRecord get(@Parameter(description = "投喂记录ID") @PathVariable String id) throws SQLException { return contract.get(id); }

    @Operation(summary = "创建投喂记录")
    @PostMapping
    public ResponseEntity<Void> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "记录对象") @RequestBody FeedingRecord r) throws SQLException { contract.create(r); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "审批投喂记录 - 通过")
    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@Parameter(description = "记录ID") @PathVariable String id, @Parameter(description = "审核人ID") @RequestParam String verifierId, @RequestParam(required = false) String notes) throws SQLException { contract.approve(id, verifierId, notes == null ? "" : notes); return ResponseEntity.ok().build(); }

    @Operation(summary = "审批投喂记录 - 拒绝")
    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@Parameter(description = "记录ID") @PathVariable String id, @Parameter(description = "审核人ID") @RequestParam String verifierId, @RequestParam(required = false) String notes) throws SQLException { contract.reject(id, verifierId, notes == null ? "" : notes); return ResponseEntity.ok().build(); }
}
