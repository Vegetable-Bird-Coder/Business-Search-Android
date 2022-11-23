package com.example.businesssearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingsRecViewAdapter extends RecyclerView.Adapter<BookingsRecViewAdapter.ViewHolder> {
    private ArrayList<BookingInfo> bookingsInfo;

    public void setBookingsInfo(ArrayList<BookingInfo> bookingsInfo) {
        this.bookingsInfo = bookingsInfo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_booking, parent, false);
        return new BookingsRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.index.setText(String.valueOf(position + 1));
        holder.name.setText(bookingsInfo.get(position).getName());
        holder.date.setText(bookingsInfo.get(position).getDate());
        holder.time.setText(bookingsInfo.get(position).getTime());
        holder.email.setText(bookingsInfo.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return bookingsInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView index, name, date, time, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.indexBookingInfo);
            name = itemView.findViewById(R.id.nameBookingInfo);
            date = itemView.findViewById(R.id.dateBookingInfo);
            time = itemView.findViewById(R.id.timeBookingInfo);
            email = itemView.findViewById(R.id.emailBookingInfo);

        }
    }
}
