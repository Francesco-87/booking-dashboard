package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

import com.ciccone.backend.entity.UserRole;

public class UserResponseDto {

    private final Long id;
    private final String fullName;
    private final String email;
    private final UserRole role;
    private final Boolean isActive;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    public UserResponseDto(Long id, String fullName, String email, UserRole role,
                           Boolean isActive, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public Boolean getIsActive() { return isActive; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
