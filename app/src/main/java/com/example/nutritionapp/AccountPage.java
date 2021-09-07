package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.example.nutritionapp.MainActivity.goal;

public class AccountPage extends AppCompatActivity {
    Button button;
    EditText input,input2,calgoal,age,height;
    TextView textview;
    String userinput;
    Spinner inputg;
    public  static String[] read = {"",""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_page);
        int bmr;


        button = findViewById(R.id.button);
        input = findViewById(R.id.input);
        textview = findViewById(R.id.textView2);
        inputg = findViewById(R.id.genderinput);
        input2 = findViewById(R.id.input2);
        calgoal = findViewById(R.id.input3);
        age = findViewById(R.id.input4);
        height = findViewById(R.id.input5);

        String[] gender = {"Male", "Female","Other"};
        //Spinner code


        inputg.setAdapter(new ArrayAdapter<>(AccountPage.this, android.R.layout.simple_spinner_dropdown_item,gender));

        try {
            getData();
            if (MainActivity.gender.equals("Male")){
                inputg.setSelection(0);
            }
            else if (MainActivity.gender.equals("Female")){inputg.setSelection(1);}
            else {inputg.setSelection(2);}
            input.setText(Integer.toString(MainActivity.getGoal()));

            input2.setText(Double.toString(MainActivity.getcurrent()));


            input.setText(String.valueOf(MainActivity.goal));
            input2.setText(String.valueOf(MainActivity.currentweight));
            calgoal.setText(String.valueOf(MainActivity.calgoal));
            age.setText(String.valueOf(MainActivity.age));
            height.setText(String.valueOf(MainActivity.height));

        } catch (IOException e) {
            e.printStackTrace();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userinput = String.valueOf(input.getText());
                try {

                    saveUserdata();
                    getData();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });
    }




    //private  void saveGoal(String goal) throws IOException {
    //    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("userdata.txt", Context.MODE_PRIVATE));
    //    outputStreamWriter.write(goal);
    //    outputStreamWriter.close();
    //}

    private void saveUserdata() throws IOException {
        //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("userdata.txt", Context.MODE_PRIVATE));
        //outputStreamWriter.write(userinput = input.getText()+"\n");
        //outputStreamWriter.write(userinput = inputg.getSelectedItem().toString()+"\n");
        //outputStreamWriter.close();

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        sharedPreferences.edit().putInt("bmr",CalcBMR()).apply();
        sharedPreferences.edit().putString("gender", inputg.getSelectedItem().toString()).apply();
        if (input.getText()==null){
            sharedPreferences.edit().putInt("goal",70 ).apply();
        }else {sharedPreferences.edit().putInt("goal", Integer.parseInt(input.getText().toString())).apply();}
        if (input2.getText()==null){
            putDouble(sharedPreferences.edit(),"current",0.0).apply();
        }else {putDouble(sharedPreferences.edit(),"current",Double.parseDouble(input2.getText().toString())).apply();}
        if (calgoal.getText()==null){
            sharedPreferences.edit().putInt("calgoal",0 ).apply();
        }else {sharedPreferences.edit().putInt("calgoal",Integer.parseInt(calgoal.getText().toString())).apply();}

        if (age.getText()==null){
            sharedPreferences.edit().putInt("age",0 ).apply();
        }else {sharedPreferences.edit().putInt("age",Integer.parseInt(age.getText().toString())).apply();}

        if (height.getText()==null){
            sharedPreferences.edit().putInt("height",0 ).apply();
        }else {sharedPreferences.edit().putInt("height",Integer.parseInt(height.getText().toString())).apply();}

        if (height.getText()!=null&&age.getText()!=null&&input2.getText()!=null){
            sharedPreferences.edit().putInt("bmr",CalcBMR() ).apply();
        }
    }

    public void getData()throws  IOException {

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        MainActivity.setGoal(sharedPreferences.getInt("goal",70));
        MainActivity.setCurrentweight(getDouble(sharedPreferences,"current",0.0));
        MainActivity.setGender(sharedPreferences.getString("gender","Male"));
        MainActivity.setAge(sharedPreferences.getInt("age",0));
        MainActivity.setHeight(sharedPreferences.getInt("height",0));
        MainActivity.setBMR(CalcBMR());

        //String result = "";
        //int i =0;
        //InputStream inputStream = openFileInput("userdata.txt");
        //if(inputStream != null)
        //{
        //    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        //    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //    String temp = "";
//
//
        //    while((temp = bufferedReader.readLine()) != null)
        //    {
        //        read[i]=temp;
        //        i++;
        //    }
//
        //    inputStream.close();
        //}
        //if (read[0]==null){
        //    read[0]="0";
        //}
        //try{
        //    goal = Integer.parseInt(read[0]);
        //} catch(NumberFormatException er) {
        //    goal = 0;
        //}
        //MainActivity.gender = read[1];
    }


    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }


    public int CalcBMR(){
        double BMR = 0;
        if (MainActivity.gender.equals("Male")){
            BMR = 66.47+(6.24*MainActivity.KGtoPoounds(MainActivity.currentweight))+(12.7*MainActivity.height)-(6.755*MainActivity.age);
        }else if (MainActivity.gender.equals("Female")){
            BMR = 655.1+(4.35*MainActivity.KGtoPoounds(MainActivity.currentweight))+(4.7*MainActivity.height)-(4.7*MainActivity.age);
        }
        else {}
        BMR = (int) Math.round(BMR);
        return (int) BMR;
    }

}