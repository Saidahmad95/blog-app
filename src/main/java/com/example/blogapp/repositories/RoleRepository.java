package com.example.blogapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.entities.Role;
import com.example.blogapp.enums.ERole;

public interface RoleRepository extends JpaRepository<Role,Long>{
    Optional<Role>findByName(ERole name);
}
