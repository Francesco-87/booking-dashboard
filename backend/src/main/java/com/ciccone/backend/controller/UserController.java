package com.ciccone.backend.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ciccone.backend.dto.UserCreateRequestDto;
import com.ciccone.backend.dto.UserResponseDto;
import com.ciccone.backend.dto.UserUpdateRequestDto;
import com.ciccone.backend.service.UserService;

import jakarta.validation.Valid;

// REST controller that handles all HTTP requests related to user operations
@RestController
// Maps all user-related endpoints to the /api/users base path
@RequestMapping("/api/users")
// Allows requests from the frontend running on localhost:5173 to access these endpoints
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    // Dependency injection: UserService is injected through constructor
    private final UserService userService;

    // Constructor that accepts UserService dependency
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST endpoint to create a new user; returns 201 Created status
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody @Valid UserCreateRequestDto userRequestDto) {
        // Delegates to service layer to process user creation with validated input
        return userService.createUser(userRequestDto);
    }
        
    // GET endpoint to retrieve all users from the database
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        // Returns a list of all users in the system
        return userService.getAllUsers();
    }   

    // GET endpoint to retrieve a specific user by their ID
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        // Retrieves a single user by ID from the service layer
        return userService.getUserById(id);
    }

    // PUT endpoint to fully update an existing user's information
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto updatedUser) {
        // Updates user data with validated input and returns updated user
        return userService.updateUser(id, updatedUser);
    }
    
    // PATCH endpoint to update only the user's status field
    @PatchMapping("/{id}/status")
    public UserResponseDto updateStatus(@PathVariable Long id, @RequestBody UserUpdateRequestDto updatedUser) {
        // Partially updates user status without affecting other fields
        return userService.updateUser(id, updatedUser);
    }

    // DELETE endpoint to remove a user from the database; returns 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        // Deletes a user by ID; returns no content on success
        userService.deleteUser(id);
    }
}