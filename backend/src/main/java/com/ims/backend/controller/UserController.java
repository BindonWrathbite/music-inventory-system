package com.ims.backend.controller;

import com.ims.backend.dto.PasswordChangeRequest;
import com.ims.backend.dto.UserDTO;
import com.ims.backend.entity.User;
import com.ims.backend.mapper.UserMapper;
import com.ims.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // 🔐 Admin-only: Get all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // 🔐 Admin-only: Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(userMapper.toDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔐 Admin-only: Create user
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        User saved = userService.saveUser(userMapper.toEntity(userDto));
        return ResponseEntity.ok(userMapper.toDto(saved));
    }

    // 🔐 Admin-only: Update user
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedDto) {
        Optional<User> existing = userService.getUserById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        User user = userMapper.toEntity(updatedDto);
        user.setId(id);
        User saved = userService.saveUser(user);
        return ResponseEntity.ok(userMapper.toDto(saved));
    }

    // 🔐 Admin-only: Delete user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 🔐 Admin or Self: Change password
    @PutMapping("/{username}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable String username,
            @RequestBody PasswordChangeRequest request,
            Authentication authentication) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        String currentUsername = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !username.equals(currentUsername)) {
            return ResponseEntity.status(403).body("Unauthorized to change this user's password.");
        }

        boolean updated = userService.updatePassword(user.get().getId(), request.getNewPassword());
        return updated
                ? ResponseEntity.ok("Password updated successfully.")
                : ResponseEntity.status(500).body("Failed to update password.");
    }

}
