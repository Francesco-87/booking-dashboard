package com.ciccone.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciccone.backend.dto.StaffProfileRequestDto;
import com.ciccone.backend.dto.StaffProfileResponseDto;
import com.ciccone.backend.service.StaffProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/staff-profiles")
public class StaffProfileController {

    private final StaffProfileService staffProfileService;

    public StaffProfileController(StaffProfileService staffProfileService) {
        this.staffProfileService = staffProfileService;
    }

    @PostMapping
    public StaffProfileResponseDto createStaffProfile(@RequestBody @Valid StaffProfileRequestDto staffProfileRequestDto) {
        return staffProfileService.createStaffProfile(staffProfileRequestDto);
    }

    @GetMapping
    public List<StaffProfileResponseDto> getAllStaffProfiles() {
        return staffProfileService.getAllStaffProfiles();
    }

    @GetMapping("/{id}")
    public StaffProfileResponseDto getStaffProfileById(@PathVariable Long id) {
        return staffProfileService.getStaffProfileById(id);
    }

    @PutMapping("/{id}")
    public StaffProfileResponseDto updateStaffProfile(@PathVariable Long id, @RequestBody @Valid StaffProfileRequestDto updatedStaffProfile) {
        return staffProfileService.updateStaffProfile(id, updatedStaffProfile);
    }

    @DeleteMapping("/{id}")
    public void deleteStaffProfile(@PathVariable Long id) {
        staffProfileService.deleteStaffProfile(id);
    }

    // Additional endpoint to assign a service to a staff profile
    @PostMapping("/{staffId}/services/{serviceId}")
    public void assignServiceToStaffProfile(@PathVariable Long staffId, @PathVariable Long serviceId) {
        staffProfileService.addServiceToStaffProfile(staffId, serviceId);
    }

}
