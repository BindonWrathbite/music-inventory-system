package com.ims.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InstrumentDTO {

    private Long id;
    private String type;
    private String brand;
    private String serialNumber;
    private String inventoryNumber;
    private String repairs;
    private String condition;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private String notes;

    private Long locationId; // for dropdown selection and saving
    private String locationName; // for display purposes

    private Long assignedStudentId; // for dropdown selection and saving
    private String assignedStudentName; // for display purposes
}
