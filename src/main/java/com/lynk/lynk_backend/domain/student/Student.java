package com.lynk.lynk_backend.domain.student;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name= "students")
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name="full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(name="created_at", columnDefinition="timestamptz", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public Student () {}

    public Student (String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
