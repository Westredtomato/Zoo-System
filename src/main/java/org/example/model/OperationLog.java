package org.example.model;

import java.time.LocalDateTime;

public class OperationLog {
    private int logId;              // log_id
    private String userId;          // user_id
    private LocalDateTime operationTime; // operation_time
    private String module;          // module
    private String operationType;   // operation_type
    private String tableName;       // table_name
    private String recordId;        // record_id
    private String oldValues;       // old_values
    private String newValues;       // new_values
    private String ipAddress;       // ip_address
    private String description;     // description

    // getters/setters
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public LocalDateTime getOperationTime() { return operationTime; }
    public void setOperationTime(LocalDateTime operationTime) { this.operationTime = operationTime; }
    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }
    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public String getOldValues() { return oldValues; }
    public void setOldValues(String oldValues) { this.oldValues = oldValues; }
    public String getNewValues() { return newValues; }
    public void setNewValues(String newValues) { this.newValues = newValues; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

