package com.example.businesssearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusinessesRecViewAdapter extends RecyclerView.Adapter<BusinessesRecViewAdapter.ViewHolder> {
    private ArrayList<BusinessInfo> businessesInfo = new ArrayList<>();
    private Context context;
    private RequestQueue requestQueue;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_business, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.businessId.setText(String.valueOf(holder.getAdapterPosition() + 1));
        holder.businessName.setText(businessesInfo.get(position).getName());
        holder.businessRating.setText(String.valueOf(businessesInfo.get(position).getRating()));
        holder.businessDistance.setText(String.valueOf(businessesInfo.get(position).getDistance()));
        Glide.with(context).asBitmap().load(businessesInfo.get(position).getImageUrl()).into(holder.businessImg);

        holder.businessInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://business-search-web-backend.wl.r.appspot.com/api.yelp.com/v3/businesses/" + businessesInfo.get(holder.getAdapterPosition()).getId();
                detailInfoSearch(url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return businessesInfo.size();
    }

    public BusinessesRecViewAdapter(Context context, RequestQueue requestQueue) {
        this.context = context;
        this.requestQueue = requestQueue;
    }

    public void setBusinessesInfo(ArrayList<BusinessInfo> businessesInfo) {
        this.businessesInfo = businessesInfo;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView businessId, businessName, businessRating, businessDistance;
        private ImageView businessImg;
        private CardView businessInfoCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessId = itemView.findViewById(R.id.idResult);
            businessName = itemView.findViewById(R.id.nameResult);
            businessRating = itemView.findViewById(R.id.ratingResult);
            businessDistance = itemView.findViewById(R.id.distanceResult);
            businessImg = itemView.findViewById(R.id.imgResult);
            businessInfoCard = itemView.findViewById(R.id.businessInfoCard);


        }
    }

    private void detailInfoSearch(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String id, name, location, price, phone, category, yelpUrl;
                        boolean status;
                        String[] coordinates = new String[2];
                        String[] photos = null;
                        String[] realPhotos = null;
                        id = "N/A";
                        name = "N/A";
                        location = "N/A";
                        price = "N/A";
                        phone = "N/A";
                        category = "N/A";
                        yelpUrl = "N/A";
                        status = false;
                        try {
                            id = response.getString("id");
                            name = response.getString("name");
                            JSONObject locationObject = response.getJSONObject("location");
                            String locationString = locationObject.getString("display_address");
                            String prefix = "";
                            location = "";
                            for (String s : locationString.substring(1, locationString.length() - 1).split(",")) {
                                location += prefix + s.substring(1, s.length() - 1);
                                prefix = ", ";
                            }
                            price = response.getString("price");
                            phone = response.getString("display_phone");
                            JSONArray categoryArray = response.getJSONArray("categories");
                            prefix = "";
                            category = "";
                            for (int i = 0; i < categoryArray.length(); i++) {
                                category += (prefix + categoryArray.getJSONObject(i).getString("title"));
                                prefix = ", ";
                            }
                            yelpUrl = response.getString("url");
                            status = response.getBoolean("is_closed");
                            JSONObject coordinatesObject = response.getJSONObject("coordinates");
                            coordinates[0] = coordinatesObject.getString("latitude");
                            coordinates[1] = coordinatesObject.getString("longitude");

                            String photosStr = response.getString("photos");
                            photos = photosStr.substring(1, photosStr.length() - 1).split(",");
                            realPhotos = new String[photos.length];
                            int idx = 0;
                            for (String photo : photos) {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (char ch : photo.toCharArray()) {
                                    if (ch != '\\') {
                                        stringBuilder.append(ch);
                                    }
                                }
                                String res = stringBuilder.toString();
                                realPhotos[idx++] = res.substring(1, res.length() - 1);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(context, DetailInfoActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("address", location);
                        intent.putExtra("price", price);
                        intent.putExtra("phone", phone);
                        intent.putExtra("category", category);
                        intent.putExtra("url", yelpUrl);
                        intent.putExtra("status", status);
                        intent.putExtra("coordinate", coordinates);
                        intent.putExtra("photos", realPhotos);
                        context.startActivity(intent);
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
