package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ciccone.backend.dto.ServiceMapper;
import com.ciccone.backend.dto.ServiceRequestDto;
import com.ciccone.backend.dto.ServiceResponseDto;
import com.ciccone.backend.entity.ServiceEntity;
import com.ciccone.backend.exception.ResourceNotFoundException;
import com.ciccone.backend.repository.ServiceRepository;

// Service layer for service business logic; handles CRUD operations for services
// Bridges between controllers (API) and repository (database) layers
@Service
public class ServiceService {

    // Dependencies injected via constructor for database access and DTO mapping
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    // Constructor for dependency injection
    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    // Creates a new service with timestamp management
    public ServiceResponseDto createService(ServiceRequestDto serviceRequestDto) {
        // Convert DTO to entity using mapper
        ServiceEntity service = serviceMapper.toEntity(serviceRequestDto);

        // Set creation and update timestamps to current time
        OffsetDateTime now = OffsetDateTime.now();
        service.setCreatedAt(now);
        service.setUpdatedAt(now);

        // Save to database and return as response DTO
        return serviceMapper.toResponseDto(serviceRepository.save(service));
    }

    // Retrieves all services from database and returns as DTOs
    public List<ServiceResponseDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(serviceMapper::toResponseDto)
                .toList();
    }

    // Retrieves a specific service by ID; throws exception if not found
    public ServiceResponseDto getServiceById(Long id) {
        // Fetch service or throw 404 Not Found exception
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        return serviceMapper.toResponseDto(service);
    }

    // Updates a service with new values
    public ServiceResponseDto updateService(Long id, ServiceRequestDto updatedService) {
        // Fetch existing service or throw 404 Not Found exception
        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        // Update all service fields with new values
        existingService.setName(updatedService.getName());
        existingService.setDescription(updatedService.getDescription());
        existingService.setDurationMinutes(updatedService.getDurationMinutes());
        existingService.setPriceCents(updatedService.getPriceCents());
        existingService.setIsActive(updatedService.getIsActive());
        
        // Update the modification timestamp
        existingService.setUpdatedAt(OffsetDateTime.now());

        // Save updated service to database and return as response DTO
        return serviceMapper.toResponseDto(serviceRepository.save(existingService));
    }

    // Deletes a service by ID; throws exception if not found
    public void deleteService(Long id) {
        // Fetch service or throw 404 Not Found exception
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        // Delete service from database
        serviceRepository.delete(service);
    }
}