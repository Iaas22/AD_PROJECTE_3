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
import com.ra5.projecte3.dto.UserRequestDTO;
import com.ra5.projecte3.model.AcademicProfile;

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

    public UserResponseDTO create(UserRequestDTO request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        return null;
    }

    User user = userMapper.toEntity(request);
    User saved = userRepository.save(user);
    return userMapper.toDto(saved);
}

public UserResponseDTO update(String id, UserRequestDTO request) {
    User existing = userRepository.findById(id).orElse(null);
    if (existing == null) return null;

    existing.setFirstName(request.getFirstName());
    existing.setLastName(request.getLastName());
    existing.setEmail(request.getEmail());
    existing.setUsername(request.getUsername());
    existing.setPassword(request.getPassword());
    existing.setRole(request.getRole());

    if (request.getGrade() != null) {
        AcademicProfile profile = new AcademicProfile();
        profile.setGrade(request.getGrade());
        profile.setCourse(request.getCourse());
        profile.setObservations(request.getObservations());
        existing.setAcademicProfile(profile);
    } else {
        existing.setAcademicProfile(null);
    }

    User updated = userRepository.save(existing);
    return userMapper.toDto(updated);
}

public boolean delete(String id) {
    if (!userRepository.existsById(id)) return false;
    userRepository.deleteById(id);
    return true;
}
}
