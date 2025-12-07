package org.example.model;

import java.time.LocalDateTime;

public class Enclosure {
    // 对应表 enclosure
    private String enclosureId;        // enclosure_id VARCHAR(10)
    private String name;              // name VARCHAR(50)
    private int capacity;             // capacity INT
    private int currentAnimalCount;   // current_animal_count INT
    private String status;            // status VARCHAR(20)
    private String description;       // description TEXT
    private LocalDateTime createdAt;  // created_at TIMESTAMP
    private LocalDateTime updatedAt;  // updated_at TIMESTAMP

    public Enclosure() {}

    public Enclosure(String enclosureId, String name, int capacity) {
        this.enclosureId = enclosureId;
        this.name = name;
        this.capacity = capacity;
    }

    public String getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(String enclosureId) {
        this.enclosureId = enclosureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentAnimalCount() {
        return currentAnimalCount;
    }

    public void setCurrentAnimalCount(int currentAnimalCount) {
        this.currentAnimalCount = currentAnimalCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Enclosure{" +
                "enclosureId='" + enclosureId + '\'' +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", currentAnimalCount=" + currentAnimalCount +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}