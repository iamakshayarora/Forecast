package com.example.akshay.forecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class FrontActivity extends AppCompatActivity {

    public Button button;

    TextView t1_mintmp,t2_max_tmp,t3_desc,t4_desc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        Calendar calendar = Calendar.getInstance();
        String CurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        TextView textViewDate  = findViewById(R.id.date);
        textViewDate.setText(CurrentDate);

        button = findViewById(R.id.but1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        t1_mintmp = (TextView)findViewById(R.id.mintmp);
        t2_max_tmp = (TextView)findViewById(R.id.maxtmp);
        t3_desc = (TextView)findViewById(R.id.desc);
        t4_desc2=(TextView)findViewById(R.id.desc2);

        find_weather();
    }

    public void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void find_weather(){

        String url ="http://dataservice.accuweather.com/forecasts/v1/daily/1day/190795?apikey=JUlCIjrcGoB71Vi1RgGLwwiGPooatana&metric=true";

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
