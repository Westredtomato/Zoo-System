package org.example.service;

import org.example.dao.AnimalDAO;
import org.example.dao.EmployeeDAO;
import org.example.dao.FeedDAO;
import org.example.dao.FeedingRecordDAO;
import org.example.model.Animal;
import org.example.model.Employee;
import org.example.model.Feed;
import org.example.model.FeedingRecord;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * 投喂业务逻辑：
 * - 创建投喂记录并扣减饲料库存
 * - 审批/驳回投喂记录
 * - 基础查询
 */
public class FeedingService {

    private final FeedingRecordDAO feedingRecordDAO = new FeedingRecordDAO();
    private final FeedDAO feedDAO = new FeedDAO();
    private final AnimalDAO animalDAO = new AnimalDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    /**
     * 创建投喂记录：
     * - 校验动物/饲料/员工存在
     * - 校验饲料库存是否足够
     * - 写入 feeding_record
     * - 扣减 feed.stock_quantity
     */
    public void createFeedingRecord(FeedingRecord record) throws SQLException {
        // 校验动物
        Animal animal = animalDAO.findById(record.getAnimalId());
        if (animal == null) {
            throw new IllegalArgumentException("动物不存在: " + record.getAnimalId());
        }
        // 校验饲料
        Feed feed = feedDAO.findById(record.getFeedId());
        if (feed == null) {
            throw new IllegalArgumentException("饲料不存在: " + record.getFeedId());
        }
        // 校验员工
        Employee emp = employeeDAO.findById(record.getEmployeeId());
        if (emp == null) {
            throw new IllegalArgumentException("员工不存在: " + record.getEmployeeId());
        }

        BigDecimal qty = record.getQuantity();
        if (qty == null || qty.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("投喂数量必须大于 0");
        }
        if (feed.getStockQuantity().compareTo(qty) < 0) {
            throw new IllegalStateException("饲料库存不足: " + record.getFeedId());
        }

        // 插入投喂记录
        feedingRecordDAO.insert(record);

        // 扣减饲料库存
        feed.setStockQuantity(feed.getStockQuantity().subtract(qty));
        feedDAO.update(feed);
    }

    /** 审批投喂记录 */
    public void approveFeeding(String recordId, String verifierId, String notes) throws SQLException {
        FeedingRecord record = feedingRecordDAO.findById(recordId);
        if (record == null) {
            throw new IllegalArgumentException("投喂记录不存在: " + recordId);
        }
        Employee verifier = employeeDAO.findById(verifierId);
        if (verifier == null) {
            throw new IllegalArgumentException("审批人不存在: " + verifierId);
        }
        record.setStatus("approved");
        record.setVerifiedBy(verifierId);
        record.setVerificationNotes(notes);
        record.setVerificationTime(java.time.LocalDateTime.now());
        feedingRecordDAO.update(record);
    }

    /** 驳回投喂记录（不回滚库存，只更新状态和备注） */
    public void rejectFeeding(String recordId, String verifierId, String notes) throws SQLException {
        FeedingRecord record = feedingRecordDAO.findById(recordId);
        if (record == null) {
            throw new IllegalArgumentException("投喂记录不存在: " + recordId);
        }
        Employee verifier = employeeDAO.findById(verifierId);
        if (verifier == null) {
            throw new IllegalArgumentException("审批人不存在: " + verifierId);
        }
        record.setStatus("rejected");
        record.setVerifiedBy(verifierId);
        record.setVerificationNotes(notes);
        record.setVerificationTime(java.time.LocalDateTime.now());
        feedingRecordDAO.update(record);
    }

    public FeedingRecord getFeedingRecord(String recordId) throws SQLException {
        return feedingRecordDAO.findById(recordId);
    }

    public List<FeedingRecord> listAllFeedingRecords() throws SQLException {
        return feedingRecordDAO.findAll();
    }

    public int countFeedingRecords() throws SQLException {
        return feedingRecordDAO.countRecords();
    }
}

