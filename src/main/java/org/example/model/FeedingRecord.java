package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FeedingRecord {
    private String recordId;          // record_id VARCHAR(15)
    private LocalDateTime feedingTime;// feeding_time
    private String animalId;         // animal_id
    private String feedId;           // feed_id
    private String employeeId;       // employee_id
    private BigDecimal quantity;     // quantity
    private String notes;            // notes
    private String status;           // status
    private String createdBy;        // created_by
    private String verifiedBy;       // verified_by
    private LocalDateTime verificationTime; // verification_time
    private String verificationNotes;// verification_notes
    private LocalDateTime createdAt; // created_at
    private LocalDateTime updatedAt; // updated_at

    // getters/setters
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public LocalDateTime getFeedingTime() { return feedingTime; }
    public void setFeedingTime(LocalDateTime feedingTime) { this.feedingTime = feedingTime; }
    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }
    public String getFeedId() { return feedId; }
    public void setFeedId(String feedId) { this.feedId = feedId; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
    public LocalDateTime getVerificationTime() { return verificationTime; }
    public void setVerificationTime(LocalDateTime verificationTime) { this.verificationTime = verificationTime; }
    public String getVerificationNotes() { return verificationNotes; }
    public void setVerificationNotes(String verificationNotes) { this.verificationNotes = verificationNotes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FeedingRecord{");
        sb.append("recordId=").append(recordId != null ? recordId : "null");
        sb.append(", feedingTime=").append(feedingTime != null ? feedingTime.toString() : "null");
        sb.append(", animalId=").append(animalId != null ? animalId : "null");
        sb.append(", feedId=").append(feedId != null ? feedId : "null");
        sb.append(", employeeId=").append(employeeId != null ? employeeId : "null");
        sb.append(", quantity=").append(quantity != null ? quantity.toPlainString() : "null");
        sb.append(", status=").append(status != null ? status : "null");
        sb.append(", createdAt=").append(createdAt != null ? createdAt.toString() : "null");
        sb.append('}');
        return sb.toString();
    }
}
