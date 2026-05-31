package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ciccone.backend.dto.UserMapper;
import com.ciccone.backend.dto.UserCreateRequestDto;
import com.ciccone.backend.dto.UserResponseDto;
import com.ciccone.backend.dto.UserUpdateRequestDto;
import com.ciccone.backend.entity.UserEntity;
import com.ciccone.backend.exception.ResourceNotFoundException;
import com.ciccone.backend.repository.UserRepository;

// Service layer for user business logic; handles CRUD operations and user-related processes
// Bridges between controllers (API) and repository (database) layers
@Service
public class UserService {

    // Dependencies injected via constructor for database access and DTO mapping
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Constructor for dependency injection
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Creates a new user with validation and timestamp management
    public UserResponseDto createUser(UserCreateRequestDto userRequestDto) {
        // Convert DTO to entity using mapper
        UserEntity user = userMapper.toEntity(userRequestDto);

        // Set creation and update timestamps to current time
        OffsetDateTime now = OffsetDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        // Save to database and return as response DTO
        return userMapper.toResponseDto(userRepository.save(user));
    }

    // Retrieves all users from database and returns as DTOs
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    // Retrieves a specific user by ID; throws exception if not found
    public UserResponseDto getUserById(Long id) {
        // Fetch user or throw 404 Not Found exception
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponseDto(user);
    }

    // Updates a user with full replacement of fields using UserCreateRequestDto
    public UserResponseDto updateUser(Long id, UserCreateRequestDto updatedUser) {
        // Fetch existing user or throw 404 Not Found exception
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update all fields with new values
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPasswordHash(updatedUser.getPasswordHash());
        existingUser.setRole(updatedUser.getRole());

        // Update isActive if provided
        if (updatedUser.getIsActive() != null) {
            existingUser.setIsActive(updatedUser.getIsActive());
        }

        // Update the modification timestamp
        existingUser.setUpdatedAt(OffsetDateTime.now());

        // Save updated user to database and return as response DTO
        return userMapper.toResponseDto(userRepository.save(existingUser));
    }

    // Updates a user with partial updates using UserUpdateRequestDto
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto updatedUser) {
        // Fetch existing user or throw 404 Not Found exception
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update user information with new values
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());

        // Only update password if provided and not blank (allows password to remain unchanged)
        if (updatedUser.getPasswordHash() != null && !updatedUser.getPasswordHash().isBlank()) {
            existingUser.setPasswordHash(updatedUser.getPasswordHash());
        }

        // Update isActive only if explicitly provided
        if (updatedUser.getIsActive() != null) {
            existingUser.setIsActive(updatedUser.getIsActive());
        }

        // Update the modification timestamp
        existingUser.setUpdatedAt(OffsetDateTime.now());

        // Save updated user to database and return as response DTO
        return userMapper.toResponseDto(userRepository.save(existingUser));
    }

    // Deletes a user by ID; throws exception if user not found
    public void deleteUser(Long id) {
        // Fetch user or throw 404 Not Found exception
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Delete user from database
        userRepository.delete(existingUser);
    }
}

    