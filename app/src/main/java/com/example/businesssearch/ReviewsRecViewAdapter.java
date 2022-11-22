package com.example.businesssearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsRecViewAdapter extends RecyclerView.Adapter<ReviewsRecViewAdapter.ViewHolder> {
    private ArrayList<Review> reviews = new ArrayList<>();
    private Context context;

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public ReviewsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.reviewName.setText(reviews.get(position).getName());
        String ratingText = "Rating: " + reviews.get(position).getRating() + "/5";
        holder.reviewRating.setText(ratingText);
        holder.reviewContent.setText(reviews.get(position).getText());
        holder.reviewDate.setText(reviews.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewName, reviewRating, reviewContent, reviewDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewName = itemView.findViewById(R.id.nameReview);
            reviewRating = itemView.findViewById(R.id.ratingReview);
            reviewContent = itemView.findViewById(R.id.textReview);
            reviewDate = itemView.findViewById(R.id.timeReview);
        }
    }
}
