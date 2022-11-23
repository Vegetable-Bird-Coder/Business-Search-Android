package com.example.businesssearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businesssearch.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Stack;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookingsActivity extends AppCompatActivity {
    private ArrayList<BookingInfo> bookingsInfo;
    private RecyclerView bookingsRecyclerView;
    private BookingsRecViewAdapter bookingsRecViewAdapter;
    private ConstraintLayout bookingsInfoLayout;
    private ImageView backIcon;
    private TextView txtNoBooking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        bookingsInfo = Bookings.getInstance(this).getAllBookings();
        txtNoBooking = findViewById(R.id.txtNoBooking);



        bookingsRecyclerView = findViewById(R.id.bookingsRecView);
        bookingsRecViewAdapter = new BookingsRecViewAdapter();
        bookingsRecyclerView.setAdapter(bookingsRecViewAdapter);
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingsRecViewAdapter.setBookingsInfo(bookingsInfo);
        bookingsInfoLayout = findViewById(R.id.bookingsInfoLayout);
        backIcon = findViewById(R.id.backBookingsBar);

        if (bookingsInfo.size() > 0) {
            bookingsRecyclerView.setVisibility(View.VISIBLE);
            txtNoBooking.setVisibility(View.GONE);
        } else {
            bookingsRecyclerView.setVisibility(View.GONE);
            txtNoBooking.setVisibility(View.VISIBLE);
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookingsActivity.this.finish();
            }
        });



        new ItemTouchHelper(simpleCallback).attachToRecyclerView(bookingsRecyclerView);
    }

    BookingInfo bookingInfo;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            bookingInfo = bookingsInfo.get(position);
            System.out.println(bookingInfo);
            Bookings.getInstance(BookingsActivity.this).removeBooking(bookingInfo);
            bookingsInfo = Bookings.getInstance(BookingsActivity.this).getAllBookings();
            bookingsRecViewAdapter.setBookingsInfo(bookingsInfo);
            if (bookingsInfo.size() == 0) {
                bookingsRecyclerView.setVisibility(View.GONE);
                txtNoBooking.setVisibility(View.VISIBLE);
            }

            Snackbar.make(bookingsInfoLayout, "Removing Existing Reservation", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(getResources().getColor(R.color.red_500))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}