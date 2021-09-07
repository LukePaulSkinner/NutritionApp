package com.example.nutritionapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomFoodList extends ArrayAdapter {
    private ArrayList<FoodItem> selectedlist;
    private Activity context;

    public CustomFoodList(Activity context, ArrayList<FoodItem> selected) {
        super(context, R.layout.row_item, selected);
        this.context = context;
        this.selectedlist = selected;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.textViewName);
        TextView textViewCapital = (TextView) row.findViewById(R.id.textViewDescription);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewholder);

        textViewCountry.setText(selectedlist.get(position).getName());
        textViewCapital.setText(selectedlist.get(position).getServing());
        Picasso.get().load(selectedlist.get(position).getImgurl()).into(imageFlag);


        return  row;
    }
}
