package com.example.nutritionapp;
//Class To hold information about food
public class FoodItem {
    private String Name;
    private double calories;
    private double protein;
    private double fat;
    private double carbohydrate;
    private double sugars;
    private double fibre;
    private double saturates;
    private double score;
    private String imgurl;
    private String serving;
    private String highresimageurl;

    public String getHighresimageurl() {
        return highresimageurl;
    }

    public void setHighresimageurl(String highresimageurl) {
        this.highresimageurl = highresimageurl;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving,int quantity) {
        this.serving = serving+": "+quantity;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getSugars() {
        return sugars;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public double getfibre() {
        return fibre;
    }

    public void setfibre(double fibre) {
        this.fibre = fibre;
    }

    public double getSaturates() {
        return saturates;
    }

    public void setSaturates(double saturates) {
        this.saturates = saturates;
    }


    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    //private int Calories; Add once more func


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
//Press alt and insert to add get and set

    @Override
    public String toString() {
        return Name;
    }
}
