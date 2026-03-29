package com.ciccone.backend.dto;

import java.time.OffsetDateTime;


public class StaffProfileResponseDto {

    private final Long id;
    private final Long userId;
    private final String displayName;
    private final Boolean isActive;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    public StaffProfileResponseDto(Long id, Long userId, String displayName,
                                   Boolean isActive, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.displayName = displayName;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public Boolean getIsActive() { return isActive; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}