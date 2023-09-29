package com.myapp.blog.blogappapi.controllers;

import com.myapp.blog.blogappapi.payloads.ApiResponse;
import com.myapp.blog.blogappapi.payloads.UserDto;
import com.myapp.blog.blogappapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto  userDto){
        UserDto createdUserDto = this.userService.createUser(userDto);
        return  new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
        UserDto updateUser= this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateUser);


    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUsers(@PathVariable Integer userId){

        return ResponseEntity.ok(this.userService.getUserById(userId));
    }






}
