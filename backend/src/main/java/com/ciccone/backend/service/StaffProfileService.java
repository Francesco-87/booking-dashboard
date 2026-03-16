package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ciccone.backend.dto.StaffProfileMapper;
import com.ciccone.backend.dto.StaffProfileRequestDto;
import com.ciccone.backend.dto.StaffProfileResponseDto;
import com.ciccone.backend.entity.StaffProfileEntity;
import com.ciccone.backend.repository.StaffProfileRepository;

@Service
public class StaffProfileService {

    private final StaffProfileRepository staffProfileRepository;
    private final StaffProfileMapper staffProfileMapper;

    public StaffProfileService(StaffProfileRepository staffProfileRepository, StaffProfileMapper staffProfileMapper) {
        this.staffProfileRepository = staffProfileRepository;
        this.staffProfileMapper = staffProfileMapper;
    }

    public StaffProfileResponseDto createStaffProfile(StaffProfileRequestDto staffProfileRequestDto) {
            StaffProfileEntity staffEntity = staffProfileMapper.toEntity(staffProfileRequestDto);
            OffsetDateTime now = OffsetDateTime.now();
        staffEntity.setCreatedAt(now);
        staffEntity.setUpdatedAt(now);
        return staffProfileMapper.toResponseDto(staffProfileRepository.save(staffEntity));
    }

    public List<StaffProfileResponseDto> getAllStaffProfiles() {
        return staffProfileRepository.findAll().stream()
            .map(staffProfileMapper::toResponseDto)
            .toList();
    }

    public StaffProfileResponseDto getStaffProfileById(Long id) {
        return staffProfileMapper.toResponseDto(staffProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Staff profile not found")));
    }

    public StaffProfileResponseDto updateStaffProfile(Long id, StaffProfileRequestDto updatedStaffProfile) {

        StaffProfileEntity staffEntity = staffProfileMapper.toEntity(updatedStaffProfile);

        StaffProfileEntity existingStaffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff profile not found"));

        existingStaffProfile.setUserId(staffEntity.getUserId());
        existingStaffProfile.setDisplayName(staffEntity.getDisplayName());
        if (updatedStaffProfile.getIsActive() != null) {
        existingStaffProfile.setIsActive(updatedStaffProfile.getIsActive());
}
        existingStaffProfile.setUpdatedAt(OffsetDateTime.now());

        return staffProfileMapper.toResponseDto(staffProfileRepository.save(existingStaffProfile));
    }

    public void deleteStaffProfile(Long id) {
        StaffProfileEntity existingStaffProfile = staffProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff profile not found"));
        staffProfileRepository.delete(existingStaffProfile);

    }

}
