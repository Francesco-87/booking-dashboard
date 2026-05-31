package com.ciccone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ciccone.backend.entity.ServiceEntity;

// Spring Data JPA repository for ServiceEntity
// Provides built-in CRUD operations (Create, Read, Update, Delete) for service database operations
// Supports standard queries like findById, findAll, save, delete, etc.
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    
}