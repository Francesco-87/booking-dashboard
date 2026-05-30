package com.ciccone.backend.dto;

import com.ciccone.backend.entity.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO for user creation requests; used when creating a new user account
public class UserCreateRequestDto {

    // User's full name; must not be blank
    @NotBlank
    private String fullName;

    // User's email address; must be valid email format and not blank
    @Email
    @NotBlank
    private String email;

    // Hashed password; must not be blank
    @NotBlank
    private String passwordHash;

    // User's role (ADMIN or CUSTOMER); required field
    @NotNull
    private UserRole role;

    // Flag indicating if user account is active; optional with default value
    private Boolean isActive;

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
}