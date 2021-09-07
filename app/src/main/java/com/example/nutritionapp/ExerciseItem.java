package com.example.nutritionapp;

public class ExerciseItem {
    private double duration;
    private String type;
    private int calories;

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String toString() {
        return type;
    }
}
