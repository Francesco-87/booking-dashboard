package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ciccone.backend.dto.ServiceMapper;
import com.ciccone.backend.dto.ServiceRequestDto;
import com.ciccone.backend.dto.ServiceResponseDto;
import com.ciccone.backend.entity.ServiceEntity;
import com.ciccone.backend.repository.ServiceRepository;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper = new ServiceMapper();
    

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ServiceResponseDto createService(ServiceRequestDto serviceRequestDto) {
         ServiceEntity serviceEntity = serviceMapper.toEntity(serviceRequestDto);
        OffsetDateTime now = OffsetDateTime.now();
        serviceEntity.setCreatedAt(now);
        serviceEntity.setUpdatedAt(now);
        return serviceMapper.toResponseDto(serviceRepository.save(serviceEntity));
    }

    public List<ServiceResponseDto> getAllServices() {
         return serviceRepository.findAll().stream()
            .map(serviceMapper::toResponseDto)
            .toList();
}

    public ServiceResponseDto getServiceById(Long id) {
        return serviceMapper.toResponseDto(serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found")));
    }

    public ServiceResponseDto updateService(Long id, ServiceRequestDto updatedService) {

        ServiceEntity serviceEntity = serviceMapper.toEntity(updatedService);

        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existingService.setName(serviceEntity.getName());
        existingService.setDescription(serviceEntity.getDescription());
        existingService.setDurationMinutes(serviceEntity.getDurationMinutes());
        existingService.setPriceCents(serviceEntity.getPriceCents());
        existingService.setIsActive(serviceEntity.getIsActive());
        existingService.setUpdatedAt(OffsetDateTime.now());

        return serviceMapper.toResponseDto(serviceRepository.save(existingService));
}

    public void deleteService(Long id) {
            ServiceEntity service = serviceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Service not found"));
            serviceRepository.delete(service);
}

    
}
