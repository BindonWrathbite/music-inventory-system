package com.ims.backend.controller;

import com.ims.backend.dto.StudentDTO;
import com.ims.backend.entity.Student;
import com.ims.backend.entity.User;
import com.ims.backend.mapper.StudentMapper;
import com.ims.backend.service.StudentService;
import com.ims.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper, UserService userService) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.userService = userService;
    }

    // GET all students for the current user's location
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<StudentDTO>> getAllStudents(@AuthenticationPrincipal UserDetails authUser) {
        Optional<User> userOpt = userService.getUserByUsername(authUser.getUsername());
        if (userOpt.isEmpty() || userOpt.get().getLocation() == null) {
            return ResponseEntity.badRequest().build();
        }

        List<StudentDTO> students = studentService.getStudentsByLocation(userOpt.get().getLocation()).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(students);
    }

    // GET one student (only if at user's location)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id, @AuthenticationPrincipal UserDetails authUser) {
        Optional<User> userOpt = userService.getUserByUsername(authUser.getUsername());
        Optional<Student> studentOpt = studentService.getStudentById(id);

        if (userOpt.isEmpty() || studentOpt.isEmpty()) return ResponseEntity.notFound().build();
        if (!studentOpt.get().getLocation().getId().equals(userOpt.get().getLocation().getId())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(studentMapper.toDto(studentOpt.get()));
    }

    // CREATE a student (auto-assign user's location)
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO dto, @AuthenticationPrincipal UserDetails authUser) {
        Optional<User> userOpt = userService.getUserByUsername(authUser.getUsername());

        if (userOpt.isEmpty() || userOpt.get().getLocation() == null) {
            return ResponseEntity.badRequest().build();
        }

        Student student = studentMapper.toEntity(dto, userOpt.get().getLocation());
        Student saved = studentService.saveStudent(student);

        return ResponseEntity.ok(studentMapper.toDto(saved));
    }

    // UPDATE a student (only if student is at user's location)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO dto, @AuthenticationPrincipal UserDetails authUser) {
        Optional<User> userOpt = userService.getUserByUsername(authUser.getUsername());
        Optional<Student> existingOpt = studentService.getStudentById(id);

        if (userOpt.isEmpty() || existingOpt.isEmpty()) return ResponseEntity.notFound().build();
        if (!existingOpt.get().getLocation().getId().equals(userOpt.get().getLocation().getId())) {
            return ResponseEntity.status(403).build();
        }

        Student updated = studentMapper.toEntity(dto, existingOpt.get().getLocation());
        updated.setId(id);

        Student saved = studentService.saveStudent(updated);
        return ResponseEntity.ok(studentMapper.toDto(saved));
    }

    // DELETE a student (only if student is at user's location)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id, @AuthenticationPrincipal UserDetails authUser) {
        Optional<User> userOpt = userService.getUserByUsername(authUser.getUsername());
        Optional<Student> existingOpt = studentService.getStudentById(id);

        if (userOpt.isEmpty() || existingOpt.isEmpty()) return ResponseEntity.notFound().build();
        if (!existingOpt.get().getLocation().getId().equals(userOpt.get().getLocation().getId())) {
            return ResponseEntity.status(403).build();
        }

        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
