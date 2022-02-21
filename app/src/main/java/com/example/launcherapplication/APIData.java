package com.example.launcherapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class APIData extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RecyclerViewData> data = new ArrayList<>();
    ProgressBar loadingPB;
    String city_name, country_name, temperature_Celsius, description_detail;
    String url;

    // Array of URLs of APIs to be parsed
    String[] links = new String[]{"http://weather.bfsah.com/beijing", "http://weather.bfsah.com/berlin",
            "http://weather.bfsah.com/cardiff", "http://weather.bfsah.com/edinburgh",
            "http://weather.bfsah.com/london"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_data);
         //set up the RecyclerView
        recyclerView = findViewById(R.id.apidata);
        // set up the progress bar
        loadingPB = findViewById(R.id.idLoadingPB);

        //For hiding our progress bar.
        loadingPB.setVisibility(View.GONE);

        // Recyclerview is visible after getting all the data.
        recyclerView.setVisibility(View.VISIBLE);

        FileContentReader fcr= new FileContentReader(getApplicationContext());
        for (int i = 0; i < links.length; i++) {
        url= fcr.getContentFromUrl(links[i]);
        if(!url.isEmpty()) {
            try {
                JSONObject ja = new JSONObject(url);
                String text_city = ja.getString("city");
                String text_country = ja.getString("country");
                String text_temperature = ja.getString("temperature");
                String text_description = ja.getString("description");
                city_name = "City name: " + text_city;
                country_name = "Country: " + text_country;
                temperature_Celsius = "Current temperature: " + text_temperature;
                description_detail = "Current description : " + text_description;


                data.add(new RecyclerViewData(city_name, country_name, temperature_Celsius, description_detail));
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);
                recyclerView.setAdapter(adapter);

                // To set RecyclerView's layout
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);

                //To put Divider between each API data
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        linearLayoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }
    }
}
