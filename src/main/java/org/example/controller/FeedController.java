package org.example.controller;

import org.example.model.Feed;
import org.example.service.FeedService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller contract for Feed operations.
 * Example mappings:
 * - GET /api/feeds
 * - GET /api/feeds/{id}
 * - POST /api/feeds
 * - PUT /api/feeds/{id}
 * - DELETE /api/feeds/{id}
 * - POST /api/feeds/{id}/increase
 * - POST /api/feeds/{id}/decrease
 */
public class FeedController {
    private final FeedService service = new FeedService();

    public List<Feed> listAll() throws SQLException { return service.listAllFeeds(); }
    public Feed get(String id) throws SQLException { return service.getFeed(id); }
    public void create(Feed f) throws SQLException { service.createFeed(f); }
    public void update(Feed f) throws SQLException { service.updateFeed(f); }
    public void delete(String id) throws SQLException { service.deleteFeed(id); }
    public void increaseStock(String id, BigDecimal qty) throws SQLException { service.increaseStock(id, qty); }
    public void decreaseStock(String id, BigDecimal qty) throws SQLException { service.decreaseStock(id, qty); }
}

