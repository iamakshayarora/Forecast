package com.example.akshay.forecast;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class WeatherpageActivity extends AppCompatActivity {
    public Button button;
    TextView t1_mintmp,t2_max_tmp,t3_desc,t4_desc2,text,category;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherpage);
        String cityname= getIntent().getStringExtra("CityName").toUpperCase();
        String countryname= getIntent().getStringExtra("CountryName").toUpperCase();
        final String key= getIntent().getStringExtra("Key");

        mDrawerList = (ListView)findViewById(R.id.navList);

        String[] osArray = { "Home", "Search", "Search on Map", "About"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FrontActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                if(position==0) {
                    Intent myIntent = new Intent(WeatherpageActivity.this, FrontActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    WeatherpageActivity.this.startActivity(myIntent);
                }
                if(position==1) {
                    Intent myIntent = new Intent(WeatherpageActivity.this, SearchActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    WeatherpageActivity.this.startActivity(myIntent);
                }
                if(position==2) {
                    Intent myIntent = new Intent(WeatherpageActivity.this, MapsActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    WeatherpageActivity.this.startActivity(myIntent);
                }
                if(position==3) {
                    Intent myIntent = new Intent(WeatherpageActivity.this, AboutActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    WeatherpageActivity.this.startActivity(myIntent);
                }

            }
        });
        

        TextView textviewcity  = findViewById(R.id.city);
        textviewcity.setText(cityname);
        TextView textviewcountry  = findViewById(R.id.country);
        textviewcountry.setText(countryname);

        Calendar calendar = Calendar.getInstance();
        String CurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        TextView textViewDate  = findViewById(R.id.date);
        textViewDate.setText(CurrentDate);



        t1_mintmp = findViewById(R.id.mintmp);
        t2_max_tmp =findViewById(R.id.maxtmp);
        t3_desc = findViewById(R.id.desc);
        t4_desc2=findViewById(R.id.desc2);
        text=findViewById(R.id.condition);
        category=findViewById(R.id.category);

        find_weather(key);


    }

    public void find_weather(String key){

        String url ="http://dataservice.accuweather.com/forecasts/v1/daily/1day/"+key+"?apikey=52JoPh1gnZJ1islbf3j5g3e8fvOP7CmY&metric=true";

        JsonObjectRequest jor =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray array = response.getJSONArray("DailyForecasts");
                    JSONObject resultsObj = array.getJSONObject(0);
                    JSONObject temp = resultsObj.getJSONObject("Temperature");
                    String minTemp = temp.getJSONObject("Minimum").getString("Value");
                    String maxTemp = temp.getJSONObject("Maximum").getString("Value");

                    JSONObject day = resultsObj.getJSONObject("Day");
                    String desc = day.getString("IconPhrase");

                    JSONObject night = resultsObj.getJSONObject("Night");
                    String desc2 = night.getString("IconPhrase");

                    t1_mintmp.setText(minTemp);
                    t2_max_tmp.setText(maxTemp);
                    t3_desc.setText(desc);
                    t4_desc2.setText(desc2);


                    //JSONArray array2 = response.getJSONArray("Headline");
                    //JSONObject resultsObj2 = array2.getJSONObject(0);
                    String head = response.getJSONObject("Headline").getString("Text");
                    String cat = response.getJSONObject("Headline").getString("Category");
                    text.setText(head);
                    category.setText("Category : "+cat);


                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);

    }

}
