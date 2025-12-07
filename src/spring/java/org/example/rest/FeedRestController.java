package org.example.rest;

import org.example.controller.FeedController;
import org.example.model.Feed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Feed", description = "饲料管理相关 API")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/feeds")
public class FeedRestController {
    private final FeedController contract = new FeedController();

    @Operation(summary = "列出所有饲料")
    @GetMapping
    public List<Feed> listAll() throws SQLException { return contract.listAll(); }

    @Operation(summary = "根据 ID 获取饲料")
    @GetMapping("/{id}")
    public Feed get(@Parameter(description = "饲料ID") @PathVariable String id) throws SQLException { return contract.get(id); }

    @Operation(summary = "创建饲料")
    @PostMapping
    public ResponseEntity<Void> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "饲料对象") @RequestBody Feed f) throws SQLException { contract.create(f); return ResponseEntity.status(HttpStatus.CREATED).build(); }

    @Operation(summary = "更新饲料")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Parameter(description = "饲料ID") @PathVariable String id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "饲料对象") @RequestBody Feed f) throws SQLException { f.setFeedId(id); contract.update(f); return ResponseEntity.ok().build(); }

    @Operation(summary = "删除饲料")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "饲料ID") @PathVariable String id) throws SQLException { contract.delete(id); return ResponseEntity.noContent().build(); }

    @Operation(summary = "增加饲料库存")
    @PostMapping("/{id}/increase")
    public ResponseEntity<Void> increase(@Parameter(description = "饲料ID") @PathVariable String id, @Parameter(description = "增加数量") @RequestParam BigDecimal qty) throws SQLException { contract.increaseStock(id, qty); return ResponseEntity.ok().build(); }

    @Operation(summary = "减少饲料库存")
    @PostMapping("/{id}/decrease")
    public ResponseEntity<Void> decrease(@Parameter(description = "饲料ID") @PathVariable String id, @Parameter(description = "减少数量") @RequestParam BigDecimal qty) throws SQLException { contract.decreaseStock(id, qty); return ResponseEntity.ok().build(); }
}
