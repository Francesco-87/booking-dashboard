package com.ciccone.backend.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

// JPA entity representing a staff member's profile in the system
@Entity
@Table(name = "staff_profiles")
public class StaffProfileEntity {

    // Primary key; auto-generated unique identifier for each staff profile
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key reference to the associated user account; required field
    @Column(name = "user_id")
    @NotNull
    private Long userId;

    // Display name or professional name of the staff member; required field
    @Column(name = "display_name")
    @NotBlank
    private String displayName;

    // Flag indicating if staff member is available/active; defaults to true
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Timestamp when staff profile was created; managed by database
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // Timestamp when staff profile was last updated; managed by database
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // Default no-arg constructor required by JPA
    public StaffProfileEntity() {
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}