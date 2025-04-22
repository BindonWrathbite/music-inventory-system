package com.ims.backend.mapper;

import com.ims.backend.dto.UserDTO;
import com.ims.backend.entity.Location;
import com.ims.backend.entity.User;
import com.ims.backend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(LocationRepository locationRepository, PasswordEncoder passwordEncoder) {
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO toDto(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());

        if (user.getLocation() != null) {
            dto.setLocationName(user.getLocation().getName());
            dto.setLocationId(user.getLocation().getId());
        }

        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getLocationId() != null) {
            locationRepository.findById(dto.getLocationId()).ifPresent(user::setLocation);
        } else {
            user.setLocation(null);
        }

        return user;
    }
}
