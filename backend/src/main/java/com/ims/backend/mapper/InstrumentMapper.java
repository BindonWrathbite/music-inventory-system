package com.ims.backend.mapper;

import com.ims.backend.dto.InstrumentDTO;
import com.ims.backend.entity.Instrument;
import com.ims.backend.entity.Location;
import com.ims.backend.entity.Student;

public class InstrumentMapper {

    public static InstrumentDTO toDTO(Instrument instrument) {
        InstrumentDTO dto = new InstrumentDTO();
        dto.setId(instrument.getId());
        dto.setType(instrument.getType());
        dto.setBrand(instrument.getBrand());
        dto.setSerialNumber(instrument.getSerialNumber());
        dto.setInventoryNumber(instrument.getInventoryNumber());
        dto.setRepairs(instrument.getRepairs());
        dto.setCondition(instrument.getCondition());
        dto.setPurchaseDate(instrument.getPurchaseDate());
        dto.setPurchasePrice(instrument.getPurchasePrice());
        dto.setNotes(instrument.getNotes());

        dto.setLocationName(instrument.getLocation() != null ? instrument.getLocation().getName() : null);
        dto.setAssignedStudentName(instrument.getAssignedStudent() != null
                ? instrument.getAssignedStudent().getFirstName() + " " + instrument.getAssignedStudent().getLastName()
                : null);

        return dto;
    }

    public static Instrument toEntity(InstrumentDTO dto, Location location, Student student) {
        Instrument instrument = new Instrument();
        instrument.setId(dto.getId());
        instrument.setType(dto.getType());
        instrument.setBrand(dto.getBrand());
        instrument.setSerialNumber(dto.getSerialNumber());
        instrument.setInventoryNumber(dto.getInventoryNumber());
        instrument.setRepairs(dto.getRepairs());
        instrument.setCondition(dto.getCondition());
        instrument.setPurchaseDate(dto.getPurchaseDate());
        instrument.setPurchasePrice(dto.getPurchasePrice());
        instrument.setNotes(dto.getNotes());

        instrument.setLocation(location); // may be null
        instrument.setAssignedStudent(student); // may be null

        return instrument;
    }
}
