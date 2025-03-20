package com.example.capstone1.Model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {


    @NotEmpty(message = "id must not be empty")
    private String id;

    @NotEmpty(message = "name must not be empty")
    private String productID;

    @NotEmpty(message = "merchant ID must not be empty")
    private String merchantID;

    @NotNull(message = "stock must not be empty")
    @Min(value = 10,message = "Stock most be more than 10 to start ")
    private int stock;


}
