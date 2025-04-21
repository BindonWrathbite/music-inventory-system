package com.ims.backend.mapper;

import com.ims.backend.dto.StudentDTO;
import com.ims.backend.entity.Location;
import com.ims.backend.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDTO toDto(Student student) {
        if (student == null) return null;

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());

        if (student.getLocation() != null) {
            dto.setLocationId(student.getLocation().getId());
            dto.setLocationName(student.getLocation().getName());
        }

        return dto;
    }

    public Student toEntity(StudentDTO dto, Location location) {
        if (dto == null) return null;

        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setLocation(location);
        return student;
    }
}
