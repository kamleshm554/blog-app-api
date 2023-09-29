package com.myapp.blog.blogappapi.repositories;

import com.myapp.blog.blogappapi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
