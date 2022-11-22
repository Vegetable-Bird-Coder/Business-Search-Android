package com.example.businesssearch;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
    private ArrayList<String> photos;
    Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Glide.with(viewHolder.itemView)
                .load(photos.get(position))
                .fitCenter()
                .into(viewHolder.sliderImg);
    }

    @Override
    public int getCount() {
        return photos.size();
    }



    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        ImageView sliderImg;
        View itemView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            sliderImg = itemView.findViewById(R.id.sliderImg);
            this.itemView = itemView;
        }
    }
}
