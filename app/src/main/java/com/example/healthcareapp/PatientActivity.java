package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

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

public class PatientActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button logout;
    private FirebaseUser user;
    private ImageButton profileButton;
    private ImageButton searchButton;
    private ImageButton doctorButton;
    private ImageButton medicalFolderButton;
    private TextView heading;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        profileButton = findViewById(R.id.profile_imgbtn);
        searchButton = findViewById(R.id.search_imgbtn);
        doctorButton = findViewById(R.id.doctor_imgbtn);
        medicalFolderButton = findViewById(R.id.patient_req_bt);
        heading = findViewById(R.id.heading);

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientProfileActivity.class);
                startActivity(intent);
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorListActivity.class);
                startActivity(intent);
            }
        });

        medicalFolderButton.setOnClickListener(new View.OnClickListener() {
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
        DatabaseReference usersReference = databaseReference.child("Patients");
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
