package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Feed {
    private String feedId;             // feed_id VARCHAR(10)
    private String name;              // name VARCHAR(50)
    private String category;          // category VARCHAR(30)
    private BigDecimal stockQuantity; // stock_quantity NUMERIC(10,2)
    private String unit;              // unit VARCHAR(10)
    private BigDecimal safetyStock;   // safety_stock NUMERIC(10,2)
    private String supplier;          // supplier VARCHAR(100)
    private BigDecimal unitPrice;     // unit_price NUMERIC(10,2)
    private String createdBy;         // created_by VARCHAR(20)
    private LocalDateTime createdAt;  // created_at TIMESTAMP
    private String updatedBy;         // updated_by VARCHAR(20)
    private LocalDateTime updatedAt;  // updated_at TIMESTAMP

    public String getFeedId() { return feedId; }
    public void setFeedId(String feedId) { this.feedId = feedId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(BigDecimal stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public BigDecimal getSafetyStock() { return safetyStock; }
    public void setSafetyStock(BigDecimal safetyStock) { this.safetyStock = safetyStock; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Feed{" +
                "feedId='" + feedId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", stockQuantity=" + (stockQuantity != null ? stockQuantity : "0") +
                ", unit='" + unit + '\'' +
                ", safetyStock=" + (safetyStock != null ? safetyStock : "") +
                ", supplier='" + supplier + '\'' +
                ", unitPrice=" + (unitPrice != null ? unitPrice : "") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
