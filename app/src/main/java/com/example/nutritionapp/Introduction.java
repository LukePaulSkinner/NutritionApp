package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;

public class Introduction extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);


        SharedPreferences sharedPreferences = getSharedPreferences("used", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("used",true).apply();

        viewPager = findViewById(R.id.viewPager);
        IntroAdapter introAdapter = new IntroAdapter(getSupportFragmentManager());
        viewPager.setAdapter(introAdapter);


    }

}