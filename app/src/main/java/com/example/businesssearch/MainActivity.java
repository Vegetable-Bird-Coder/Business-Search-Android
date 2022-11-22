package com.example.businesssearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView keywordSearch;
    private EditText distanceSearch, locationSearch;
    private Spinner categorySearch;
    private CheckBox autoDetectSearch;
    private Button submitSearch, clearSearch;
    private RequestQueue requestQueue;
    private ImageView calendarIcon;
    private String[] suggestions = new String[]{"123", "234", "asdf"};

    private String[] autoLocation;
    private ArrayList<BusinessInfo> businessesInfo;
    private RecyclerView businessesRecyclerView;
    private BusinessesRecViewAdapter businessesRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        keywordSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String keyword = keywordSearch.getText().toString();
//                if (keyword.length() >= 1) {
//                    String url = "https://business-search-web-backend.wl.r.appspot.com/api.yelp.com/v3/autocomplete?text=";
//                    url += keyword;
//                    Log.d("MyURL", url);
//                    autoComplete(url);
//                }
//            }
//        });



        autoDetectSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoDetectLocation();
            }
        });

        // category
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category_array_search, android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySearch.setAdapter(categoryAdapter);




        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmit();
            }
        });


        
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClear();
            }
        });

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Click Me", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleClear() {
        businessesRecyclerView.setVisibility(View.GONE);
        businessesInfo = new ArrayList<>();
        keywordSearch.setText("");
        distanceSearch.setText("");
        categorySearch.setSelection(0);
        locationSearch.setText("");
        if (autoDetectSearch.isChecked()) {
            autoDetectSearch.setChecked(false);
            locationSearch.setVisibility(View.VISIBLE);
        }
    }

    private String buildBusinessesSearchUrl() {
        String keyword = keywordSearch.getText().toString();
        String category = categorySearch.getSelectedItem().toString();
        String location = locationSearch.getText().toString();
        String latitude = autoLocation[0];
        String longitude = autoLocation[1];
        String distance = distanceSearch.getText().toString();

        String url = "https://business-search-web-backend.wl.r.appspot.com/api.yelp.com/v3/businesses/search?";
        url += "term=" + keyword;
        switch (category) {
            case "Arts and Entertainment":
                category = "arts";
                break;
            case "Health and Medical":
                category = "health";
                break;
            case "Hotel and Travel":
                category = "hoteltravel";
                break;
            case "Food":
                category = "food";
                break;
            case "Professional Services":
                category = "professional";
                break;
            default:
                category = "all";
                break;
        }
        url += "&&categories=" + category;
        if (distance.equals("")) {
            distance = "10";
        } else {
            distance = String.valueOf(Math.min(10, Integer.parseInt(distance)));
        }
        url += "&&radius=" + distance;
        if (!location.equals("")) {
            url += "&&location=" + location;
        } else {
            url += "&&latitude=" + latitude + "&&longitude=" + longitude;
        }
        return url;
    }

    private boolean checkAllField() {
        if (keywordSearch.getText().toString().equals("")) {
            keywordSearch.setError("This field is required");
            return false;
        }
        if (locationSearch.getText().toString().equals("") && !autoDetectSearch.isChecked()) {
            locationSearch.setError("This field is required");
            return false;
        }
        return true;
    }

    private void handleSubmit() {
        if (checkAllField()) {
            businessSearch(buildBusinessesSearchUrl());
        }
    }

    private void autoDetectLocation() {
        if (autoDetectSearch.isChecked()) {
            locationSearch.setEnabled(false);
            locationSearch.setText("");
            locationSearch.setVisibility(View.INVISIBLE);
            API.autoLocation(requestQueue, autoLocation);
        } else {
            locationSearch.setEnabled(true);
            locationSearch.setVisibility(View.VISIBLE);
        }
    }

    private void autoComplete(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String data = response.get("autocomplete").toString();
                            data = data.substring(1, data.length() - 1);
                            suggestions = data.split(",");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (String suggestion : suggestions) {
                            System.out.println(suggestion);
                        }
                        keywordSearch.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, suggestions));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void init() {
        keywordSearch = findViewById(R.id.keywordSearch);
        distanceSearch = findViewById(R.id.distanceSearch);
        locationSearch = findViewById(R.id.locationSearch);
        categorySearch = findViewById(R.id.categorySearch);
        autoDetectSearch = findViewById(R.id.autoDetectSearch);
        submitSearch = findViewById(R.id.submitSearch);
        clearSearch = findViewById(R.id.clearSearch);
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        autoLocation = new String[2];
        businessesInfo = new ArrayList<>();
        businessesRecyclerView = findViewById(R.id.businessesInfoResult);
        calendarIcon = findViewById(R.id.calendarIcon);
    }

    public void businessSearch(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("businesses");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String imageUrl = jsonObject.getString("image_url");
                                double rating = jsonObject.getDouble("rating");
                                int distance = (int) Math.round(jsonObject.getDouble("distance") / 1609.34);
                                BusinessInfo businessInfo = new BusinessInfo(id, name, imageUrl, rating, distance);
//                                Log.d("BusinessInfo", businessInfo.toString());
                                businessesInfo.add(businessInfo);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        businessesRecViewAdapter = new BusinessesRecViewAdapter(MainActivity.this, requestQueue);
                        businessesRecyclerView.setAdapter(businessesRecViewAdapter);
                        businessesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        businessesRecViewAdapter.setBusinessesInfo(businessesInfo);
                        businessesRecyclerView.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}