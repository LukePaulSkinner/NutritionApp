package com.example.nutritionapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
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
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Initialize Objects

    public static String gender = "";
    public static int bmr,days;
    public static List<CustomMeal> custommeals= new ArrayList<>();
    public static FoodItem activeITEM;
    public static ArrayList<HistoryItem> FoodHistory= new ArrayList<>();
    public static int calories ;
    ArrayAdapter adapter;
    public  int protein ;
    public  int sugar ;
    public  int fat ;
    public  int carbohydrates ;
    public  int fibre ;
    public  int saturates ;
    public static int calgoal,height,age;
    int dailyCalories = 2400;
    public  static ArrayList<FoodItem> Selected = new ArrayList<FoodItem>();
    public  static ArrayList<ExerciseItem> ExerciseSelected = new ArrayList<ExerciseItem>();
    private String appid = "d276f2d9";
    private String appkey = "3d1a613b4f599dbcc4a1ba322dab6b4f";
    private String active ="food";



    public static void setDays(int num){MainActivity.days=num;}
    public static void setGender(String gender) {
        MainActivity.gender = gender;
    }
    public static int getGoal() {
        return goal;
    }
    public static double getcurrent() {
        return currentweight;
    }
    public static void setGoal(int goal) {
        MainActivity.goal = goal;
    }
    public static void setAge(int age) {
        MainActivity.age = age;
    }
    public static void setBMR(int bmr) {
        MainActivity.bmr = bmr;
    }
    public static void setHeight(int height) {
        MainActivity.height = height;
    }
    public static void setCurrentweight(double setCurrentweight) { MainActivity.currentweight = setCurrentweight; }

    public static int goal = 70;
    public static double currentweight = 00;
    ListView listview;
    SearchView search;
    Button accountbutton,newfood,history,savebutton,achiev,toggle;
    TextView textView,mode;




    /////////////////
    //INFO ABOUT DAILY RECOMMENDATIONS
    int calm =2500;
    int calf =2000;
    int protm =56;
    int protf =46;
    int sugm =150;
    int sugf =100;
    int fatm =375;
    int fatf =300;
    int carbm =1375;
    int carbf =1100;
    int fibrem =28;
    int fibref =28;
    int satm =250;
    int satf =200;

    ////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        //CODE FOR FIRST TIME START UP

        //viewPager = findViewById(R.id.viewPager);
        //IntroAdapter introAdapter = new IntroAdapter(getSupportFragmentManager());
        //viewPager.setAdapter(introAdapter);
        if (hasused()==false) {
            Intent i = new Intent(getApplicationContext(), Introduction.class);
            startActivity(i);
            finish();
        }
        /////////////////////////////

        //Reading CSV file







        try {
            getinfo();
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Assigning var ID
        search = findViewById(R.id.search);
        textView = findViewById(R.id.textView);
        listview = findViewById(R.id.Listview);
        accountbutton = findViewById(R.id.accountbutton);
        newfood = findViewById(R.id.newfoodbutton);
        history = findViewById(R.id.history);
        savebutton = findViewById(R.id.savebutton);
        achiev = findViewById(R.id.Achievments);

        mode = findViewById(R.id.modetext);
        toggle=findViewById(R.id.togglebutton);




        ////
        //Setting daily intake
        compareToRecomended();
        if (gender=="Male"){
            if (calgoal==0){
                dailyCalories = calm;
            }else {dailyCalories=calgoal;}

            protein = protm;
            sugar = sugm;
            fat=fatm;
            carbohydrates=carbm;
            fibre=fibrem;
            saturates=satm;
        }else {
            if (calgoal==0){
                dailyCalories = calf;
            }else {dailyCalories=calgoal;}
            protein = protf;
            sugar = sugf;
            fat=fatf;
            carbohydrates=carbf;
            fibre=fibref;
            saturates=satf;
        }
        savenutrition();
        ////
        final CustomFoodList myfoodlist = new CustomFoodList(this,Selected);
        final CustomExersiceList myexerciselist = new CustomExersiceList(this,ExerciseSelected);
        //Setting adapter and Spinner content


        listview.setAdapter(myfoodlist);


        final ArrayList<String> customnames = new ArrayList<>();
        for (CustomMeal meal:custommeals){
            customnames.add(meal.getName());
        }

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,customnames );










        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    if (active=="food") {
                        Requests.sendfoodquery(query, listview, MainActivity.this, myfoodlist,Selected);
                        compareToRecomended();
                    }else if (active=="exercise"){Requests.sendexercisequery(query, listview, MainActivity.this, myexerciselist,ExerciseSelected);}

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String userInput) {
                if (active=="meal"){

                    listview.setAdapter(adapter);
                    System.out.println(customnames.size());

                    adapter.getFilter().filter(userInput);

                }else{}

                return false;
            }
        });




        //Code For Clicking on list
        if (active=="food") {
            listview.setAdapter(myfoodlist);
        }else {listview.setAdapter(myexerciselist);}
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(active=="food"){
                    Toast.makeText(MainActivity.this,"Removed"+Selected.get(position).toString(),Toast.LENGTH_SHORT).show();
                    Selected.remove(Selected.get(position));
                    listview.setAdapter(myfoodlist);
                }else if (active=="meal"){
                    for (CustomMeal meal:custommeals){
                        if (meal.getName().equals(customnames.get(position))){
                            for (FoodItem item:meal.getMeallsit()){
                                Selected.add(item);
                            }
                        }
                    }
                    active="food";
                    mode.setText("Food");
                    listview.setAdapter(myfoodlist);


                    ///////////////////////////////////////////////////////////////////////////////

                }
                else{
                    Toast.makeText(MainActivity.this,"Removed"+ExerciseSelected.get(position).toString(),Toast.LENGTH_SHORT).show();
                    ExerciseSelected.remove(ExerciseSelected.get(position));
                    listview.setAdapter(myexerciselist);
                }

                compareToRecomended();

            }
        });



        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (active=="food"){
                    activeITEM = (Selected.get(position));
                    saveSelected();
                    Intent i = new Intent(getApplicationContext(),FoodInfo.class);
                    startActivity(i);
                }
                return true;
            }
        });


        //////
        //NOTIFICATIONS

        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.HOUR_OF_DAY, 15);
        //calendar.set(Calendar.MINUTE, 57);
        //calendar.set(Calendar.SECOND, 40);
        //Intent intent = new Intent(getApplicationContext(), Notification_reciver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        /////
        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelected();
                Intent i = new Intent(getApplicationContext(),AccountPage.class);
                startActivity(i);
            }
        });


        newfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelected();
                Intent i = new Intent(getApplicationContext(),AddFood.class);
                startActivity(i);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelected();
                Intent i = new Intent(getApplicationContext(),History.class);
                startActivity(i);
            }
        });
        achiev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelected();
                Intent i = new Intent(getApplicationContext(),Achievements.class);
                startActivity(i);
            }
        });


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (active=="food") {
                    active = "meal";
                    listview.setAdapter(adapter);
                    mode.setText("Meals");

                }else if (active=="meal"){
                    active="exercise";
                    mode.setText("Exercise");
                    listview.setAdapter(myexerciselist);


                }
                else{
                    active="food";
                    mode.setText("Food");
                    listview.setAdapter(myfoodlist);


                }
                for (CustomMeal meal:custommeals){
                    Log.d("TAG", "onClick:"+meal.getName());

                }
            }
        });




        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isdate = false;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String check = formatter.format(date);
                for (HistoryItem hist: FoodHistory){
                    if (hist.getName().equals(check)){
                        isdate=true;
                        hist.addlist(Selected);
                        hist.addExercise(ExerciseSelected);


                    }
                }
                if (isdate==false){
                    HistoryItem b = new HistoryItem();
                    List<FoodItem> example = new ArrayList<>(Selected);
                    List<ExerciseItem> exerexample = new ArrayList<>(ExerciseSelected);
                    b.setFoodlist(example);
                    //generateScore(a);
                    b.setName(formatter.format(date));
                    b.addExercise(exerexample);
                    FoodHistory.add(b);
                    Log.d("TAG", "onClick: "+Selected.size());
                    SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                    setDays(MainActivity.days+1);
                    sharedPreferences.edit().putInt("days",MainActivity.days ).apply();

                }
                Selected.clear();
                ExerciseSelected.clear();
                if (active=="food") {
                    listview.setAdapter(myfoodlist);
                }else {listview.setAdapter(myexerciselist);}
                Toast.makeText(MainActivity.this, "Food items have been saved", Toast.LENGTH_LONG).show();

                //compareToRecomended();

                //Selected.clear();
                for (HistoryItem ite:FoodHistory){
                    Log.d("TAG", "onClick2222:"+ite.getFoodlist().size());
                }
                saveHistory();
            }
        });



    }




    private void compareToRecomended(){
        double totalcal = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        for (HistoryItem hist: FoodHistory){
            if (hist.getName().equals(formatter.format(date))){
                totalcal=hist.gettotalCal();
            }
        }
        for (FoodItem item:Selected){
            totalcal+=item.getCalories();
        }
        if (totalcal>dailyCalories){textView.setText("You have eaten: "+totalcal+" calories. This is: "+(totalcal-dailyCalories)+" too many");}
        else if(totalcal<dailyCalories){textView.setText("You have eaten: "+totalcal+" calories. This is: "+(dailyCalories-totalcal)+" too few");}
        else {textView.setText("You have eaten: "+totalcal+" calories. This is perfect.");}
    }






    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public void getinfo()throws  IOException {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        MainActivity.setGoal(sharedPreferences.getInt("goal",70));
        MainActivity.setCurrentweight(getDouble(sharedPreferences,"current",0.0));
        MainActivity.setGender(sharedPreferences.getString("gender","Male"));
        MainActivity.setAge(sharedPreferences.getInt("age",0));
        MainActivity.setHeight(sharedPreferences.getInt("height",0));
        MainActivity.setBMR(sharedPreferences.getInt("bmr",0));
        calgoal=sharedPreferences.getInt("calgoal",2200);

        MainActivity.setDays(sharedPreferences.getInt("days",0));

    }

    public static double KGtoPoounds(double kg){
        return kg/2.205;
    }






    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        String jsonselect = sharedPreferences.getString("select", null);
        String jsoncustom = sharedPreferences.getString("custommeals", null);
        String exerslect = sharedPreferences.getString("exerselect", null);

        Type typecustom = new TypeToken<ArrayList<CustomMeal>>() {}.getType();
        MainActivity.custommeals = gson.fromJson(jsoncustom, typecustom);
        if (MainActivity.custommeals == null) {
            MainActivity.custommeals = new ArrayList<>();
        }

        Type type = new TypeToken<ArrayList<FoodItem>>() {}.getType();
        MainActivity.Selected=gson.fromJson(jsonselect, type);
        if (MainActivity.Selected == null) {
            MainActivity.Selected = new ArrayList<>();
        }
        Type exertype = new TypeToken<ArrayList<ExerciseItem>>() {}.getType();
        MainActivity.ExerciseSelected=gson.fromJson(exerslect, exertype);
        if (MainActivity.ExerciseSelected == null) {
            MainActivity.ExerciseSelected = new ArrayList<>();
        }


        SharedPreferences sharedPreferencesh = getSharedPreferences("history", MODE_PRIVATE);
        Gson gsonh = new Gson();
        String jsonh = sharedPreferencesh.getString("hist", null);
        Type typeh = new TypeToken<ArrayList<HistoryItem>>() {}.getType();
        MainActivity.FoodHistory = gsonh.fromJson(jsonh, typeh);
        if (MainActivity.FoodHistory == null) {
            MainActivity.FoodHistory = new ArrayList<>();
        }




    }

    private void saveSelected(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.Selected);
        editor.putString("select", json);
        String exerjson = gson.toJson(MainActivity.ExerciseSelected);
        editor.putString("exerselect", json);
        editor.apply();
    }
    private void savenutrition(){
        SharedPreferences sharedPreferences = getSharedPreferences("nut", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        sharedPreferences.edit().putInt("cal",dailyCalories).apply();
        sharedPreferences.edit().putInt("protein",protein).apply();
        sharedPreferences.edit().putInt("sugar",sugar).apply();
        sharedPreferences.edit().putInt("fat",fat).apply();
        sharedPreferences.edit().putInt("carbohydrates",carbohydrates).apply();
        sharedPreferences.edit().putInt("fibre",fibre).apply();
        sharedPreferences.edit().putInt("saturates",saturates).apply();
        Log.d("TAG", "TEST2!"+ protein);
    }


    private void saveHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.FoodHistory);
        editor.putString("hist", json);
        editor.apply();
    }
    //rewrite
    public void generateScore(FoodItem foodday){
        double score=0;

        //Score
        if (calories!=0){score += calories/foodday.getCalories();}
        if (protein!=0){score += protein/foodday.getProtein();}
        if (fat!=0){score += fat/foodday.getFat();}
        if (carbohydrates!=0){score += carbohydrates/foodday.getCarbohydrate();}
        if (saturates!=0){score += saturates/foodday.getSaturates();}
        if (fibre!=0){score += fibre/foodday.getfibre();}
        if (sugar!=0){score += sugar/foodday.getSugars();}

        System.out.println("THIS HERE: "+score);
        score=score/7;
        if (score>100){
            score=100-(score-100);
        }


        foodday.setScore(score);
    }
    private boolean hasused(){
        SharedPreferences sharedPreferences = getSharedPreferences("used", MODE_PRIVATE);
        Boolean result = sharedPreferences.getBoolean("used",false);
        Log.d("TAG", "RESULT: "+result);
        return result;
    }

}