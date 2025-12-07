package org.example.controller;

import org.example.model.FeedingRecord;
import org.example.service.FeedingService;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller contract for FeedingRecord operations.
 * Example mappings:
 * - GET /api/feedings
 * - GET /api/feedings/{id}
 * - POST /api/feedings
 * - POST /api/feedings/{id}/approve
 */
public class FeedingController {
    private final FeedingService service = new FeedingService();

    public List<FeedingRecord> listAll() throws SQLException { return service.listAllFeedingRecords(); }
    public FeedingRecord get(String id) throws SQLException { return service.getFeedingRecord(id); }
    public void create(FeedingRecord r) throws SQLException { service.createFeedingRecord(r); }
    public void approve(String recordId, String verifierId, String notes) throws SQLException { service.approveFeeding(recordId, verifierId, notes); }
    public void reject(String recordId, String verifierId, String notes) throws SQLException { service.rejectFeeding(recordId, verifierId, notes); }
}

