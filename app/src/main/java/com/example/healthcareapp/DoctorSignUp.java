package com.example.healthcareapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthcareapp.databinding.ActivityDoctorBinding;
import com.example.healthcareapp.databinding.ActivityDoctorSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorSignUp extends AppCompatActivity {

    EditText et_email, et_pass;
    Button signup;
    FirebaseAuth mAuth;
    EditText name,degree,experience,clinicAddress, clinicTimings;

    //reference variable for binding class
    ActivityDoctorSignUpBinding binding;
    String email, password, fname, deg, exp,addr,timings;
    FirebaseDatabase db;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
            Toast.makeText(DoctorSignUp.this, "Authentication success",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        et_email = (EditText) findViewById(R.id.emailDoc);
        et_pass = (EditText) findViewById(R.id.passwordDoc);
        signup = (Button) findViewById(R.id.sigupDoc);

        //Storing doctor details in database

        name = findViewById(R.id.docName);
        degree = findViewById(R.id.degree);
        experience = findViewById(R.id.experience);
        clinicAddress = findViewById(R.id.address);
        clinicTimings = findViewById(R.id.time);

        binding.sigupDoc.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //String email, pswd;
                //email = String.valueOf(et_email.getText());
                //pswd = String.valueOf(et_pass.getText());
                fname = binding.docName.getText().toString();
                email = binding.emailDoc.getText().toString();
                password = binding.passwordDoc.getText().toString();
                deg = binding.degree.getText().toString();
                exp = binding.experience.getText().toString();
                addr = binding.address.getText().toString();
                timings = binding.time.getText().toString();

                //firebase auth
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DoctorSignUp.this, "signup success.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DoctorSignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                if (!fname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !deg.isEmpty() && !exp.isEmpty() && !addr.isEmpty() && !timings.isEmpty()) {
                    DoctorClass doctors = new DoctorClass(fname, email, password, deg, exp, addr, timings);
                    //instance for the db
                    db = FirebaseDatabase.getInstance();
                    //reference for the database, creates root node Doctors
                    reference = db.getReference("Doctors");
                    //adding the doctors(child nodes)
                    reference.child(fname).setValue(doctors).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.docName.setText("");
                            binding.emailDoc.setText("");
                            binding.passwordDoc.setText("");
                            binding.address.setText("");
                            binding.experience.setText("");
                            binding.address.setText("");
                            binding.time.setText("");
                            Toast.makeText(DoctorSignUp.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(DoctorSignUp.this, "Data fields shouldn't be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
