package com.example.prg381.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.prg381.dto.StudentDto;
import com.example.prg381.entity.Role;
import com.example.prg381.entity.Student;
import com.example.prg381.repository.RoleRepository;
import com.example.prg381.repository.StudentRepository;
import com.example.prg381.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private StudentRepository studentRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(StudentRepository studentRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getFirstName() + " " + studentDto.getLastName());
        student.setEmail(studentDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        student.setAddress(studentDto.getAddress());
        student.setCourse(studentDto.getCourse());
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        student.setRoles(Arrays.asList(role));
        studentRepository.save(student);
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public List<StudentDto> findAllUsers() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private StudentDto convertEntityToDto(Student student){
        StudentDto studentDto = new StudentDto();
        String[] name = student.getName().split(" ");
        studentDto.setFirstName(name[0]);
        studentDto.setLastName(name[1]);
        studentDto.setEmail(student.getEmail());
        studentDto.setAddress(student.getAddress());
        studentDto.setCourse(student.getCourse());
        return studentDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
