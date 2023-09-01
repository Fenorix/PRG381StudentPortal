package com.example.prg381.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prg381.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
}
