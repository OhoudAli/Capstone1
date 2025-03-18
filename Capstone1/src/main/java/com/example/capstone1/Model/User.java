package com.example.capstone1.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "id must not be empty")
    private String id;

    @NotEmpty(message = "user name must not be empty")
    @Size(min = 5,message = "must be more than 5")
    private String userName;

    @NotEmpty(message = "password must not be empty")
    @Size(message = "password must be more than 6")
    private String password;

    @Email
    private String email;


    @NotEmpty(message = "role must not be empty")
    @Pattern(regexp = "Admin|Customer")
    private String role;

    @NotNull(message = "balance must not be empty")
    @Positive(message = "balance must be positive")
    private double balance;
}
