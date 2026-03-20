package com.ciccone.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ciccone.backend.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
