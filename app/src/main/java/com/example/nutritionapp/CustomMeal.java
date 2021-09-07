package com.example.nutritionapp;

import java.util.List;

public class CustomMeal {
    private List<FoodItem> meallsit;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodItem> getMeallsit() {
        return meallsit;
    }

    public void setMeallsit(List<FoodItem> meallsit) {
        this.meallsit = meallsit;
    }
    public void addMeallist(FoodItem item){
        meallsit.add(item);
    }
}
