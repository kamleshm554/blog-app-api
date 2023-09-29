package com.myapp.blog.blogappapi.repositories;

import com.myapp.blog.blogappapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
