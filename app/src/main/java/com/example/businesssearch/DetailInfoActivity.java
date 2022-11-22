package com.example.businesssearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.MapView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class DetailInfoActivity extends AppCompatActivity {
    public static String id, name, location, price, phone, category, yelpUrl;
    public static boolean status;
    public static String[] coordinates = new String[2];
    public static String[] photos = null;
    public static RequestQueue requestQueue;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            location = intent.getStringExtra("address");
            price = intent.getStringExtra("price");
            phone = intent.getStringExtra("phone");
            category = intent.getStringExtra("category");
            yelpUrl = intent.getStringExtra("url");
            status = intent.getBooleanExtra("status", true);
            coordinates = intent.getStringArrayExtra("coordinate");
            photos = intent.getStringArrayExtra("photos");
        }

        requestQueue =Volley.newRequestQueue(this);
        context = DetailInfoActivity.context;


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);

        DetailInfoFragmentAdapter detailInfoFragmentAdapter = new DetailInfoFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(detailInfoFragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("BUSINESS DETAILS");
            } else if (position == 1) {
                tab.setText("MAP LOCATION");
            } else {
                tab.setText("REVIEWS");
            }
        }).attach();


    }

}