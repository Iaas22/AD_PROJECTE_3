package com.ra5.projecte3.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ra5.projecte3.dto.UserResponseDTO;
import com.ra5.projecte3.mapper.UserMapper;
import com.ra5.projecte3.model.Role;
import com.ra5.projecte3.model.User;
import com.ra5.projecte3.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper; 

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDto).orElse(null);
    }

    public List<UserResponseDTO> findByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(userMapper::toDto).orElse(null);
    }
}
