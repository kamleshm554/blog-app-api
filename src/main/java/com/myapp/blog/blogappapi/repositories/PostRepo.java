package com.myapp.blog.blogappapi.repositories;

import com.myapp.blog.blogappapi.entities.Category;
import com.myapp.blog.blogappapi.entities.Post;
import com.myapp.blog.blogappapi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {


    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);


}
