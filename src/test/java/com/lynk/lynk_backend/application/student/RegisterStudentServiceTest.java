package com.lynk.lynk_backend.application.student;

import com.lynk.lynk_backend.config.LynkProperties;
import com.lynk.lynk_backend.domain.student.Student;
import com.lynk.lynk_backend.domain.student.StudentRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class RegisterStudentServiceTest {

    private RegisterStudentService svcWithAllowedDomains(StudentRepository repo, String... domains) {
        LynkProperties props = new LynkProperties();
        props.setAllowedEmailDomains(List.of(domains));
        return new RegisterStudentService(repo, props);
        // constructor injection keeps it simple & testable
    }

    @Test
    void rejectsNonUniversityEmail() {
        StudentRepository repo = mock(StudentRepository.class);
        var svc = svcWithAllowedDomains(repo, "uni-wien.ac.at", "tuwien.ac.at");

        assertThatThrownBy(() -> svc.register("bob@gmail.com", "Bob"))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("not allowed");

        verifyNoInteractions(repo);
    }

    @Test
    void rejectsDuplicateEmail() {
        StudentRepository repo = mock(StudentRepository.class);
        when(repo.existsByEmail("alice@uni-wien.ac.at")).thenReturn(true);

        var svc = svcWithAllowedDomains(repo, "uni-wien.ac.at");

        assertThatThrownBy(() -> svc.register("alice@uni-wien.ac.at", "Alice"))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("already");

        verify(repo).existsByEmail("alice@uni-wien.ac.at");
        verify(repo, never()).save(any(Student.class));
    }

    @Test
    void savesNewStudent() {
        StudentRepository repo = mock(StudentRepository.class);
        when(repo.existsByEmail("eve@tuwien.ac.at")).thenReturn(false);
        when(repo.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));

        var svc = svcWithAllowedDomains(repo, "tuwien.ac.at");
        Student s = svc.register("eve@tuwien.ac.at", "Eve");

        verify(repo).save(any(Student.class));
        assert s.getEmail().equals("eve@tuwien.ac.at");
    }
}
