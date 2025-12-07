package org.example.dao;

import org.example.model.Feed;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class FeedDAO extends BaseDAO<Feed> {

    public int insert(Feed feed) throws SQLException {
        String sql = "INSERT INTO feed (feed_id, name, category, stock_quantity, unit, safety_stock, " +
                "supplier, unit_price, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                feed.getFeedId(),
                feed.getName(),
                feed.getCategory(),
                feed.getStockQuantity(),
                feed.getUnit(),
                feed.getSafetyStock(),
                feed.getSupplier(),
                feed.getUnitPrice(),
                feed.getCreatedBy()
        );
    }

    public Feed findById(String feedId) throws SQLException {
        String sql = "SELECT * FROM feed WHERE feed_id = ?";
        return queryForObject(sql, feedId);
    }

    public List<Feed> findAll() throws SQLException {
        String sql = "SELECT * FROM feed ORDER BY feed_id";
        return queryForList(sql);
    }

    public int update(Feed feed) throws SQLException {
        String sql = "UPDATE feed SET name = ?, category = ?, stock_quantity = ?, unit = ?, safety_stock = ?, " +
                "supplier = ?, unit_price = ?, updated_by = ?, updated_at = CURRENT_TIMESTAMP WHERE feed_id = ?";
        return executeUpdate(sql,
                feed.getName(),
                feed.getCategory(),
                feed.getStockQuantity(),
                feed.getUnit(),
                feed.getSafetyStock(),
                feed.getSupplier(),
                feed.getUnitPrice(),
                feed.getUpdatedBy(),
                feed.getFeedId()
        );
    }

    public int delete(String feedId) throws SQLException {
        String sql = "DELETE FROM feed WHERE feed_id = ?";
        return executeUpdate(sql, feedId);
    }

    public int countFeeds() throws SQLException {
        String sql = "SELECT COUNT(*) FROM feed";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected Feed mapResultSetToEntity(ResultSet rs) throws SQLException {
        Feed f = new Feed();
        f.setFeedId(rs.getString("feed_id"));
        f.setName(rs.getString("name"));
        f.setCategory(rs.getString("category"));
        f.setStockQuantity(rs.getBigDecimal("stock_quantity"));
        f.setUnit(rs.getString("unit"));
        f.setSafetyStock(rs.getBigDecimal("safety_stock"));
        f.setSupplier(rs.getString("supplier"));
        f.setUnitPrice(rs.getBigDecimal("unit_price"));
        f.setCreatedBy(rs.getString("created_by"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            f.setCreatedAt(createdAt.toLocalDateTime());
        }
        f.setUpdatedBy(rs.getString("updated_by"));
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            f.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return f;
    }
}
