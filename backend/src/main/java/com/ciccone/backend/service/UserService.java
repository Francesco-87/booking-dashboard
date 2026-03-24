package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ciccone.backend.dto.UserMapper;
import com.ciccone.backend.dto.UserRequestDto;
import com.ciccone.backend.dto.UserResponseDto;
import com.ciccone.backend.entity.UserEntity;
import com.ciccone.backend.exception.ResourceNotFoundException;
import com.ciccone.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;        

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto){
        UserEntity userEntity = userMapper.toEntity(userRequestDto);
        OffsetDateTime now = OffsetDateTime.now();
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);
        return userMapper.toResponseDto(userRepository.save(userEntity));
       
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream()
            .map(userMapper::toResponseDto)
            .toList();
    }

    public UserResponseDto getUserById(Long id){
        return userMapper.toResponseDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto updatedUser) {

        UserEntity userEntity = userMapper.toEntity(updatedUser);

        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingUser.setFullName(userEntity.getFullName());
        existingUser.setEmail(userEntity.getEmail());
        existingUser.setPasswordHash(userEntity.getPasswordHash());
        existingUser.setRole(userEntity.getRole());
        if (updatedUser.getIsActive() != null) {existingUser.setIsActive(updatedUser.getIsActive());}    
        existingUser.setUpdatedAt(OffsetDateTime.now());   
        return userMapper.toResponseDto(userRepository.save(existingUser));
    }

    public void deleteUser(Long id) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(existingUser);
    }



}

    
