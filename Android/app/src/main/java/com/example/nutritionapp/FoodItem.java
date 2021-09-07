package com.example.nutritionapp;
//Class To hold information about food
public class FoodItem {
    private String Name;
    private double Calories,protein,fat;

    public String getName() {
        return Name;
    }

    public double getCalories() {
        return Calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public void setCalories(double calories) {
        Calories = calories;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setName(String name) {
        Name = name;
    }
//Press alt and insert to add get and set as well as others

    @Override
    public String toString() {
        return Name;
    }
}
