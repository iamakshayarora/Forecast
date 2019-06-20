package com.example.akshay.forecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akshay on 18-03-2018.
 */

public class WeatherAdapter2 extends ArrayAdapter<Weather2> {
    public WeatherAdapter2(@NonNull Context context, ArrayList<Weather2> weatherArrayList2) {
        super(context, 0, weatherArrayList2);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Weather2 weather = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item2, parent, false);
        }

        TextView citynameTextView = convertView.findViewById(R.id.tvCityName);
        TextView countrynameTextView = convertView.findViewById(R.id.tvCountryName);
        TextView areanameTextView = convertView.findViewById(R.id.tvAreaName);

        //TextView maxTextView = convertView.findViewById(R.id.tvHighTemperature);
        //TextView descTextView = convertView.findViewById(R.id.tvDesc);
        //TextView maxTextView2 = convertView.findViewById(R.id.maxtmp);
        //TextView minTextView2 = convertView.findViewById(R.id.mintmp);



        citynameTextView.setText(weather.getCityname());
        countrynameTextView.setText(weather.getCountryname());
        areanameTextView.setText(weather.getAreaname());
        //maxTextView.setText(weather.getMaxTemp());
        //descTextView.setText(weather.getDesc());
        //minTextView2.setText(weather.getMinTemp2());
        //maxTextView2.setText(weather.getMaxTemp2());


        return convertView;
    }
}
