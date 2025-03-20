package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean addMerchant(Merchant merchant){
        for(Merchant m: merchants){
            if(merchant.getId().equals(m.getId())){
                return false;
            }
        }
        return true;
    }

    public boolean updateMerchant(String id, Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getId().equals(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id){
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getId().equals(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }


//    public boolean ratingMerchant(String merchantID , double rate){
//        Merchant merchant = null;
//        double totalRate =0;
//        for(Merchant m : merchants){
//            if(m.getId().equals(merchantID)){
//                merchant = m;
//                totalRate = merchant.getRating().
//                break;
//            }
//        }
//           if(merchant == null){
//               return false;
//           }
//
//           if(rate < 1 || rate >5 ){
//               return false;
//           }
//
//
//
//    }


//    public Merchant getBestMerchant() {
//        Merchant bestMerchant = null;
//        double maxRating = -1;
//        for (Merchant merchant : merchants) {
//            if (merchant.getRating() > maxRating) {
//                maxRating = merchant.getRating();
//                bestMerchant = merchant;
//            }
//        }
//        return bestMerchant;
//    }
}
