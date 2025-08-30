package com.lynk.lynk_backend.application.student;

import com.lynk.lynk_backend.config.LynkProperties;
import com.lynk.lynk_backend.domain.student.Student;
import com.lynk.lynk_backend.domain.student.StudentRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Locale;

@Service
public class RegisterStudentService {
    private final StudentRepository repo;
    private final LynkProperties props;

    public RegisterStudentService(StudentRepository repo, LynkProperties props) {
        this.repo = repo;
        this.props = props;
    }

    private boolean isAllowedDomain(String email) {
        int at = email.indexOf('@');
        if (at < 0) return false;
        String domain = email.substring(at + 1).toLowerCase(Locale.ROOT);
        return props.getAllowedEmailDomains().stream()
                .map(d -> d.toLowerCase(Locale.ROOT))
                .anyMatch(domain::endsWith);
    }

    @Transactional
    public Student register(String email, String fullName) {
        if (!isAllowedDomain(email)) {
            throw new ValidationException("Email domain is not allowed");
        }
        if (repo.existsByEmail(email)) {
            throw new ValidationException("Email already registered");
        }
        return repo.save(new Student(email, fullName));
    }
}
