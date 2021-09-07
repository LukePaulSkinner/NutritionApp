package com.example.nutritionapp;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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

public class Requests {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void sendfoodquery(String query, ListView list, Context cont, CustomFoodList mylist, ArrayList<FoodItem> select) throws IOException, JSONException {
        String raw = "";
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here



            URL url = new URL("https://trackapi.nutritionix.com/v2/natural/nutrients");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");


            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-app-id", "d276f2d9");
            con.setRequestProperty("x-app-key", "a8ec5707141e19330b40e1bb07081ce7");


            con.setDoOutput(true);


            String jsonInputString = "{\n \"query\":\"" + query + "\",\n \"timezone\": \"US/Eastern\"\n}";
            System.out.println(jsonInputString);


            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                //System.out.println(response.toString());

                parsefood(response.toString(),select,list,cont,mylist);


            }
            con.disconnect();

        }

    }
    public static void parsefood(String responsebody, ArrayList<FoodItem> select, ListView listview, Context con, CustomFoodList mylist) throws JSONException {
        System.out.println(responsebody);
        JSONObject albums = new JSONObject(responsebody);

        JSONObject ar = new JSONObject(responsebody);
        JSONArray newar = ar.getJSONArray("foods");


        JSONObject obj;
        JSONObject photo;
        if (newar.length()==0){
            Toast.makeText(con, "no items found", Toast.LENGTH_LONG).show();}
        for (int i = 0; i < newar.length(); i++) {
            System.out.println(newar.get(i));
            obj = (JSONObject) newar.get(i);
            photo = (JSONObject) ((JSONObject) newar.get(i)).get("photo");
            System.out.println(obj.get("food_name")+" : "+obj.get("nf_calories")+" : "+photo.get("thumb"));
            System.out.println(photo);
            FoodItem a = new FoodItem();
            a.setName((String) obj.get("food_name"));
            a.setCalories(((Number) obj.get("nf_calories")).doubleValue());


            try {
                a.setSugars(((Number) obj.get("nf_sugars")).doubleValue());
            }catch (java.lang.ClassCastException je){
                a.setSugars(0);
            }

            try {
                a.setfibre(((Number) obj.get("nf_dietary_fiber")).doubleValue());
            }catch (java.lang.ClassCastException je){
                a.setfibre(0);
            }
            try {
                a.setCarbohydrate(((Number) obj.get("nf_total_carbohydrate")).doubleValue());
            }catch (java.lang.ClassCastException je){
                a.setCarbohydrate(0);
            }
            try {
                a.setFat(((Number) obj.get("nf_total_fat")).doubleValue());
            }catch (java.lang.ClassCastException je){
                a.setFat(0);
            }
            try {
                a.setSaturates(((Number) obj.get("nf_saturated_fat")).doubleValue());
            }catch (java.lang.ClassCastException je){
                a.setSaturates(0);
            }
            try {
                a.setProtein(((Number) obj.get("nf_protein")).doubleValue());
            }catch (java.lang.ClassCastException je){
                a.setProtein(0);
            }



            try {
                a.setImgurl((String) photo.get("thumb"));
            }catch (java.lang.ClassCastException je){
                a.setImgurl("https://via.placeholder.com/300x300?text=Could+not+find+image");
            }
            try {
                a.setHighresimageurl((String) photo.get("highres"));
            }catch (java.lang.ClassCastException je){
                a.setHighresimageurl("https://via.placeholder.com/300x300?text=Could+not+find+image");
            }


            a.setServing((String)obj.get("serving_unit"),((Number) obj.get("serving_qty")).intValue());
            select.add(a);

        }
        listview.setAdapter(mylist);
        //listview.setAdapter(new ArrayAdapter<>(con, android.R.layout.simple_list_item_1,Selected));



        //22:35 ---- https://www.youtube.com/watch?v=qzRKa8I36Ww&t=1205s&ab_channel=CodingMaster-ProgrammingTutorials
    }













    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void sendexercisequery(String query, ListView list, Context cont, CustomExersiceList mylist, ArrayList<ExerciseItem> select) throws IOException, JSONException {
        String raw = "";
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here



            URL url = new URL("https://trackapi.nutritionix.com/v2/natural/exercise");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");


            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-app-id", "d276f2d9");
            con.setRequestProperty("x-app-key", "a8ec5707141e19330b40e1bb07081ce7");


            con.setDoOutput(true);


            String jsonInputString = "{\n" +
                    "     \"query\":\""+query+"\"\n" +
                    "}";
            System.out.println(jsonInputString);


            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                //System.out.println(response.toString());

                parseexercise(response.toString(),select,list,cont,mylist);


            }
            con.disconnect();

        }

    }


    public static void parseexercise(String responsebody, ArrayList<ExerciseItem> select, ListView listview, Context con, CustomExersiceList mylist) throws JSONException {
        System.out.println(responsebody);
        JSONObject albums = new JSONObject(responsebody);

        JSONObject ar = new JSONObject(responsebody);
        JSONArray newar = ar.getJSONArray("exercises");


        JSONObject obj;
        JSONObject photo;
        if (newar.length()==0){Toast.makeText(con, "no items found", Toast.LENGTH_LONG).show();}
        for (int i = 0; i < newar.length(); i++) {
            System.out.println(newar.get(i));
            obj = (JSONObject) newar.get(i);

            ExerciseItem a = new ExerciseItem();
            a.setType((String) obj.get("user_input"));
            a.setCalories(((Number) obj.get("nf_calories")).intValue());
            a.setDuration(((Number) obj.get("duration_min")).doubleValue());

            select.add(a);

        }
        listview.setAdapter(mylist);
        //listview.setAdapter(new ArrayAdapter<>(con, android.R.layout.simple_list_item_1,Selected));



        //22:35 ---- https://www.youtube.com/watch?v=qzRKa8I36Ww&t=1205s&ab_channel=CodingMaster-ProgrammingTutorials
    }
}
