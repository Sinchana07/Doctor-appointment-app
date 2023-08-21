package com.example.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DoctorCalendarActivity extends AppCompatActivity {
    private DatabaseReference bookingsRef;
    private String currentDoctorEmail;
    private String doctorName;
    private CalendarView calendarView;
    private LinearLayout timeSlotsLayout;
    private Button lastHighlightedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_calendar);

        // Initialize Firebase Database reference to "Bookings" node
        bookingsRef = FirebaseDatabase.getInstance().getReference().child("Bookings");

        // Get the current logged-in doctor's email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentDoctorEmail = user.getEmail();
        } else {
            // Handle the case when the user is not logged in
        }

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        timeSlotsLayout = findViewById(R.id.timeSlotsLayout);

        // Set a listener for date selection on the calendar
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                long selectedDateMillis = calendar.getTimeInMillis();
                String selectedDate = formatDate(selectedDateMillis);
                clearLastHighlightedButton();
                highlightBookedTimeSlots(selectedDate);
            }
        });

        // Retrieve the doctor's name and observe bookings
        retrieveDoctorName();
    }

    private String formatDate(long selectedDateMillis) {
        // Format the selected date to match the format in the database (yyyy-MM-dd)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(selectedDateMillis);
    }

    private void retrieveDoctorName() {
        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctors");
        Query query = doctorsRef.orderByChild("email").equalTo(currentDoctorEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor doctor = snapshot.getValue(Doctor.class);
                    if (doctor != null) {
                        doctorName = doctor.getName();
                        observeBookings();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    private void observeBookings() {
        bookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Booking booking = snapshot.getValue(Booking.class);
                    if (booking != null && booking.getDoctorName().equals(doctorName)) {
                        String selectedDate = formatDate(calendarView.getDate());
                        String bookingDate = booking.getDate();
                        if (bookingDate.equals(selectedDate)) {
                            highlightTimeSlot(booking.getTime());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    private void highlightBookedTimeSlots(String date) {
        // Query the bookings for the selected date and current doctor's name
        Query query = bookingsRef.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Booking booking = snapshot.getValue(Booking.class);
                    if (booking != null && booking.getDoctorName().equals(doctorName)) {
                        highlightTimeSlot(booking.getTime());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    private void highlightTimeSlot(String time) {
        // Clear the last highlighted button if any
        clearLastHighlightedButton();

        // Find the corresponding button and change its background color
        Button button = getTimeSlotButton(time);
        if (button != null) {
            int highlightColor = ContextCompat.getColor(this, R.color.purple_200);
            button.setBackgroundColor(highlightColor);
            lastHighlightedButton = button;
        }
    }

    private Button getTimeSlotButton(String time) {
        switch (time) {
            case "9:00 AM":
                return findViewById(R.id.button9AM);
            case "10:00 AM":
                return findViewById(R.id.button10AM);
            case "11:00 AM":
                return findViewById(R.id.button11AM);
            case "12:00 PM":
                return findViewById(R.id.button12PM);
            case "2:00 PM":
                return findViewById(R.id.button2PM);
            case "3:00 PM":
                return findViewById(R.id.button3PM);
            case "4:00 PM":
                return findViewById(R.id.button4PM);
            case "5:00 PM":
                return findViewById(R.id.button5PM);
            case "7:00 PM":
                return findViewById(R.id.button7PM);
            case "8:00 PM":
                return findViewById(R.id.button8PM);
            case "9:00 PM":
                return findViewById(R.id.button9PM);
            default:
                return null;
        }
    }

    private void clearLastHighlightedButton() {
        if (lastHighlightedButton != null) {
            int originalButtonColor = ContextCompat.getColor(this, R.color.teal_200);
            lastHighlightedButton.setBackgroundColor(originalButtonColor);
            lastHighlightedButton = null;
        }
    }
}
