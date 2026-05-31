package com.ciccone.backend.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// JPA entity representing a user account in the system
@Entity
@Table(name = "users")  
public class UserEntity {

    // Primary key; auto-generated unique identifier for each user
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // User's full name; required and stored in database
    @Column(name = "full_name", nullable = false)
    private String fullName;

    // User's email address; required, must be unique across all users
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Hashed password for secure authentication; required field
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // User's role (ADMIN, STAFF, or CUSTOMER); determines access permissions
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "user_role")
    private UserRole role;

    // Flag indicating if user account is active; defaults to true
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    // Timestamp when user record was created; managed by database
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    // Timestamp when user record was last updated; managed by database
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // Default no-arg constructor required by JPA
    public UserEntity() {
    };

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
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