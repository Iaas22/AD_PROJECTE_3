package com.ra5.projecte3.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ra5.projecte3.dto.UserResponseDTO;
import com.ra5.projecte3.model.Role;
import com.ra5.projecte3.service.UserService;
import com.ra5.projecte3.dto.UserRequestDTO;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        UserResponseDTO user = userService.findById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable String role) {
        try {
            Role parsedRole = Role.valueOf(role.toUpperCase());
            return ResponseEntity.ok(userService.findByRole(parsedRole));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        UserResponseDTO user = userService.findByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
    @PostMapping
public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO request) {
    UserResponseDTO created = userService.create(request);
    if (created == null) {
        return ResponseEntity.status(409).build();
    }
    return ResponseEntity.status(201).body(created);
}

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable String id,
                                                @RequestBody UserRequestDTO request) {
        UserResponseDTO updated = userService.update(id, request);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = userService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
