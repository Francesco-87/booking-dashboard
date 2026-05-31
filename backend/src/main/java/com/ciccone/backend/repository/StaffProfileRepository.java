package com.ciccone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciccone.backend.entity.StaffProfileEntity;


// Spring Data JPA repository for StaffProfileEntity
// Provides built-in CRUD operations (Create, Read, Update, Delete) for staff profile database operations
// Supports standard queries like findById, findAll, save, delete, etc.
public interface StaffProfileRepository extends JpaRepository<StaffProfileEntity, Long> {

}