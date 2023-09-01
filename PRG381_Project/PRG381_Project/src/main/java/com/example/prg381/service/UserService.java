package com.example.prg381.service;

import java.util.List;

import com.example.prg381.dto.StudentDto;
import com.example.prg381.entity.Student;

public interface UserService {
    void saveUser(StudentDto studentDto);

    Student findByEmail(String email);

    List<StudentDto> findAllUsers();
}
