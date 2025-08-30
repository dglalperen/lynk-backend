package com.lynk.lynk_backend.presentation;

import com.lynk.lynk_backend.application.student.RegisterStudentService;
import com.lynk.lynk_backend.domain.student.Student;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    public record RegisterRequest(@NotBlank @Email String email,
                                  @NotBlank String fullName) {}

    private final RegisterStudentService register;

    public StudentController(RegisterStudentService register) {
        this.register = register;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RegisterRequest req) {
        Student s = register.register(req.email(), req.fullName());
        return ResponseEntity.created(URI.create("/api/students/" + s.getId())).body(s);
    }
}
