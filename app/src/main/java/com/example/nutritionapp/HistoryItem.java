package com.example.nutritionapp;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryItem {
    private String Name;
    private  int calburned;
    private List<FoodItem> Foodlist = new ArrayList<>();
    private List<ExerciseItem> Exerlist = new ArrayList<>();

    public double estimateWeight(double lastweight,int bmr){
        double weight = lastweight;
        int loss = calculateburn(bmr);
        double gain = gettotalCal();
        double difference =  gain-loss;
        //calories to kg
        difference=difference/7700;
        weight+=difference;
        return weight;
    }



    public void addExercise(List<ExerciseItem> item){
        Exerlist.addAll(item);
    }
    public List<ExerciseItem> getexerciselist(){return Exerlist;}

    public int calculateburn(double bmr){
        double total = 0;
        for (ExerciseItem item :Exerlist){
            total+=item.getCalories();
        }

        calburned = (int) Math.round(total);
        return (int) (calburned+bmr);

    }

    public int calculatescore(int reccalories,int recprotein,int recsugar,int recfat,int reccarbohydrates,int recfibre,int recsaturates){
        double calories =0;
        double protein =0;
        double sugar =0;
        double fat =0;
        double carbohydrates =0;
        double fibre =0;
        double saturates =0;
        FoodItem a = new FoodItem();
        for (FoodItem item:Foodlist){
            calories += item.getCalories();
            protein += item.getProtein();
            sugar += item.getSugars();
            fat += item.getFat();
            carbohydrates += item.getCarbohydrate();
            fibre += item.getfibre();
            saturates += item.getSaturates();
        }
        int totalpercent=0;
        totalpercent+= percentage(reccalories,calories);
        totalpercent+= percentage(recprotein,protein);
        totalpercent+= percentage(recsugar,sugar);
        totalpercent+= percentage(recfat,fat);
        totalpercent+= percentage(reccarbohydrates,carbohydrates);
        totalpercent+= percentage(recfibre,fibre);
        totalpercent+= percentage(recsaturates,saturates);

        return totalpercent/7;
    }
    private int percentage(int rec, double actual){
        int percentage = (int)((actual * 100.0f) / rec);
        if (percentage>100){percentage=100-(percentage-100);}
        return percentage;
    }


    public double gettotalCal(){
        double cals=0;
        for (FoodItem item:Foodlist){
            cals+=item.getCalories();
        }
        return  cals;
    }



    public void addlist(List<FoodItem> list){
        Foodlist.addAll(list);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public List<FoodItem> getFoodlist() {
        return Foodlist;
    }

    public void setFoodlist(List<FoodItem> foodlist) {
        Log.d("TAG", "SIZE: "+ foodlist.size());
        Foodlist = foodlist;
    }


    @Override
    public String toString() {
        return Name+":  "+gettotalCal()+" cals";
    }
}

