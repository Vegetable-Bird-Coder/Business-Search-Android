package com.example.businesssearch;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Bookings {
    private static Bookings instance;
    private SharedPreferences sharedPreferences;

    private Bookings(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);
        if (getAllBookings() == null) {
            initData();
        }
    }

    private void initData() {
        ArrayList<BookingInfo> bookingsInfo = new ArrayList<>();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString("bookings", gson.toJson(bookingsInfo));
        editor.commit();
    }

    public static synchronized Bookings getInstance(Context context) {
        if (instance == null) {
            instance = new Bookings(context);
        }
        return instance;
    }

    public ArrayList<BookingInfo> getAllBookings() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BookingInfo>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString("bookings", null), type);
    }

    public boolean addBooking(BookingInfo bookingInfo) {
        ArrayList<BookingInfo> bookingsInfo = getAllBookings();
        if (bookingsInfo != null) {
            for (BookingInfo bookingInfo1 : bookingsInfo) {
                if (bookingInfo1.getId().equals(bookingInfo.getId())) {
                    bookingsInfo.remove(bookingInfo1);
                    break;
                }
            }
            if (bookingsInfo.add(bookingInfo)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("bookings");
                editor.putString("bookings", gson.toJson(bookingsInfo));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeBooking(BookingInfo bookingInfo) {
        ArrayList<BookingInfo> bookingsInfo = getAllBookings();
        if (bookingsInfo != null) {
            for (BookingInfo b : bookingsInfo) {
                if (b.getId().equals(bookingInfo.getId())) {
                    if (bookingsInfo.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("bookings");
                        editor.putString("bookings", gson.toJson(bookingsInfo));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
