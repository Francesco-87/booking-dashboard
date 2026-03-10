package com.ciccone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ciccone.backend.entity.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    
}
