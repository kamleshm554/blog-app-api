package com.myapp.blog.blogappapi.services;

import com.myapp.blog.blogappapi.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto registerNewUser(UserDto user);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);

}
