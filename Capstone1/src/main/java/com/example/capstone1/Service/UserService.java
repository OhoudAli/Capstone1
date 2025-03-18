package com.example.capstone1.Service;


import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private final MerchantService merchantService;
    ArrayList<User> users = new ArrayList<>();

    public UserService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean addUsers(User user){
       for(User u: users){
           if(user.getId().equals(u.getId())){
               return false;
           }
       }
       users.add(user);
       return true;
    }

    public boolean updateUser(String id, User user){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

//         public boolean bestMerchant(String merchantID ,String userID ){
//           Merchant mer ;
//           for (Merchant m : merchantService.getMerchants()){
//               if(m.getId().){
//
//               }
//           }
//         }
}
