package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantSockController {

     private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ArrayList<MerchantStock> getMerchantStock(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        return merchantStocks;
    }


    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isValid = merchantStockService.addMerchantStock(merchantStock);
        if(isValid){
            return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID is taken"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id){
        boolean isDelete = merchantStockService.deleteMerchantStock(id);
        if(isDelete){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ID not found"));
    }


    @PostMapping("/addNewStock/{productID}/{merchantID}/{amount}")
    public ResponseEntity addNewStock(@PathVariable String productID , @PathVariable String merchantID, @PathVariable int amount){
        boolean adding = merchantStockService.addNewStock(productID, merchantID, amount);
        if(adding){
        return ResponseEntity.status(200).body(new ApiResponse("add new stock successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant or product not found"));

    }

// need improve
    @PostMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        String buyValid = merchantStockService.buyProduct(userId, productId, merchantId);
        if (buyValid.equals("buy product successfully .")) {
            return ResponseEntity.status(200).body(new ApiResponse(buyValid));
        }
        return ResponseEntity.status(400).body(new ApiResponse(buyValid));
    }
// good
    @GetMapping("/discount/{userId}/{merchantId}")
    public ResponseEntity getDiscount(@PathVariable String userId, @PathVariable String merchantId) {
        double discount = merchantStockService.getUserDiscount(userId, merchantId);
        return ResponseEntity.status(200).body(new ApiResponse("Discount for user: " + (discount * 100) + "%"));
    }

    // rating based on the product the merchant sale
    @PostMapping("/rateMerchant/{merchantID}/{rating}")
    public ResponseEntity rateMerchant(@PathVariable String merchantID, @PathVariable int rating) {
        boolean ratedSuccessfully = merchantStockService.merchantRate(merchantID, rating);
        if (ratedSuccessfully) {
            return ResponseEntity.status(200).body(new ApiResponse("you rated the merchant successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Failed to rate merchant"));
    }
// user searching for specific product that merchant has
    @PostMapping("/return/{userId}/{productId}/{merchantId}")
    public ResponseEntity returnProduct(@PathVariable String userId, @PathVariable String productId,@PathVariable String merchantId) {
        boolean returned = merchantStockService.returnProduct(userId, productId, merchantId);

        if(returned){
            return   ResponseEntity.ok(new ApiResponse("Product returned successfully"));
        }
         return ResponseEntity.badRequest().body(new ApiResponse("Return failed: You did not purchase this product"));
    }




    @PostMapping("/applyDiscount/{merchantID}")
    public ResponseEntity applyDiscount(@PathVariable String merchantID) {
        merchantStockService.applyDiscountToMerchantProducts(merchantID);
        return ResponseEntity.ok(new ApiResponse("Discount applied successfully"));
    }

    // calc average rate that the merchant has
    @GetMapping("/averageRating/{merchantID}")
    public ResponseEntity getAverageRating(@PathVariable String merchantID) {
        double avgRating = merchantStockService.calculateAverageRating(merchantID);
        return ResponseEntity.ok(avgRating);
    }

    // check if the stock are in the minimum
    @GetMapping("/checkStock/{productID}/{merchantID}")
    public ResponseEntity checkStockLevel(@PathVariable String productID, @PathVariable String merchantID) {
        String message = merchantStockService.checkStockLevel(productID, merchantID);
        return ResponseEntity.ok(new ApiResponse(message));
    }

}
