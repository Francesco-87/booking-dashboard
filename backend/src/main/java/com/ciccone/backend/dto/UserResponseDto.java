package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

import com.ciccone.backend.entity.UserRole;

// DTO for user data returned in API responses; uses immutable final fields for thread-safety
public class UserResponseDto {

    // User's unique identifier from the database
    private final Long id;
    // User's full name
    private final String fullName;
    // User's email address
    private final String email;
    // User's role (ADMIN or CUSTOMER)
    private final UserRole role;
    // Flag indicating if user account is active
    private final Boolean isActive;
    // Timestamp when user record was created
    private final OffsetDateTime createdAt;
    // Timestamp when user record was last updated
    private final OffsetDateTime updatedAt;

    // All-args constructor to initialize immutable response DTO with all fields
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

    // Getter methods for all immutable fields
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public Boolean getIsActive() { return isActive; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}