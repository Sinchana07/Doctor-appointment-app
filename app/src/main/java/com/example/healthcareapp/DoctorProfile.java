package com.example.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    //private String userID;
    private DoctorClass doc;
    private TextView docName;
    private TextView emailID;
    private TextView deg;
    private TextView exp;
    private TextView addr;
    private TextView time;
    private Button logout;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Doctors");
        //userID = user.getUid();

        //retrieving name of the doctor logged in
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //retrieving the user data based on the email of the user
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            Log.d("TAG", email);
            retrieveUserData(email);
        } else {
            // User is not logged in
            Toast.makeText(DoctorProfile.this, "User Not logged in", Toast.LENGTH_SHORT).show();
        }

        docName = (TextView) findViewById(R.id.docNameP);
        emailID = (TextView) findViewById(R.id.email);
        deg = (TextView) findViewById(R.id.degree);
        exp = (TextView) findViewById(R.id.experience);
        addr = (TextView) findViewById(R.id.clinicAddr);
        time = (TextView) findViewById(R.id.clinicTimings);
        logout = (Button) findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void retrieveUserData(String email) {
        DatabaseReference usersReference = databaseReference.child("Doctors");
        usersReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@io.reactivex.rxjava3.annotations.NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userName = userSnapshot.child("name").getValue(String.class);
                        String degree = userSnapshot.child("degree").getValue(String.class);
                        String experience = userSnapshot.child("experience").getValue(String.class);
                        String address = userSnapshot.child("clinicAddress").getValue(String.class);
                        String timings = userSnapshot.child("clinicTimings").getValue(String.class);
                        Log.d("TAG", userName);
                        Log.d("TAG", degree);
                        docName.setText(userName);
                        emailID.setText(email);
                        deg.setText(degree);
                        exp.setText(experience);
                        addr.setText(address);
                        time.setText(timings);
                    }
                } else {
                    Toast.makeText(DoctorProfile.this,"User name not found.",Toast.LENGTH_LONG).show();
                    //docName.setText("");
                }
            }

            @Override
            public void onCancelled(@io.reactivex.rxjava3.annotations.NonNull DatabaseError databaseError) {
                Toast.makeText(DoctorProfile.this,"Error occurred while retrieving user name.",Toast.LENGTH_LONG).show();
            }
        });
    }
}