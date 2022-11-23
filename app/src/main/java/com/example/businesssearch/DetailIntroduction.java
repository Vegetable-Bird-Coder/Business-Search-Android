package com.example.businesssearch;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;


public class DetailIntroduction extends Fragment {

    public static final String TITLE = "BUSINESS DETAILS";
    private TextView addressIntro, priceIntro, phoneIntro, statusIntro, categoryIntro, yelpIntro;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;

    private Button bookingBtn;

    private TextView nameDialog;
    private EditText emailDialog, dateDialog, timeDialog;
    private Button cancelDialog, submitDialog;
    private AlertDialog dialog;
    private RelativeLayout dateBox, timeBox;

    private int mYear, mMonth, mDay, mHour, mMinute;



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
        addressIntro.setText(DetailInfoActivity.location);
        priceIntro.setText(DetailInfoActivity.price);
        phoneIntro.setText(DetailInfoActivity.phone);
        yelpIntro = view.findViewById(R.id.yelpIntro);
        String dynamicUrl = DetailInfoActivity.yelpUrl;
        String linkedText = String.format("<a href=\"%s\">Business Link</a> ", dynamicUrl);
        yelpIntro.setText(Html.fromHtml(linkedText));
        yelpIntro.setMovementMethod(LinkMovementMethod.getInstance());

        if (DetailInfoActivity.status) {
            statusIntro.setTextColor(getResources().getColor(R.color.green_500));
            statusIntro.setText("Open Now");
        } else {
            statusIntro.setTextColor(getResources().getColor(R.color.red_500));
            statusIntro.setText("Closed");
        }
        categoryIntro.setText(DetailInfoActivity.category);
//        yelpIntro.setText(DetailInfoActivity.yelpUrl);

        ArrayList<String> photoSource = new ArrayList<>();
        photoSource.addAll(Arrays.asList(DetailInfoActivity.photos));

        sliderView = view.findViewById(R.id.detailPhotosSlider);
        sliderAdapter = new SliderAdapter(DetailInfoActivity.context);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderAdapter.setPhotos(photoSource);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        bookingBtn = view.findViewById(R.id.btnBooking);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.booking_dialog, null);
        builder.setView(dialogView);
        dialog = builder.create();


        nameDialog = dialogView.findViewById(R.id.resNameDialog);
        emailDialog = dialogView.findViewById(R.id.emailDialog);
        dateDialog = dialogView.findViewById(R.id.dateDialog);
        timeDialog = dialogView.findViewById(R.id.timeDialog);
        cancelDialog = dialogView.findViewById(R.id.cancelDialog);
        submitDialog = dialogView.findViewById(R.id.submitDialog);
        dateBox = dialogView.findViewById(R.id.dateBox);
        timeBox = dialogView.findViewById(R.id.timeBox);

        nameDialog.setText(DetailInfoActivity.name);




        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                dateDialog.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });

        timeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String time = hourOfDay + ":" + minute;
                                timeDialog.setText(time);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog.setText("");
                dateDialog.setText("");
                timeDialog.setText("");
                dialog.dismiss();
            }
        });

        submitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexPattern = "^(.+)@(\\S+)$";
                String email = emailDialog.getText().toString();
                String date = dateDialog.getText().toString();
                String time = timeDialog.getText().toString();
                int hour = 0;
                int minutes = 0;
                if (time.length() > 0) {
                    hour = Integer.parseInt(time.split(":")[0]);
                    minutes = Integer.parseInt(time.split(":")[1]);
                }
                if (email.length() == 0 || !Pattern.compile(regexPattern).matcher(email).matches()) {
                    Toast.makeText(getContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                } else if (date.length() == 0) {
                    Toast.makeText(getContext(), "Invalid Appointment Date", Toast.LENGTH_SHORT).show();
                } else if (time.length() == 0 || hour < 10 || hour > 17 || (hour == 17 && minutes > 0)) {
                    Toast.makeText(getContext(), "Time should be between 10Am and 5PM", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Reservation Booked", Toast.LENGTH_SHORT).show();
                    BookingInfo bookingInfo = new BookingInfo(DetailInfoActivity.id, DetailInfoActivity.name, date, time, email);
                    Bookings.getInstance(getContext()).addBooking(bookingInfo);
                    for (BookingInfo b : Bookings.getInstance(getContext()).getAllBookings()) {
                        System.out.println(b);
                    }
                }
                emailDialog.setText("");
                dateDialog.setText("");
                timeDialog.setText("");
                dialog.dismiss();
            }
        });


        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

}