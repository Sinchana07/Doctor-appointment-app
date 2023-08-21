package com.example.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    ImageButton login;
    EditText et_email, et_pass;
    ImageButton signup;
    FirebaseAuth mAuth;


    //@Override
    /*public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
            Toast.makeText(LoginActivity.this, "Authentication success",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
            startActivity(intent);
            finish();
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        et_email = (EditText) findViewById(R.id.email);
        et_pass = (EditText) findViewById(R.id.pswd);
        signup = (ImageButton) findViewById(R.id.signUp);
        login = (ImageButton)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pswd;
                email = String.valueOf(et_email.getText());
                pswd = String.valueOf(et_pass.getText());

                if(TextUtils.isEmpty((email))){
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty((pswd))){
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, pswd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);

                                            // Authentication successful
                                            String loggedInEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Doctors");
                                            Query query = databaseRef.orderByChild("email").equalTo(loggedInEmail);
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        // Email exists in "doctors" child
                                                        Toast.makeText(LoginActivity.this, "Authentication success (Doctor)", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        // Email does not exist in "Doctors" child
                                                        Toast.makeText(LoginActivity.this, "Authentication success (Patient)", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), PatientActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Error occurred while querying the database
                                                    Toast.makeText(LoginActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            // Authentication failed
                                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
            }
        });
    }
}