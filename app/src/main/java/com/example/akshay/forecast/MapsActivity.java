package com.example.akshay.forecast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker marker;
    String key,minTemp,maxTemp,cityname,countryname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point){
                final LatLng mark = new LatLng(point.latitude, point.longitude);
                if (marker != null) {
                    marker.remove();
                }
                String url ="http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=aQMG1AeQjo788PzhW44ajsd9XV4g7whU&q="+point.latitude+"%2C"+point.longitude;
                JsonObjectRequest jor =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            key = response.getString("Key");
                            cityname=response.getString("EnglishName");
                            countryname=response.getJSONObject("Country").getString("EnglishName");
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
                RequestQueue queue=  Volley.newRequestQueue(MapsActivity.this);
                queue.add(jor);
                String url2="http://dataservice.accuweather.com/forecasts/v1/daily/1day/"+key+"?apikey=aQMG1AeQjo788PzhW44ajsd9XV4g7whU&metric=true";
                JsonObjectRequest jor2 =new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray array = response.getJSONArray("DailyForecasts");
                            JSONObject resultsObj = array.getJSONObject(0);
                            JSONObject temp = resultsObj.getJSONObject("Temperature");
                            minTemp = temp.getJSONObject("Minimum").getString("Value");
                            maxTemp = temp.getJSONObject("Maximum").getString("Value");
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
                RequestQueue queue2= Volley.newRequestQueue(MapsActivity.this);
                queue2.add(jor2);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        marker=mMap.addMarker(new MarkerOptions().position(mark).title("Temperature").snippet("High: "+maxTemp+" Low: "+minTemp));
                        marker.showInfoWindow();
                        Toast.makeText(getBaseContext(),cityname + ", " + countryname,Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }
}
