package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    public static HistoryItem selected;

    private int calories=0;
    private int protein=0;
    private int sugar=0;
    private int fat=0;
    private int carbohydrates=0;
    private int fibre=0;
    private int saturates=0;


    Button home,edit;
    ListView list;
    HistoryItem activehist;
    Boolean IsEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        loadData();
        load();
        home = findViewById(R.id.homebutt);
        list = findViewById(R.id.historylist);
        edit = findViewById(R.id.editbutt);


        list.setAdapter(new ArrayAdapter<>(History.this, android.R.layout.simple_list_item_1,MainActivity.FoodHistory));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsEdit==true){
                    edit.setText("Edit");
                    IsEdit=false;

                    Toast.makeText(getApplicationContext(),"No longer editing",Toast.LENGTH_SHORT).show();
                }
                else {
                    edit.setText("Stop Editing");
                    IsEdit=true;
                    Toast.makeText(getApplicationContext(),"Tap a date to edit it",Toast.LENGTH_SHORT).show();
                }


            }
        });



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //activehist = MainActivity.FoodHistory.get(position);
                //Toast.makeText(History.this,"Score: "+MainActivity.FoodHistory.get(position).getName(),Toast.LENGTH_SHORT).show();
                //list.setAdapter(new ArrayAdapter<>(History.this, android.R.layout.simple_list_item_1,MainActivity.FoodHistory));

                if (IsEdit==true){
                    selected = MainActivity.FoodHistory.get(position);
                    Intent i = new Intent(getApplicationContext(),History_edit.class);
                    startActivity(i);
                }
                else {
                    PopUpClass popUpClass = new PopUpClass();


                    String test = MainActivity.FoodHistory.get(position).getName();
                    HistoryItem hist = MainActivity.FoodHistory.get(position);

                    int score = hist.calculatescore(calories,protein,sugar,fat,carbohydrates,fibre,saturates);


                    String text = "";
                    for (FoodItem item : hist.getFoodlist()) {
                        text += item.getName() + ": " + item.getCalories() + " Cals" + "\n";
                    }
                    int a = hist.calculateburn(MainActivity.bmr);
                    int b = (int) hist.gettotalCal();
                    Log.i("TAG", String.valueOf(MainActivity.bmr));

                    Log.d("TAG", "TEST!"+ calories);
                    text += "Calories burned:" + a + "\n";
                    text += "Calorie difference:" + (b - a) + "\n";
                    text+="Score: "+score;
                    popUpClass.showPopupWindow(view, text);

                }
            }
        });


        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);
        //FOR DEMONSTRATION DELETE THIS
        ////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////
        DataPoint[] values = new DataPoint[MainActivity.FoodHistory.size()];
        Log.i("TAG", String.valueOf(MainActivity.FoodHistory.size()));
        int count = 0;
        double a =-1;
        for (HistoryItem hit : MainActivity.FoodHistory){
            //DataPoint v = new DataPoint(count, hit.gettotalCal());
            if (a==-1){
                DataPoint v = new DataPoint(count,hit.estimateWeight(MainActivity.getcurrent(),MainActivity.bmr));
                a=hit.estimateWeight(MainActivity.getcurrent(),MainActivity.bmr);
                values[count]=v;
            }else {
                DataPoint v = new DataPoint(count,hit.estimateWeight(a,MainActivity.bmr));
                values[count]=v;
            }

            count+=1;
        }
        try {
            LineGraphSeries <DataPoint> series = new LineGraphSeries< >(values);
            graph.addSeries(series);
        } catch (IllegalArgumentException e) {
            Toast.makeText(History.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }



    /////////////////////Score saving for achievements
        int sevenfive = 0;
        int ninezero = 0;
        for (HistoryItem hist:MainActivity.FoodHistory){
            if (hist.calculatescore(calories,protein,sugar,fat,carbohydrates,fibre,saturates)>75){sevenfive+=1;}
            if (hist.calculatescore(calories,protein,sugar,fat,carbohydrates,fibre,saturates)>90){ninezero+=1;}
        }
        savescore(sevenfive,ninezero);
    }
    //////////////////////


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("hist", null);
        Type type = new TypeToken<ArrayList<HistoryItem>>() {}.getType();
        MainActivity.FoodHistory = gson.fromJson(json, type);
        if (MainActivity.FoodHistory == null) {
            MainActivity.FoodHistory = new ArrayList<>();
        }

    }
    private void load(){
        SharedPreferences sharedPreferences = getSharedPreferences("nut", MODE_PRIVATE);

        calories = sharedPreferences.getInt("cal",0);
        protein = sharedPreferences.getInt("protein",0);
        sugar = sharedPreferences.getInt("sugar",0);
        fat = sharedPreferences.getInt("fat",0);
        carbohydrates = sharedPreferences.getInt("carbohydrates",0);
        fibre = sharedPreferences.getInt("fibre",0);
        saturates = sharedPreferences.getInt("saturates",0);
        Log.d("TAG", "TEST2!"+ sharedPreferences.getInt("protein",0));

    }
    private void savescore(int sevenfive, int ninezero){
        SharedPreferences sharedPreferences = getSharedPreferences("Achievements", MODE_PRIVATE);
        sharedPreferences.edit().putInt("75",sevenfive ).apply();
        sharedPreferences.edit().putInt("90", ninezero).apply();
    }


}