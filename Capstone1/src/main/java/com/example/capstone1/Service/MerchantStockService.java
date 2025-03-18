package com.example.capstone1.Service;


import com.example.capstone1.Model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {


    private final MerchantService merchantService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    ArrayList<String> userPurchases = new ArrayList<>();
    ArrayList<String> merchantRatings = new ArrayList<>();



    public MerchantStockService(MerchantService merchantService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.merchantService = merchantService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public boolean addMerchantStock(MerchantStock merchantStock){
        for(MerchantStock m : merchantStocks){
            if(merchantStock.getId().equals(m.getId())){
                return false;
            }
        }
        merchantStocks.add(merchantStock);
        return true;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getMerchantID().equals(id)){
                merchantStocks.set(i,merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getMerchantID().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }



    public boolean addNewStock(String productId,String merchantId,  int amount){
        boolean checkMerchant = false;
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantId)) {
                checkMerchant = true;
                break;
            }
        }

        if (!checkMerchant) {
            System.out.println("Merchant not found: " + merchantId);

            return false;
        }
        boolean checkProduct = false;
        for(Product p: productService.getProducts()){
            if(p.getId().equals(productId)){
                checkProduct =true;
                break;
            }
        }
        if(!checkProduct){
            System.out.println("Product not found: " + productId);

            return false;
        }

        for (MerchantStock m : merchantStocks) {
            if (m.getProductID().equals(productId) && m.getMerchantID().equals(merchantId)) {
                m.setStock(m.getStock() + amount);
                System.out.println("Updated stock: " + m.getStock());
                return true;
            }
        }
//        System.out.println("Creating new stock for product: " + productId + " and merchant: " + merchantId);
//        MerchantStock newStock = new MerchantStock(productId, merchantId,);
//        merchantStocks.add(newStock);

        return true;

    }


    public String buyProduct(String userID , String productID , String merchantID ){
        User userValid = null;
        for(User u: userService.getUsers()){
            if(u.getId().equals(userID)){
                userValid = u;
                break;
            }
        }
        MerchantStock mValid = null ;
        for (MerchantStock s : merchantStocks) {
            if (s.getProductID().equals(productID) && s.getMerchantID().equals(merchantID)) {
                mValid = s;
                break;
            }
        }

      Product pValid = null;
      for(Product p: productService.getProducts()){
          if(p.getId().equals(productID)){
              pValid = p;
              break;
          }
      }

        Category cValid = null;
      for (Category c: categoryService.getCategories()){
          if (pValid != null && c.getId().equals(pValid.getCategoryID())) {
              cValid = c;
              break;
          }
      }

      if(userValid == null){
          return "user is not found";
      }

      if(pValid== null){
          return "product not found ";
      }
      if(mValid == null){
          return "merchant not found ";
      }
      if(cValid== null){
          return "category  not found";
      }



         if(mValid.getStock()<=0){
             return "Product out of stock";
         }
         if(userValid.getBalance()< pValid.getPrice()){
             return "you do not have enough money";
         }

         mValid.setStock(mValid.getStock()-1);
         userValid.setBalance(userValid.getBalance()-pValid.getPrice());

         //i add this method to store the user purchases
         userPurchases.add(userID+"-"+merchantID);

         return "buy product successfully";


    }


    public double getUserDiscount(String userID, String merchantID) {
        int countPurchase = 0;
        for (String purchase : userPurchases) {
            if (purchase.equals(userID + "-" + merchantID)) {
                countPurchase++;
            }
        }

        if (countPurchase >= 2) {
            return 0.10;
        } else if (countPurchase == 1) {
            return 0.05;
        }
        return 0.0;
    }


    /// idea :discount for user who buy at least ones from the same merchant
    /// idea 2: user can search for best merchant and best product the merchant have
    ///  idea 3: if the user want to return something to merchant
    /// idea 4: user can rate the merchant --> if the merchant rate is less than 3 should make new price less than the old price
    public boolean returnProduct(String userID, String productID, String merchantID) {
        String purchaseRecord = userID + "-" + merchantID;
        if (userPurchases.contains(purchaseRecord)) {
            userPurchases.remove(purchaseRecord);
            return true;
        }
        return false;
    }

    public void badMerchant(String merchantID, int rating) {

        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating, must be between 1 and 5.");
            return;
        }
        merchantRatings.add(merchantID + "-" + rating);

        double averageRating = calculateAverageRating(merchantID);

        if (averageRating < 3) {
            applyDiscountToMerchantProducts(merchantID);
        }
    }
    public double calculateAverageRating(String merchantID) {
        int totalRating = 0;
        int count = 0;
        for (String ratingRecord : merchantRatings) {
            if (ratingRecord.startsWith(merchantID + "-")) {
                String ratingString = ratingRecord.substring(merchantID.length() + 1);
                int rating = Integer.parseInt(ratingString);
                totalRating += rating;
                count++;
            }
        }
        if (count > 0) {
            return (double) totalRating / count;
        } else {
            return 0.0;
        }
    }

    public void applyDiscountToMerchantProducts(String merchantID) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equals(merchantID)) {
                Product product = findProductById(stock.getProductID());
                if (product != null) {
                    double oldPrice = product.getPrice();
                    double newPrice = oldPrice * 0.9; // 10% discount
                    product.setPrice((int) newPrice);
                    System.out.println("Product " + product.getName() + " price reduced from " + oldPrice + " to " + newPrice);
                }
            }
        }
    }
    private Product findProductById(String productID) {
        for (Product product : productService.getProducts()) {
            if (product.getId().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    public boolean merchantRate(String merchantID, int rating) {
        if(rating <1 || rating >5){
            return false;
        }

        merchantRatings.add(merchantID + "-" + rating);

        badMerchant(merchantID, rating);
        return true;
    }
}
