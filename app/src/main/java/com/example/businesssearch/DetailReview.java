package com.example.businesssearch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailReview extends Fragment {

    private ArrayList<Review> reviews;
    private RecyclerView reviewsRecyclerView;
    private ReviewsRecViewAdapter reviewsRecViewAdapter;

    public DetailReview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsRecyclerView = view.findViewById(R.id.reviewsRecView);
        reviews = new ArrayList<>();
        String url = "https://business-search-web-backend.wl.r.appspot.com/api.yelp.com/v3/businesses/" + DetailInfoActivity.id + "/reviews";
        loadReviews(url);
    }

    private void loadReviews(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONObject user = jsonObject.getJSONObject("user");
                                String name = user.getString("name");
                                int rating = jsonObject.getInt("rating");
                                String content = jsonObject.getString("text");
                                String date = jsonObject.getString("time_created").split(" ")[0];
                                Review review = new Review(name, rating, content, date);
                                reviews.add(review);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        reviewsRecViewAdapter = new ReviewsRecViewAdapter(DetailInfoActivity.context);
                        reviewsRecyclerView.setAdapter(reviewsRecViewAdapter);
                        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(DetailInfoActivity.context));
                        reviewsRecViewAdapter.setReviews(reviews);
                        reviewsRecyclerView.addItemDecoration(new DividerItemDecoration(reviewsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        DetailInfoActivity.requestQueue.add(jsonArrayRequest);
    }

}