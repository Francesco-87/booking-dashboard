package com.ciccone.backend.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ciccone.backend.dto.StaffProfileRequestDto;
import com.ciccone.backend.dto.StaffProfileResponseDto;
import com.ciccone.backend.service.StaffProfileService;

import jakarta.validation.Valid;

// REST controller that handles all HTTP requests related to staff profile operations
@RestController
// Maps all staff profile endpoints to the /api/staff-profiles base path
@RequestMapping("/api/staff-profiles")
// Allows requests from the frontend running on localhost:5173 to access these endpoints
@CrossOrigin(origins = "http://localhost:5173")
public class StaffProfileController {

    // Dependency injection: StaffProfileService is injected through constructor
    private final StaffProfileService staffProfileService;

    // Constructor that accepts StaffProfileService dependency
    public StaffProfileController(StaffProfileService staffProfileService) {
        this.staffProfileService = staffProfileService;
    }

    // POST endpoint to create a new staff profile; returns 201 Created status
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StaffProfileResponseDto createStaffProfile(@RequestBody @Valid StaffProfileRequestDto staffProfileRequestDto) {
        // Delegates to service layer to process staff profile creation with validated input
        return staffProfileService.createStaffProfile(staffProfileRequestDto);
    }

    // GET endpoint to retrieve all staff profiles from the database
    @GetMapping
    public List<StaffProfileResponseDto> getAllStaffProfiles() {
        // Returns a list of all staff members and their profiles
        return staffProfileService.getAllStaffProfiles();
    }

    // GET endpoint to retrieve a specific staff profile by its ID
    @GetMapping("/{id}")
    public StaffProfileResponseDto getStaffProfileById(@PathVariable Long id) {
        // Retrieves a single staff profile by ID from the service layer
        return staffProfileService.getStaffProfileById(id);
    }

    // PUT endpoint to fully update an existing staff profile's information
    @PutMapping("/{id}")
    public StaffProfileResponseDto updateStaffProfile(@PathVariable Long id, @RequestBody @Valid StaffProfileRequestDto updatedStaffProfile) {
        // Updates staff profile data with validated input and returns updated profile
        return staffProfileService.updateStaffProfile(id, updatedStaffProfile);
    }

    // DELETE endpoint to remove a staff profile from the database; returns 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStaffProfile(@PathVariable Long id) {
        // Deletes a staff profile by ID; returns no content on success
        staffProfileService.deleteStaffProfile(id);
    }

}