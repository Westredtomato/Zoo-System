package org.example.model;

import java.time.LocalDateTime;

public class LoginLog {
    private int logId;            // log_id (sequence)
    private String userId;        // user_id
    private LocalDateTime loginTime; // login_time
    private String ipAddress;     // ip_address
    private String userAgent;     // user_agent
    private String loginStatus;   // login_status
    private String failureReason; // failure_reason

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public String getLoginStatus() { return loginStatus; }
    public void setLoginStatus(String loginStatus) { this.loginStatus = loginStatus; }
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
}

