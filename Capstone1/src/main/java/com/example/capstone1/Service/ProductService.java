package com.example.capstone1.Service;


import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {


    private final MerchantService merchantService;

    ArrayList<Product> products = new ArrayList<>();

    public ProductService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product){
        for (Product p: products){
            if(product.getId().equals(p.getId())){
                return false;
            }
        }
        products.add(product);
        return true;
    }

    public boolean updateProduct(String id , Product product){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }


//    public Product getBestProductByMerchant(String merchantID) {
//        Product bestProduct = null;
//        double maxRating = -1;
//        for (Product product : products) {
//            if (product..equals(merchantID) && product.getRating() > maxRating) {
//                maxRating = product.getRating();
//                bestProduct = product;
//            }
//        }
//        return bestProduct;
//    }
}
