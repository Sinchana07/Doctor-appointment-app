package com.example.healthcareapp;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.rxjava3.annotations.NonNull;

public class DoctorActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button logout;
    private FirebaseUser user;
    private ImageButton prof;
    private ImageButton appoint;
    private ImageButton calendar;
    private ImageButton patient;
    private ImageButton patientReq;
    private TextView heading;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logoutBtn);
        prof = findViewById(R.id.profile_imgbtn);
        appoint = findViewById(R.id.appoint_imgbtn);
        patientReq = findViewById(R.id.patientreq_imgbtn);
        heading = findViewById(R.id.welcompat);

        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorProfile.class);
                startActivity(intent);
            }
        });

        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorCalendarActivity.class);
                startActivity(intent);
            }
        });


        patientReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientRequestActivity.class);
                startActivity(intent);
            }
        });



        //retrieving name of the doctor logged in
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            Log.d("TAG", email);
            retrieveUserName(email);
        } else {
            // User is not logged in
            heading.setText("User not logged in.");
        }
    }

    private void retrieveUserName(String email) {
        DatabaseReference usersReference = databaseReference.child("Doctors");
        usersReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userName = userSnapshot.child("name").getValue(String.class);
                        Log.d("TAG", userName);
                        heading.setText("Welcome " + userName);
                    }
                } else {
                    heading.setText("User name not found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                heading.setText("Error occurred while retrieving user name.");
            }
        });
    }
}
