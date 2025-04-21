package com.ims.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String role;

    private Long locationId;       // ✅ Used for dropdown selection
    private String locationName;   // ✅ Display only
}
