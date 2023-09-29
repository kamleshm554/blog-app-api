package com.myapp.blog.blogappapi.services;

import com.myapp.blog.blogappapi.payloads.CommentDto;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto, Integer postId);
    void  deleteComment(Integer commentId);
}
