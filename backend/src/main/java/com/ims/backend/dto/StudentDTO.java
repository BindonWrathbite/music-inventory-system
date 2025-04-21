package com.ims.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;

    private Long locationId;
    private String locationName;
}
