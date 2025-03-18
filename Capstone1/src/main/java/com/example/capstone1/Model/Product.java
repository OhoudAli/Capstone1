package com.example.capstone1.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "ID should not be empty")
    private String id;

    @NotEmpty(message = "name should not be emtpy")
    @Size(min = 3,message = "name length should be more than 3")
    private String name;

    @Positive(message = "price must be positive")
    @NotNull(message = "price should not be empty")
    private int price;

    @NotEmpty(message = "should not be empty")
    private String categoryID;

    private int rating;


}
