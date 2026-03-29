package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ciccone.backend.dto.StaffProfileMapper;
import com.ciccone.backend.dto.StaffProfileRequestDto;
import com.ciccone.backend.dto.StaffProfileResponseDto;
import com.ciccone.backend.entity.ServiceEntity;
import com.ciccone.backend.entity.StaffProfileEntity;
import com.ciccone.backend.exception.ResourceNotFoundException;
import com.ciccone.backend.repository.ServiceRepository;
import com.ciccone.backend.repository.StaffProfileRepository;

@Service
public class StaffProfileService {

    private final StaffProfileRepository staffProfileRepository;
    private final StaffProfileMapper staffProfileMapper;
    private final ServiceRepository serviceRepository;

    public StaffProfileService(
            StaffProfileRepository staffProfileRepository,
            StaffProfileMapper staffProfileMapper,
            ServiceRepository serviceRepository
    ) {
        this.staffProfileRepository = staffProfileRepository;
        this.staffProfileMapper = staffProfileMapper;
        this.serviceRepository = serviceRepository;
    }

    public StaffProfileResponseDto createStaffProfile(StaffProfileRequestDto staffProfileRequestDto) {
        StaffProfileEntity staffProfile = staffProfileMapper.toEntity(staffProfileRequestDto);

        OffsetDateTime now = OffsetDateTime.now();
        staffProfile.setCreatedAt(now);
        staffProfile.setUpdatedAt(now);

        return staffProfileMapper.toResponseDto(staffProfileRepository.save(staffProfile));
    }

    public List<StaffProfileResponseDto> getAllStaffProfiles() {
        return staffProfileRepository.findAll().stream()
                .map(staffProfileMapper::toResponseDto)
                .toList();
    }

    public StaffProfileResponseDto getStaffProfileById(Long id) {
        StaffProfileEntity staffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        return staffProfileMapper.toResponseDto(staffProfile);
    }

    public StaffProfileResponseDto updateStaffProfile(Long id, StaffProfileRequestDto updatedStaffProfile) {
        StaffProfileEntity existingStaffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        existingStaffProfile.setUserId(updatedStaffProfile.getUserId());
        existingStaffProfile.setDisplayName(updatedStaffProfile.getDisplayName());

        if (updatedStaffProfile.getIsActive() != null) {
            existingStaffProfile.setIsActive(updatedStaffProfile.getIsActive());
        }

        existingStaffProfile.setUpdatedAt(OffsetDateTime.now());

        return staffProfileMapper.toResponseDto(staffProfileRepository.save(existingStaffProfile));
    }

    public void deleteStaffProfile(Long id) {
        StaffProfileEntity existingStaffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        staffProfileRepository.delete(existingStaffProfile);
    }

    public void addServiceToStaffProfile(Long staffProfileId, Long serviceId) {
        StaffProfileEntity staffProfile = staffProfileRepository.findById(staffProfileId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        staffProfile.getServices().add(service);
        staffProfile.setUpdatedAt(OffsetDateTime.now());

        staffProfileRepository.save(staffProfile);
    }
}