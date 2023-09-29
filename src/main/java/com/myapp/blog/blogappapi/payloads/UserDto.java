package com.myapp.blog.blogappapi.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "User name must be min of 4 characters")
    private String name;

    @Email(message = "your E-mail address is not valid !! ")
    private String email;

    @NotNull
    @Size(min = 3, max = 10, message = "Password must be min of 3 characters and max of 10 characters")
    private String password;

    @NotEmpty
    private String about;
}
