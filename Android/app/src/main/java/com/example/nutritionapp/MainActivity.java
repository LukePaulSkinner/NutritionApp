package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Initialize Objects
    ListView listview;
    Spinner spinner;
    Button accountbutton;
    TextView textView;
    String sText="";
    int dailyCalories = 2400;
    ArrayList<FoodItem> Selected = new ArrayList<FoodItem>();
    private List<FoodItem> FoodList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reading CSV file
        readCSV();

        //Assigning var ID
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);
        listview = findViewById(R.id.Listview);
        accountbutton = findViewById(R.id.accountbutton);


        //Setting adapter and Spinner content
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,FoodList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            //Code For Selecting from spinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Showing selected
                FoodItem example = (FoodItem) parent.getItemAtPosition(position);
                sText =sText +"\n"+"\n"+ example.toString() + " Calories:"+example.getCalories();
                Selected.add((FoodItem) parent.getItemAtPosition(position));


                //Setting adapter for list
                listview.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,Selected));
                compareToRecomended();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Code For Clicking on list
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Removed"+Selected.get(position).toString(),Toast.LENGTH_SHORT).show();
                Selected.remove(Selected.get(position));
                listview.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,Selected));
                compareToRecomended();

            }
        });



        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AccountPage.class);
                startActivity(i);
            }
        });


    }


    private void readCSV() {
        InputStream is = getResources().openRawResource(R.raw.nutrition);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                FoodItem item = new FoodItem();
                item.setName(tokens[1]);
                item.setCalories(Double.parseDouble(tokens[3]));
                item.setProtein(Double.parseDouble(tokens[5]));
                //View 13 mins for guide on adding more
                FoodList.add(item);
                //Log.d("MyActivity","Just created"+item);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity","Error reading CSV");
            e.printStackTrace();
        }
    }

    private void compareToRecomended(){
        int totalcal = 0;
        for (FoodItem item: Selected){
            totalcal += item.getCalories();
        }
        if (totalcal>dailyCalories){textView.setText("You have eaten: "+totalcal+" calories. This is: "+(totalcal-dailyCalories)+" too many");}
        else if(totalcal<dailyCalories){textView.setText("You have eaten: "+totalcal+" calories. This is: "+(dailyCalories-totalcal)+" too few");}
        else {textView.setText("You have eaten: "+totalcal+" calories. This is perfect.");}
    }
}