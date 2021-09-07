package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FoodInfo extends AppCompatActivity {

    TextView desc,name;
    ImageView img;
    Button leave;

    private FoodItem active = MainActivity.activeITEM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        desc = findViewById(R.id.foodinfo);
        name = findViewById(R.id.InfoName);
        leave = findViewById(R.id.returnbutton);
        img = findViewById(R.id.viewfood);

        String nametext = active.getName()+" ("+active.getServing()+")";
        name.setText(nametext);
        String descriptiontext = "";
        descriptiontext+="Calories: "+active.getCalories()+"\n";

        descriptiontext+="Protein: "+active.getProtein()+"\n";
        descriptiontext+="Fat: "+active.getFat()+"\n";
        descriptiontext+="Carbohydrates: "+active.getCarbohydrate()+"\n";
        descriptiontext+="Sugars: "+active.getSugars()+"\n";
        descriptiontext+="Fibre: "+active.getfibre()+"\n";
        descriptiontext+="Saturates: "+active.getCarbohydrate();



        desc.setText(descriptiontext);
        System.out.println(active.getHighresimageurl());
        Picasso.get().load(active.getHighresimageurl()).into(img);


        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}