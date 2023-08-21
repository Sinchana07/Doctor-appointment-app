package com.example.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

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
import java.util.Locale;

public class PatientCalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Button button9AM, button10AM, button11AM, button12PM, button2PM, button3PM, button4PM, button5PM, button7PM, button8PM, button9PM;

    private String selectedDate;
    private String selectedTime;
    private String currentUserEmail;
    private String doctorName;

    private DatabaseReference bookingsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_calendar);

        // Initialize Firebase
        bookingsRef = FirebaseDatabase.getInstance().getReference("Bookings");

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        button9AM = findViewById(R.id.button9AM);
        button10AM = findViewById(R.id.button10AM);
        button11AM = findViewById(R.id.button11AM);
        button12PM = findViewById(R.id.button12PM);
        button2PM = findViewById(R.id.button2PM);
        button3PM = findViewById(R.id.button3PM);
        button4PM = findViewById(R.id.button4PM);
        button5PM = findViewById(R.id.button5PM);
        button7PM = findViewById(R.id.button7PM);
        button8PM = findViewById(R.id.button8PM);
        button9PM = findViewById(R.id.button9PM);

        // Set initial selected date to the current date
        selectedDate = getCurrentDate();

        // Set initial selected time to an empty string
        selectedTime = "";

        // Get the current user email (You should replace this with your own logic to fetch the current user email)
        currentUserEmail = getCurrentUserEmail();

        // Get the stored doctor name (You should replace this with your own logic to fetch the stored doctor name)
        doctorName = getStoredDoctorName();

        // Set OnClickListener for the time slot buttons
        button9AM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "9:00 AM";
                bookTimeSlot();
            }
        });

        button10AM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "10:00 AM";
                bookTimeSlot();
            }
        });

        button11AM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "11:00 AM";
                bookTimeSlot();
            }
        });

        button12PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "12:00 PM";
                bookTimeSlot();
            }
        });

        button2PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "2:00 PM";
                bookTimeSlot();
            }
        });

        button3PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "3:00 PM";
                bookTimeSlot();
            }
        });

        button4PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "4:00 PM";
                bookTimeSlot();
            }
        });

        button5PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "5:00 PM";
                bookTimeSlot();
            }
        });

        button7PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "7:00 PM";
                bookTimeSlot();
            }
        });

        button8PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "8:00 PM";
                bookTimeSlot();
            }
        });

        button9PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = "9:00 PM";
                bookTimeSlot();
            }
        });

        // Set OnDateChangeListener for the CalendarView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Get the selected date
                selectedDate = formatDate(year, month, dayOfMonth);

                // Update the availability of time slots based on the bookings
                updateTimeSlotAvailability();
            }
        });

        // Update the availability of time slots based on the bookings
        updateTimeSlotAvailability();
    }



// ...

    private void updateTimeSlotAvailability() {
        // Clear the background color of all time slot buttons
        button9AM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button10AM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button11AM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button12PM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button2PM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button3PM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button4PM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button5PM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button7PM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button8PM.setBackgroundColor(getResources().getColor(R.color.teal_200));
        button9PM.setBackgroundColor(getResources().getColor(R.color.teal_200));

        // Retrieve the bookings for the selected date from the database
        Query dateBookingsQuery = bookingsRef.orderByChild("date").equalTo(selectedDate);
        dateBookingsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Booking booking = snapshot.getValue(Booking.class);
                    if (booking != null && booking.getDoctorName().equals(doctorName)) {
                        // Get the time of the booking
                        String bookedTime = booking.getTime();

                        // Update the availability and color of the corresponding time slot button
                        switch (bookedTime) {
                            case "9:00 AM":
                                button9AM.setBackgroundColor(Color.RED);
                                button9AM.setEnabled(false);
                                break;
                            case "10:00 AM":
                                button10AM.setBackgroundColor(Color.RED);
                                button10AM.setEnabled(false);
                                break;
                            case "11:00 AM":
                                button11AM.setBackgroundColor(Color.RED);
                                button11AM.setEnabled(false);
                                break;
                            case "12:00 PM":
                                button12PM.setBackgroundColor(Color.RED);
                                button12PM.setEnabled(false);
                                break;
                            case "2:00 PM":
                                button2PM.setBackgroundColor(Color.RED);
                                button2PM.setEnabled(false);
                                break;
                            case "3:00 PM":
                                button3PM.setBackgroundColor(Color.RED);
                                button3PM.setEnabled(false);
                                break;
                            case "4:00 PM":
                                button4PM.setBackgroundColor(Color.RED);
                                button4PM.setEnabled(false);
                                break;
                            case "5:00 PM":
                                button5PM.setBackgroundColor(Color.RED);
                                button5PM.setEnabled(false);
                                break;
                            case "7:00 PM":
                                button7PM.setBackgroundColor(Color.RED);
                                button7PM.setEnabled(false);
                                break;
                            case "8:00 PM":
                                button8PM.setBackgroundColor(Color.RED);
                                button8PM.setEnabled(false);
                                break;
                            case "9:00 PM":
                                button9PM.setBackgroundColor(Color.RED);
                                button9PM.setEnabled(false);
                                break;
                        }
                    }
                }
            }




    @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(PatientCalendarActivity.this, "Database error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void bookTimeSlot() {
        // Check if a time slot is selected
        if (!selectedTime.isEmpty()) {
            // Create a new booking object
            Booking booking = new Booking(selectedDate, selectedTime, doctorName, currentUserEmail);

            // Store the booking in the database
            String bookingId = bookingsRef.push().getKey();
            bookingsRef.child(bookingId).setValue(booking);

            // Show a success message
            Toast.makeText(getApplicationContext(), "Booking successful!", Toast.LENGTH_SHORT).show();

            // Refresh the time slot availability
            updateTimeSlotAvailability();
        } else {
            // Show an error message
            Toast.makeText(getApplicationContext(), "Please select a time slot", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private String getCurrentUserEmail() {
        // Implement your logic to get the current user email
        // For example: return FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // Here, we're returning a dummy email for demonstration purposes
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // The user is logged in
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            // Return the name if available, otherwise return the email
            return name != null ? name : email;
        } else {
            // The user is not logged in
            return "Unknown User";
        }
    }

    private String getStoredDoctorName() {
        // Implement your logic to fetch the stored doctor name
        // Here, we're returning a dummy name for demonstration purposes
        // Get the doctor name from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("doctorName")) {
            return intent.getStringExtra("doctorName");
        }
        return "";
    }
}
