package com.ciccone.backend.dto;

import com.ciccone.backend.entity.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDto {

    @NotBlank
    private String fullName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String passwordHash;
    @NotNull
     private UserRole role;
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
