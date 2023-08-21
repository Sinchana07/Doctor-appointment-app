package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class PatientProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private Patient pat;
    private TextView patName;
    private TextView emailID;
    private TextView age;
    private TextView gender;
    private TextView addr;
    private TextView phNum;
    private Button logout;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

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
            Toast.makeText(PatientProfileActivity.this, "User Not logged in", Toast.LENGTH_SHORT).show();
        }

        patName = (TextView) findViewById(R.id.patNameP);
        emailID = (TextView) findViewById(R.id.email);
        age = (TextView) findViewById(R.id.agePat);
        gender = (TextView) findViewById(R.id.gender);
        addr = (TextView) findViewById(R.id.addr);
        phNum = (TextView) findViewById(R.id.phNum);

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
        DatabaseReference usersReference = databaseReference.child("Patients");
        usersReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@io.reactivex.rxjava3.annotations.NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userName = userSnapshot.child("name").getValue(String.class);
                        String age1 = userSnapshot.child("age").getValue(String.class);
                        String gender1 = userSnapshot.child("gender").getValue(String.class);
                        String address = userSnapshot.child("address").getValue(String.class);
                        String phoneNum = userSnapshot.child("phoneNum").getValue(String.class);
                        Log.d("TAG", userName);
                        Log.d("TAG", age1);
                        patName.setText(userName);
                        emailID.setText(email);
                        gender.setText(gender1);
                        addr.setText(address);
                        phNum.setText(phoneNum);
                        age.setText(age1);
                    }
                } else {
                    Toast.makeText(PatientProfileActivity.this,"User name not found.",Toast.LENGTH_LONG).show();
                    //docName.setText("");
                }
            }

            @Override
            public void onCancelled(@io.reactivex.rxjava3.annotations.NonNull DatabaseError databaseError) {
                Toast.makeText(PatientProfileActivity.this,"Error occurred while retrieving user name.",Toast.LENGTH_LONG).show();
            }
        });
    }
}