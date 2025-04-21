package com.ims.backend.mapper;

import com.ims.backend.dto.UserDTO;
import com.ims.backend.entity.Location;
import com.ims.backend.entity.User;
import com.ims.backend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final LocationRepository locationRepository;

    @Autowired
    public UserMapper(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public UserDTO toDto(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());

        if (user.getLocation() != null) {
            dto.setLocationName(user.getLocation().getName());
        }

        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());

        if (dto.getLocationId() != null) {
            locationRepository.findById(dto.getLocationId()).ifPresent(user::setLocation);
        } else {
            user.setLocation(null);
        }

        return user;
    }
}
