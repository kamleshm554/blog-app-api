package com.myapp.blog.blogappapi.services.impl;

import com.myapp.blog.blogappapi.entities.Category;
import com.myapp.blog.blogappapi.entities.Post;
import com.myapp.blog.blogappapi.entities.User;
import com.myapp.blog.blogappapi.exceptions.ResourceNotFoundException;
import com.myapp.blog.blogappapi.payloads.CategoryDto;
import com.myapp.blog.blogappapi.payloads.PostDto;
import com.myapp.blog.blogappapi.payloads.PostResponse;
import com.myapp.blog.blogappapi.repositories.CategoryRepo;
import com.myapp.blog.blogappapi.repositories.PostRepo;
import com.myapp.blog.blogappapi.repositories.UserRepo;
import com.myapp.blog.blogappapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","UserId",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        Post post= this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost= this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost= this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPosts= pagePost.getContent();


        List<PostDto> postDtos= allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse =new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }
    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
//        Pageable p= PageRequest.of(pageNumber, pageSize);
//        Page<Post> pagePost = this.postRepo.findByUser(p);
//        List<Post> posts = pagePost.getContent();

        User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id", userId));
        List<Post> posts= this.postRepo.findByUser(user);
        List<PostDto> postDtos= posts.stream().map((post) -> this.modelMapper.map(posts,PostDto.class)).collect(Collectors.toList());
//        PostResponse postResponse = new PostResponse();
//        postResponse.setContent(postDtos);
//        postResponse.setPageNumber(pagePost.getNumber());
//        postResponse.setPageSize(pagePost.getSize());
//        postResponse.setTotalElements(pagePost.getTotalElements());
//        postResponse.setTotalPages(pagePost.getTotalPages());
//        postResponse.setLastPage(pagePost.isLast());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post pos= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        return this.modelMapper.map(pos, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category Id",categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDtos= posts.stream().map((post) ->this.modelMapper.map(post,PostDto.class) ).collect(Collectors.toList());
        return postDtos;
    }



    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts= this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos= posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
