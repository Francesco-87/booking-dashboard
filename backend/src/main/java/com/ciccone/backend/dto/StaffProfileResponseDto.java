package com.ciccone.backend.dto;

import java.time.OffsetDateTime;


// DTO for staff profile data returned in API responses; uses immutable final fields
public class StaffProfileResponseDto {

    // Staff profile's unique identifier from the database
    private final Long id;
    // Reference to the associated user account
    private final Long userId;
    // Display name of the staff member
    private final String displayName;
    // Flag indicating if staff member is available/active
    private final Boolean isActive;
    // Timestamp when staff profile was created
    private final OffsetDateTime createdAt;
    // Timestamp when staff profile was last updated
    private final OffsetDateTime updatedAt;

    // All-args constructor to initialize immutable response DTO with all fields
    public StaffProfileResponseDto(Long id, Long userId, String displayName,
                                   Boolean isActive, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.displayName = displayName;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter methods for all immutable fields
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public Boolean getIsActive() { return isActive; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}