package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoctorListActivity extends AppCompatActivity {

    private static final String TAG = "DoctorListActivity";

    private DatabaseReference bookingsRef;
    private DatabaseReference doctorsRef;
    private FirebaseAuth auth;

    private LinearLayout doctorCardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Initialize Firebase Database references
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        bookingsRef = database.getReference("Bookings");
        doctorsRef = database.getReference("Doctors");

        // Get reference to LinearLayout for holding doctor card views
        doctorCardContainer = findViewById(R.id.doctorCardContainer);

        // Retrieve the logged-in user's email
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Retrieve the doctor names based on the logged-in user's email
            retrieveDoctorNames(userEmail);
        }
    }

    private void retrieveDoctorNames(final String userEmail) {
        Query query = bookingsRef.orderByChild("userEmail").equalTo(userEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> doctorNamesSet = new HashSet<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String doctorName = snapshot.child("doctorName").getValue(String.class);
                    if (doctorName != null) {
                        doctorNamesSet.add(doctorName);
                    }
                }

                List<String> doctorNamesList = new ArrayList<>(doctorNamesSet);

                // Once you have the list of doctor names, you can display them in the UI
                displayDoctorNames(doctorNamesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Retrieving doctor names cancelled", databaseError.toException());
            }
        });
    }

    private void displayDoctorNames(List<String> doctorNames) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (String name : doctorNames) {
            View doctorCardView = inflater.inflate(R.layout.item_doctor_card, doctorCardContainer, false);
            TextView textViewDoctorName = doctorCardView.findViewById(R.id.textViewDoctorName);
            textViewDoctorName.setText(name);

            doctorCardContainer.addView(doctorCardView);
        }
    }
}
