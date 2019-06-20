package com.example.akshay.forecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AboutActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mDrawerList = (ListView)findViewById(R.id.navList);

        String[] osArray = { "Home", "Search", "Search on Map", "About"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FrontActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                if(position==0) {
                    Intent myIntent = new Intent(AboutActivity.this, FrontActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    AboutActivity.this.startActivity(myIntent);
                }
                if(position==1) {
                    Intent myIntent = new Intent(AboutActivity.this, SearchActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    AboutActivity.this.startActivity(myIntent);
                }
                if(position==2) {
                    Intent myIntent = new Intent(AboutActivity.this, MapsActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    AboutActivity.this.startActivity(myIntent);
                }

            }
        });

    }
}
