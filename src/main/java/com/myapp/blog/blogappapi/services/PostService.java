package com.myapp.blog.blogappapi.services;

import com.myapp.blog.blogappapi.entities.Post;
import com.myapp.blog.blogappapi.payloads.PostDto;
import com.myapp.blog.blogappapi.payloads.PostResponse;

import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);

}
