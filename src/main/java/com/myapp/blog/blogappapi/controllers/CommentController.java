package com.myapp.blog.blogappapi.controllers;

import com.myapp.blog.blogappapi.entities.Comment;
import com.myapp.blog.blogappapi.payloads.ApiResponse;
import com.myapp.blog.blogappapi.payloads.CommentDto;
import com.myapp.blog.blogappapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto createdComment= this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);

    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@RequestBody Integer commentId){
         this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully !!",true), HttpStatus.OK);

    }

}
