package com.ciccone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciccone.backend.entity.StaffProfileEntity;


public interface StaffProfileRepository extends JpaRepository<StaffProfileEntity, Long> {

}