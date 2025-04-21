package com.ims.backend.service;

import com.ims.backend.entity.Location;
import com.ims.backend.entity.Student;
import com.ims.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByLocation(Location location) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getLocation() != null && student.getLocation().getId().equals(location.getId()))
                .toList();
    }

}
