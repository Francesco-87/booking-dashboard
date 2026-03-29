package com.ciccone.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.ciccone.backend.dto.ServiceRequestDto;
import com.ciccone.backend.dto.ServiceResponseDto;
import com.ciccone.backend.service.ServiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponseDto createService(@RequestBody @Valid ServiceRequestDto service) {
        return serviceService.createService(service);
    }

    @GetMapping
    public List<ServiceResponseDto> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceResponseDto getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @PutMapping("/{id}")
    public ServiceResponseDto updateService(@PathVariable Long id, @RequestBody @Valid ServiceRequestDto service) {
        return serviceService.updateService(id, service);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}