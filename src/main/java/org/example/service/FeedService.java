package org.example.service;

import org.example.dao.FeedDAO;
import org.example.model.Feed;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * 饲料管理业务逻辑：
 * - 饲料信息 CRUD
 * - 库存增减
 * - 安全库存预警
 */
public class FeedService {

    private final FeedDAO feedDAO = new FeedDAO();

    public void createFeed(Feed feed) throws SQLException {
        if (feed.getStockQuantity() == null) {
            feed.setStockQuantity(BigDecimal.ZERO);
        }
        feedDAO.insert(feed);
    }

    public void updateFeed(Feed feed) throws SQLException {
        feedDAO.update(feed);
    }

    public void deleteFeed(String feedId) throws SQLException {
        feedDAO.delete(feedId);
    }

    public Feed getFeed(String feedId) throws SQLException {
        return feedDAO.findById(feedId);
    }

    public List<Feed> listAllFeeds() throws SQLException {
        return feedDAO.findAll();
    }

    public int countFeeds() throws SQLException {
        return feedDAO.countFeeds();
    }

    /** 增加库存（一般用于采购入库） */
    public void increaseStock(String feedId, BigDecimal quantity) throws SQLException {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("增加库存数量必须大于 0");
        }
        Feed feed = feedDAO.findById(feedId);
        if (feed == null) {
            throw new IllegalArgumentException("饲料不存在: " + feedId);
        }
        BigDecimal newQty = feed.getStockQuantity().add(quantity);
        feed.setStockQuantity(newQty);
        feedDAO.update(feed);
    }

    /** 减少库存（用于投喂消耗），会检查是否足够 */
    public void decreaseStock(String feedId, BigDecimal quantity) throws SQLException {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("减少库存数量必须大于 0");
        }
        Feed feed = feedDAO.findById(feedId);
        if (feed == null) {
            throw new IllegalArgumentException("饲料不存在: " + feedId);
        }
        BigDecimal newQty = feed.getStockQuantity().subtract(quantity);
        if (newQty.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("库存不足: " + feedId);
        }
        feed.setStockQuantity(newQty);
        feedDAO.update(feed);
    }

    /** 是否低于安全库存，用于简单预警 */
    public boolean isBelowSafetyStock(Feed feed) {
        if (feed.getSafetyStock() == null) {
            return false;
        }
        return feed.getStockQuantity().compareTo(feed.getSafetyStock()) < 0;
    }
}

