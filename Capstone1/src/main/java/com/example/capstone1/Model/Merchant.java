package com.example.capstone1.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "id must not be empty")
    private String id;

    @NotEmpty(message = "name must not be empty")
    @Size(min = 3, message = "name must be more than 3")
    private String name;

//    @NotNull(message = "rate should not be empty")
    private double rating;
     private String topMerchant;
    private double merchantRate;
}
