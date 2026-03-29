package com.ciccone.backend.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStaffProfile(@PathVariable Long id) {
        staffProfileService.deleteStaffProfile(id);
    }

    @PostMapping("/{staffId}/services/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignServiceToStaffProfile(@PathVariable Long staffId, @PathVariable Long serviceId) {
        staffProfileService.addServiceToStaffProfile(staffId, serviceId);
    }
}
