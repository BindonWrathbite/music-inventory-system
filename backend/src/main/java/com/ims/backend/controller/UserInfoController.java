package com.ims.backend.controller;

import com.ims.backend.entity.User;
import com.ims.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserInfoController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/whoami")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok().body(new UserInfoResponse(user.getUsername(), user.getRole())))
                .orElse(ResponseEntity.notFound().build());
    }

    // Inner DTO for response
    public record UserInfoResponse(String username, String role) {}
}
