package org.example.dao;

import org.example.model.FeedingRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class FeedingRecordDAO extends BaseDAO<FeedingRecord> {

    public int insert(FeedingRecord record) throws SQLException {
        String sql = "INSERT INTO feeding_record (record_id, feeding_time, animal_id, feed_id, employee_id, " +
                "quantity, notes, status, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                record.getRecordId(),
                record.getFeedingTime(),
                record.getAnimalId(),
                record.getFeedId(),
                record.getEmployeeId(),
                record.getQuantity(),
                record.getNotes(),
                record.getStatus(),
                record.getCreatedBy()
        );
    }

    public FeedingRecord findById(String recordId) throws SQLException {
        String sql = "SELECT * FROM feeding_record WHERE record_id = ?";
        return queryForObject(sql, recordId);
    }

    public List<FeedingRecord> findAll() throws SQLException {
        String sql = "SELECT * FROM feeding_record ORDER BY record_id";
        return queryForList(sql);
    }

    public int update(FeedingRecord record) throws SQLException {
        String sql = "UPDATE feeding_record SET feeding_time = ?, animal_id = ?, feed_id = ?, employee_id = ?, " +
                "quantity = ?, notes = ?, status = ?, verified_by = ?, verification_time = ?, verification_notes = ?, " +
                "updated_at = CURRENT_TIMESTAMP WHERE record_id = ?";
        return executeUpdate(sql,
                record.getFeedingTime(),
                record.getAnimalId(),
                record.getFeedId(),
                record.getEmployeeId(),
                record.getQuantity(),
                record.getNotes(),
                record.getStatus(),
                record.getVerifiedBy(),
                record.getVerificationTime(),
                record.getVerificationNotes(),
                record.getRecordId()
        );
    }

    public int delete(String recordId) throws SQLException {
        String sql = "DELETE FROM feeding_record WHERE record_id = ?";
        return executeUpdate(sql, recordId);
    }

    public int countRecords() throws SQLException {
        String sql = "SELECT COUNT(*) FROM feeding_record";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected FeedingRecord mapResultSetToEntity(ResultSet rs) throws SQLException {
        FeedingRecord r = new FeedingRecord();
        r.setRecordId(rs.getString("record_id"));
        Timestamp feedingTime = rs.getTimestamp("feeding_time");
        if (feedingTime != null) {
            r.setFeedingTime(feedingTime.toLocalDateTime());
        }
        r.setAnimalId(rs.getString("animal_id"));
        r.setFeedId(rs.getString("feed_id"));
        r.setEmployeeId(rs.getString("employee_id"));
        r.setQuantity(rs.getBigDecimal("quantity"));
        r.setNotes(rs.getString("notes"));
        r.setStatus(rs.getString("status"));
        r.setCreatedBy(rs.getString("created_by"));
        r.setVerifiedBy(rs.getString("verified_by"));
        Timestamp verTime = rs.getTimestamp("verification_time");
        if (verTime != null) {
            r.setVerificationTime(verTime.toLocalDateTime());
        }
        r.setVerificationNotes(rs.getString("verification_notes"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            r.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            r.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return r;
    }
}

