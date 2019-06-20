package com.example.akshay.forecast;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    Button btn;
    TextView result;
    EditText entrytext;
    private static final String TAG = SearchActivity.class.getSimpleName() ;
    private ArrayList<Weather2> weatherArrayList2 = new ArrayList<>();
    private ListView listView2;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        btn =   findViewById(R.id.searchbtn);
        entrytext =  findViewById(R.id.searchbox);
        listView2 = findViewById(R.id.idListView2);
        mDrawerList = findViewById(R.id.navList);

        String[] osArray = { "Home", "Search", "Search on Map", "About"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                Intent myIntent = new Intent(SearchActivity.this, FrontActivity.class);
                SearchActivity.this.startActivity(myIntent);
                }
                if(position==2) {
                    Intent myIntent = new Intent(SearchActivity.this, MapsActivity.class);
                    SearchActivity.this.startActivity(myIntent);
                }
                if(position==3) {
                    Intent myIntent = new Intent(SearchActivity.this, AboutActivity.class);
                    SearchActivity.this.startActivity(myIntent);
                }
            }
        });
                btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String search = entrytext.getText().toString();
                URL weatherUrl = NetworkUtils2.buildUrlForWeather(search);
                new FetchWeatherDetails2().execute(weatherUrl);
                Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);
            }
        });
    }
    private class FetchWeatherDetails2 extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults2 = null;
            try{
                weatherSearchResults2 = NetworkUtils2.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: weatherSearchResults: " + weatherSearchResults2);
            return weatherSearchResults2;
        }

        @Override
        protected void onPostExecute(String weatherSearchResults2) {
            if(weatherSearchResults2 != null && !weatherSearchResults2.equals("")) {
                weatherArrayList2 = parseJSON(weatherSearchResults2);
                Iterator itr = weatherArrayList2.iterator();
                while(itr.hasNext()){
                    Weather2 weatherInIterator=(Weather2) itr.next();
                    Log.i(TAG, "onPostExecute: CityName: " + weatherInIterator.getCityname() +
                            " Key: " + weatherInIterator.getKey()+
                            " CountryName: " + weatherInIterator.getCountryname()+
                            " AreaName: " + weatherInIterator.getAreaname());
                }
            }
            super.onPostExecute(weatherSearchResults2);
        }
    }

    private ArrayList<Weather2> parseJSON(String weatherSearchResults2) {
        if(weatherArrayList2 != null){
            weatherArrayList2.clear();
        }
        if(weatherSearchResults2 != null){
            try{
                JSONArray rootObject = new JSONArray(weatherSearchResults2);
                for (int i=0;i<rootObject.length(); i++){
                    Weather2 weather = new Weather2();
                    JSONObject resultsObj = rootObject.getJSONObject(i);
                    String cityname = resultsObj.getString("EnglishName");
                    weather.setCityname(cityname);
                    String key = resultsObj.getString("Key");
                    weather.setKey(key);
                    JSONObject countryobj = resultsObj.getJSONObject("Country");
                    String countryname = countryobj.getString("EnglishName");
                    weather.setCountryname(countryname);
                    JSONObject areaobj = resultsObj.getJSONObject("AdministrativeArea");
                    String areaname = areaobj.getString("EnglishName");
                    weather.setAreaname(areaname);
                    weatherArrayList2.add(weather);
                }
                if(weatherArrayList2 != null){
                    WeatherAdapter2 weatherAdapter2 = new WeatherAdapter2(this,weatherArrayList2);
                    listView2.setAdapter(weatherAdapter2);
                    listView2.setClickable(true);
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object o = listView2.getItemAtPosition(position);
                            Weather2 str=(Weather2) o;//As you are using Default String Adapter
                           Intent myIntent = new Intent(SearchActivity.this, WeatherpageActivity.class);
                            myIntent.putExtra("CityName", str.getCityname());
                            myIntent.putExtra("CountryName", str.getCountryname());
                            myIntent.putExtra("Key", str.getKey());
                            SearchActivity.this.startActivity(myIntent);
                        }
                    });
                }
                return weatherArrayList2;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

