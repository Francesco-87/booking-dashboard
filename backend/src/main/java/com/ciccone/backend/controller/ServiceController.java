package com.ciccone.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.ciccone.backend.dto.ServiceRequestDto;
import com.ciccone.backend.dto.ServiceResponseDto;
import com.ciccone.backend.service.ServiceService;

import jakarta.validation.Valid;

// REST controller that handles all HTTP requests related to service operations
@RestController
// Maps all service-related endpoints to the /api/services base path
@RequestMapping("/api/services")
// Allows requests from the frontend running on localhost:5173 to access these endpoints
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceController {

    // Dependency injection: ServiceService is injected through constructor
    private final ServiceService serviceService;

    // Constructor that accepts ServiceService dependency
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // POST endpoint to create a new service; returns 201 Created status
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponseDto createService(@RequestBody @Valid ServiceRequestDto service) {
        // Delegates to service layer to process service creation with validated input
        return serviceService.createService(service);
    }

    // GET endpoint to retrieve all available services from the database
    @GetMapping
    public List<ServiceResponseDto> getAllServices() {
        // Returns a list of all services offered in the booking system
        return serviceService.getAllServices();
    }

    // GET endpoint to retrieve a specific service by its ID
    @GetMapping("/{id}")
    public ServiceResponseDto getServiceById(@PathVariable Long id) {
        // Retrieves a single service by ID from the service layer
        return serviceService.getServiceById(id);
    }

    // PUT endpoint to fully update an existing service's information
    @PutMapping("/{id}")
    public ServiceResponseDto updateService(@PathVariable Long id, @RequestBody @Valid ServiceRequestDto service) {
        // Updates service data with validated input and returns updated service
        return serviceService.updateService(id, service);
    }

    // DELETE endpoint to remove a service from the database; returns 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        // Deletes a service by ID; returns no content on success
        serviceService.deleteService(id);
    }
}