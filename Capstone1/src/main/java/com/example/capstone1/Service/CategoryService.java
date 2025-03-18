package com.example.capstone1.Service;


import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {


    ArrayList<Category> categories = new ArrayList<>();


    public ArrayList<Category> getCategories(){
        return categories;
    }


    public boolean addCategories(Category category){
        for(Category c: categories){
            if(category.getId().equals(c.getId())){
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public boolean updateCategories(String id, Category category){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId().equals(id)){
                categories.set(i,category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategories(String id){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }




}
