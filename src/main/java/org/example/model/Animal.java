package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Animal {
    // 对应表 animal
    private String animalId;          // animal_id VARCHAR(10)
    private String name;             // name VARCHAR(50)
    private String species;          // species VARCHAR(50)
    private String gender;           // gender CHAR(1) - 'M' / 'F'
    private LocalDate birthDate;     // birth_date DATE
    private String enclosureId;      // enclosure_id VARCHAR(10)
    private String healthStatus;     // health_status VARCHAR(20)
    private String description;      // description TEXT
    private String createdBy;        // created_by VARCHAR(20)
    private LocalDateTime createdAt; // created_at TIMESTAMP
    private String updatedBy;        // updated_by VARCHAR(20)
    private LocalDateTime updatedAt; // updated_at TIMESTAMP

    public Animal() {}

    public Animal(String animalId, String name, String species, String gender, String enclosureId) {
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.gender = gender;
        this.enclosureId = enclosureId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(String enclosureId) {
        this.enclosureId = enclosureId;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId='" + animalId + '\'' +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", enclosureId='" + enclosureId + '\'' +
                ", healthStatus='" + healthStatus + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}