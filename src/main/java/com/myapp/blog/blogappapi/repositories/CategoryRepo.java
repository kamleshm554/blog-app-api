package com.myapp.blog.blogappapi.repositories;

import com.myapp.blog.blogappapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
