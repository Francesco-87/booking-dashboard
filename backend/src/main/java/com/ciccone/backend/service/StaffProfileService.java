package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ciccone.backend.dto.StaffProfileMapper;
import com.ciccone.backend.dto.StaffProfileRequestDto;
import com.ciccone.backend.dto.StaffProfileResponseDto;
import com.ciccone.backend.entity.StaffProfileEntity;
import com.ciccone.backend.exception.ResourceNotFoundException;
import com.ciccone.backend.repository.StaffProfileRepository;

// Service layer for staff profile business logic; handles CRUD operations and staff-related processes
// Bridges between controllers (API) and repository (database) layers
@Service
public class StaffProfileService {

    // Dependencies injected via constructor for database access and DTO mapping
    private final StaffProfileRepository staffProfileRepository;
    private final StaffProfileMapper staffProfileMapper;
    

    // Constructor for dependency injection
    public StaffProfileService(
            StaffProfileRepository staffProfileRepository,
            StaffProfileMapper staffProfileMapper
            
    ) {
        this.staffProfileRepository = staffProfileRepository;
        this.staffProfileMapper = staffProfileMapper;
        
    }

    // Creates a new staff profile with timestamp management
    public StaffProfileResponseDto createStaffProfile(StaffProfileRequestDto staffProfileRequestDto) {
        // Convert DTO to entity using mapper
        StaffProfileEntity staffProfile = staffProfileMapper.toEntity(staffProfileRequestDto);

        // Set creation and update timestamps to current time
        OffsetDateTime now = OffsetDateTime.now();
        staffProfile.setCreatedAt(now);
        staffProfile.setUpdatedAt(now);

        // Save to database and return as response DTO
        return staffProfileMapper.toResponseDto(staffProfileRepository.save(staffProfile));
    }

    // Retrieves all staff profiles from database and returns as DTOs
    public List<StaffProfileResponseDto> getAllStaffProfiles() {
        return staffProfileRepository.findAll().stream()
                .map(staffProfileMapper::toResponseDto)
                .toList();
    }

    // Retrieves a specific staff profile by ID; throws exception if not found
    public StaffProfileResponseDto getStaffProfileById(Long id) {
        // Fetch staff profile or throw 404 Not Found exception
        StaffProfileEntity staffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        return staffProfileMapper.toResponseDto(staffProfile);
    }

    // Updates a staff profile with new values
    public StaffProfileResponseDto updateStaffProfile(Long id, StaffProfileRequestDto updatedStaffProfile) {
        // Fetch existing staff profile or throw 404 Not Found exception
        StaffProfileEntity existingStaffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        // Update staff profile information with new values
        existingStaffProfile.setUserId(updatedStaffProfile.getUserId());
        existingStaffProfile.setDisplayName(updatedStaffProfile.getDisplayName());

        // Update isActive only if explicitly provided
        if (updatedStaffProfile.getIsActive() != null) {
            existingStaffProfile.setIsActive(updatedStaffProfile.getIsActive());
        }

        // Update the modification timestamp
        existingStaffProfile.setUpdatedAt(OffsetDateTime.now());

        // Save updated staff profile to database and return as response DTO
        return staffProfileMapper.toResponseDto(staffProfileRepository.save(existingStaffProfile));
    }

    // Deletes a staff profile by ID; throws exception if not found
    public void deleteStaffProfile(Long id) {
        // Fetch staff profile or throw 404 Not Found exception
        StaffProfileEntity existingStaffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        // Delete staff profile from database
        staffProfileRepository.delete(existingStaffProfile);
    }

  }