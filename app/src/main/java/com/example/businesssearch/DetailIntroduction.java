package com.example.businesssearch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailIntroduction extends Fragment {

    public static final String TITLE = "BUSINESS DETAILS";
    private TextView addressIntro, priceIntro, phoneIntro, statusIntro, categoryIntro, yelpIntro;


    public DetailIntroduction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addressIntro = view.findViewById(R.id.addressIntro);
        priceIntro = view.findViewById(R.id.priceIntro);
        phoneIntro = view.findViewById(R.id.phoneIntro);
        statusIntro = view.findViewById(R.id.statusIntro);
        categoryIntro = view.findViewById(R.id.categoryIntro);
        yelpIntro = view.findViewById(R.id.yelpIntro);
        addressIntro.setText(DetailInfoActivity.location);
        priceIntro.setText(DetailInfoActivity.price);
        phoneIntro.setText(DetailInfoActivity.phone);
        if (DetailInfoActivity.status) {
            statusIntro.setTextColor(getResources().getColor(R.color.green_500));
            statusIntro.setText("Open Now");
        } else {
            statusIntro.setTextColor(getResources().getColor(R.color.red_500));
            statusIntro.setText("Closed");
        }
        categoryIntro.setText(DetailInfoActivity.category);
//        yelpIntro.setText(DetailInfoActivity.yelpUrl);
    }
}