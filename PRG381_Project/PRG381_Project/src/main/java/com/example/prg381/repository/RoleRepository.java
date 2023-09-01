package com.example.prg381.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prg381.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
