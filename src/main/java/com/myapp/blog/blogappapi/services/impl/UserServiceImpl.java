package com.myapp.blog.blogappapi.services.impl;

import com.myapp.blog.blogappapi.config.AppConstants;
import com.myapp.blog.blogappapi.entities.Role;
import com.myapp.blog.blogappapi.entities.User;
import com.myapp.blog.blogappapi.exceptions.ResourceNotFoundException;
import com.myapp.blog.blogappapi.payloads.UserDto;
import com.myapp.blog.blogappapi.repositories.RoleRepo;
import com.myapp.blog.blogappapi.repositories.UserRepo;
import com.myapp.blog.blogappapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user=this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1= this.userToDto(updatedUser);

        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userRepo.findAll();
        List<UserDto> userDtos= users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
         this.userRepo.delete(user);

    }

    private User dtoToUser(UserDto userDto){

        //modelMapper implmentation
        User user = this.modelMapper.map(userDto, User.class);

        //User user=new User();
        //we are using modelmapper thats why we don't need these lines
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;

    }

    public UserDto userToDto(User user){
        //implementing model mapper
        UserDto userDto= this.modelMapper.map(user,UserDto.class);


//        Manually converting one class into another classs
//        UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        return userDto;
    }
    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

}
