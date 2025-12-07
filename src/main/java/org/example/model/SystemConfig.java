package org.example.model;

import java.time.LocalDateTime;

public class SystemConfig {
    private String configKey;       // config_key
    private String configValue;     // config_value
    private String configType;      // config_type
    private String description;     // description
    private boolean publicConfig;   // is_public
    private String updatedBy;       // updated_by
    private LocalDateTime updatedAt;// updated_at

    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }
    public String getConfigType() { return configType; }
    public void setConfigType(String configType) { this.configType = configType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isPublicConfig() { return publicConfig; }
    public void setPublicConfig(boolean publicConfig) { this.publicConfig = publicConfig; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

