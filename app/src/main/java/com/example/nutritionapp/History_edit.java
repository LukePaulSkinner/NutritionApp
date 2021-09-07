package com.example.nutritionapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class History_edit extends AppCompatActivity {


    ListView listview;
    SearchView searchView;
    Button savebutton,togglebutton;
    TextView textView,textmode;
    private boolean food = true;
    EditText low2,med2,high2;
    final static HistoryItem selected = History.selected;
    public static ArrayList<FoodItem> modif = (ArrayList<FoodItem>) selected.getFoodlist();
    public static ArrayList<ExerciseItem> modife = (ArrayList<ExerciseItem>) selected.getexerciselist();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_edit);




        textView = findViewById(R.id.textViewHistory);
        listview = findViewById(R.id.ListviewHistory);
        togglebutton = findViewById(R.id.toggleedit);
        searchView = findViewById(R.id.searchViewapi);
        textmode = findViewById(R.id.modeedit);
        savebutton = findViewById(R.id.savebuttonHistory);







        final ArrayList<FoodItem> SelectedList = (ArrayList<FoodItem>) selected.getFoodlist();
        final ArrayList<ExerciseItem> SelectedExerciseList = (ArrayList<ExerciseItem>) selected.getexerciselist();

        final CustomFoodList myfoodlist = new CustomFoodList(this,SelectedList);
        final CustomExersiceList myexerciselist = new CustomExersiceList(this,SelectedExerciseList);





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    if (food==true) {
                        Requests.sendfoodquery(query, listview, History_edit.this, myfoodlist,SelectedList);
                    }else {Requests.sendexercisequery(query, listview, History_edit.this, myexerciselist,SelectedExerciseList);}
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        togglebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food==true) {
                    food = false;
                    textmode.setText("Exercise");
                    listview.setAdapter(myexerciselist);
                }else {
                    food=true;
                    textmode.setText("Food");
                    listview.setAdapter(myfoodlist);
                }
            }
        });


        if (food) {
            listview.setAdapter(myfoodlist);
        }else {listview.setAdapter(myexerciselist);}


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHistory();
                finish();
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(food==true){
                    Toast.makeText(History_edit.this,"Removed"+modif.get(position).toString(),Toast.LENGTH_SHORT).show();
                    modif.remove(modif.get(position));
                    listview.setAdapter(myfoodlist);
                }else {
                    Toast.makeText(History_edit.this,"Removed"+modife.get(position).toString(),Toast.LENGTH_SHORT).show();
                    modife.remove(modife.get(position));
                    listview.setAdapter(myexerciselist);
                }



            }
        });









    }

    private void saveHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.FoodHistory);
        editor.putString("hist", json);
        editor.apply();
    }




}