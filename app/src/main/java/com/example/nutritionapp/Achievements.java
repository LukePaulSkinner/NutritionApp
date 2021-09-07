package com.example.nutritionapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Achievements extends AppCompatActivity {

    public boolean firstmilestone;

    public boolean oneday=false;
    public boolean twoday;
    public boolean fiveday;
    public boolean sevenday;
    public boolean tenday;

    public boolean sevenfivepercent;
    public boolean ninezeropercent;
    public boolean three_ninezeropercent;
    public boolean five_ninezeropercent;
    public boolean sevenfivebool;
    public boolean ninezerobool;
    public int sevenfive;
    public int ninezero;

    Button Homebuttonach;
    TextView Achievement1,Achievement2,Achievement3,Achievement4,Achievement5,Achievement6,Achievement7,Achievement8,Achievement9,Achievement10;
    ImageView Img1,Img2,Img3,Img4,Img5,Img6,Img7,Img8,Img9,Img10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        Homebuttonach = findViewById(R.id.Homebuttonach);
        Achievement1 = findViewById(R.id.ach1);
        Achievement2 = findViewById(R.id.ach2);
        Achievement3 = findViewById(R.id.ach3);
        Achievement4 = findViewById(R.id.ach4);
        Achievement5 = findViewById(R.id.ach5);
        Achievement6 = findViewById(R.id.ach6);
        Achievement7 = findViewById(R.id.ach7);
        Achievement8 = findViewById(R.id.ach8);
        Achievement9 = findViewById(R.id.ach9);
        Achievement10 = findViewById(R.id.ach10);

        Img1 = findViewById(R.id.img1);
        Img2 = findViewById(R.id.img2);
        Img3 = findViewById(R.id.img3);
        Img4 = findViewById(R.id.img4);
        Img5 = findViewById(R.id.img5);
        Img6 = findViewById(R.id.img6);
        Img7 = findViewById(R.id.img7);
        Img8 = findViewById(R.id.img8);
        Img9 = findViewById(R.id.img9);
        Img10 = findViewById(R.id.img10);

        firstmilestone = false;
        Homebuttonach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        //GETTING DATA
        getAch();


        //IF ACHIVES EARNED
        if (firstmilestone==false&&MainActivity.goal==MainActivity.currentweight){
            //Achievment_Firstmilestone_Unlocked();
            Achievment_Unlock("I have hit my first milestone","Success","firstmilestone");
        }

        if (oneday==false&&MainActivity.days>=1){
            oneday=true;
            Achievment_Unlock("I have completed my first day","Success","oneday");
        }
        if (twoday==false&&MainActivity.days>=2){
            twoday=true;
            Achievment_Unlock("I have completed two days","Success","oneday");
        }
        if (fiveday==false&&MainActivity.days>=5){
            fiveday=true;
            Achievment_Unlock("I have completed five days","Success","oneday");
        }
        if (sevenday==false&&MainActivity.days>=7){
            sevenday=true;
            Achievment_Unlock("I have completed my first week","Success","oneday");
        }
        if (tenday==false&&MainActivity.days>=10){
            tenday=true;
            Achievment_Unlock("I have completed ten days","Success","oneday");
        }
        if (sevenfivepercent==false&&sevenfive>0){
            sevenfivepercent=true;
            Achievment_Unlock("I have just scored 75 percent","Success","sevenfivepercent");
        }
        if (ninezeropercent==false&&ninezero>0){
            ninezeropercent=true;
            Achievment_Unlock("I have just scored 90 percent","Success","ninezeropercent");
        }
        if (three_ninezeropercent==false&&ninezero>2){
            three_ninezeropercent=true;
            Achievment_Unlock("I have just scored 90 percent for the third time","Success","three_ninezeropercent");
        }
        if (five_ninezeropercent==false&&ninezero>4){
            five_ninezeropercent=true;
            Achievment_Unlock("I have just scored 90 percent for the fifth time","Success","five_ninezeropercent");
        }





        if (firstmilestone==true){
            Achievement1.setText("Successfully reached your first milestone");
            Img1.setImageResource(R.drawable.crown);
        }
        if (oneday==true){
            Achievement2.setText("Used the app for one day");
            Img2.setImageResource(R.drawable.one);
        }
        if (twoday==true){
            Achievement3.setText("Used the app for two days");
            Img3.setImageResource(R.drawable.two);
        }
        if (fiveday==true){
            Achievement4.setText("Used the app for five days");
            Img4.setImageResource(R.drawable.five);
        }
        if (sevenday==true){
            Achievement5.setText("Used the app for one week");
            Img5.setImageResource(R.drawable.seven);
        }
        if (tenday==true){
            Achievement6.setText("Used the app for ten days");
            Img6.setImageResource(R.drawable.ten);
        }
        if (sevenfivepercent==true){
            Achievement7.setText("Reached a score of 75 on a day");
            Img7.setImageResource(R.drawable.sevenfive);
        }
        if (ninezeropercent==true){
            Achievement8.setText("Reached a score of 90 on a day");
            Img8.setImageResource(R.drawable.ninezero);
        }
        if (three_ninezeropercent==true){
            Achievement9.setText("Reached a score of 90 on 3 days");
            Img9.setImageResource(R.drawable.ninezerotwo);
        }
        if (five_ninezeropercent==true){
            Achievement10.setText("Reached a score of 90 on 5 days");
            Img10.setImageResource(R.drawable.ninezerothree);
        }


    }





    public void Achievment_Unlock(final String body, final String sub,String name){
        SharedPreferences sharedPreferences = getSharedPreferences("Achievements", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(name, true).apply();

        //Popup
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        String shareBody =body;
                        String shareSub = sub;
                        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(myIntent,"share using"));

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You have unlocked a new achievement, would you like to share?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }










    private void getAch(){
        SharedPreferences sharedPreferences = getSharedPreferences("Achievements", MODE_PRIVATE);
        firstmilestone = sharedPreferences.getBoolean("firstmilestone", false);
        //Days used
        oneday = sharedPreferences.getBoolean("oneday", false);
        twoday = sharedPreferences.getBoolean("twoday", false);
        fiveday = sharedPreferences.getBoolean("fiveday", false);
        sevenday = sharedPreferences.getBoolean("sevenday", false);
        tenday = sharedPreferences.getBoolean("tenday", false);


        sevenfivepercent = sharedPreferences.getBoolean("sevenfivepercent", false);
        ninezeropercent = sharedPreferences.getBoolean("ninezeropercent", false);
        three_ninezeropercent = sharedPreferences.getBoolean("three_ninezeropercent", false);
        five_ninezeropercent = sharedPreferences.getBoolean("five_ninezeropercent", false);

        sevenfive = sharedPreferences.getInt("75", 0);
        ninezero = sharedPreferences.getInt("90", 0);


    }





}