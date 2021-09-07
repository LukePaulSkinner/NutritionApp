package com.example.nutritionapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AddFood extends AppCompatActivity {

    Button addfood;
    EditText name;
    SearchView searchView;
    ListView listView;


    public  static ArrayList<FoodItem> mealitems = new ArrayList<FoodItem>();
    private static final String PREFS_TAG = "SharedPrefs";
    private static final String PRODUCT_TAG = "MyProduct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        name = findViewById(R.id.namebox);
        searchView = findViewById(R.id.search2);
        listView = findViewById(R.id.Listview2);
        addfood = findViewById(R.id.Newfood);



        final CustomFoodList mymeallist = new CustomFoodList(this, mealitems);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {

                    Requests.sendfoodquery(query, listView, AddFood.this, mymeallist,mealitems);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddFood.this,"Removed"+mealitems.get(position).toString(),Toast.LENGTH_SHORT).show();
                mealitems.remove(mealitems.get(position));

                listView.setAdapter(mymeallist);


            }
        });




        addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean canuse = true;
                for (CustomMeal custom:MainActivity.custommeals){
                    if (String.valueOf(name.getText()).equals(custom.getName())){
                        Toast.makeText(AddFood.this,"Error that name is already in use.",Toast.LENGTH_SHORT).show();
                        canuse=false;
                    }
                }
                if (canuse){
                    CustomMeal a = new CustomMeal();
                    a.setName(String.valueOf(name.getText()));
                    a.setMeallsit(mealitems);
                    MainActivity.custommeals.add(a);

                    saveData();

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

                }

            }
        });



    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.custommeals);
        editor.putString("custommeals", json);
        editor.apply();
    }





}