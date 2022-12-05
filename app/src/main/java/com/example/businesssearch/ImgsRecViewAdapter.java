package com.example.businesssearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImgsRecViewAdapter extends RecyclerView.Adapter<ImgsRecViewAdapter.ViewHolder>{
    private ArrayList<String> photos;
    Context context;

    public ImgsRecViewAdapter(Context context) {
        this.context = context;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        context = holder.itemView.getContext();
        Glide.with(context).asBitmap().load(photos.get(position)).into(holder.businessImg);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView businessImg;
        private View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessImg = itemView.findViewById(R.id.sliderImg);
            this.itemView = itemView;
        }
    }
}
