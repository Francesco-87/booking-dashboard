package com.ciccone.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ciccone.backend.entity.UserEntity;

// Spring Data JPA repository for UserEntity
// Provides built-in CRUD operations (Create, Read, Update, Delete) for user database operations
// Supports standard queries like findById, findAll, save, delete, etc.
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}