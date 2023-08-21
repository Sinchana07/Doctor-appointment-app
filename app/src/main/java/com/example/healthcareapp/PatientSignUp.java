package com.example.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthcareapp.databinding.ActivityDoctorSignUpBinding;
import com.example.healthcareapp.databinding.ActivityPatientBinding;
import com.example.healthcareapp.databinding.ActivityPatientSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientSignUp extends AppCompatActivity {
    EditText et_email, et_pass;
    Button signup;
    FirebaseAuth mAuth;
    //ImageView uploaddoctorImage;
    EditText name,age,gender,address, ph_num;
    String imageURL;

    //reference variable for binding class
    ActivityPatientSignUpBinding binding;
    String email, password, fname, age_str, gender_str,addr,num;
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
            Toast.makeText(PatientSignUp.this, "Authentication success",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), PatientActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        et_email = (EditText) findViewById(R.id.emailPatient);
        et_pass = (EditText) findViewById(R.id.passwordPatient);
        signup = (Button) findViewById(R.id.sigupPatient);

        //Storing doctor details in database
        //uploaddoctorImage = findViewById(R.id.profilePhoto);
        name = findViewById(R.id.patientName);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.addressPatient);
        ph_num = findViewById(R.id.number);

        binding.sigupPatient.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //String email, pswd;
                //email = String.valueOf(et_email.getText());
                //pswd = String.valueOf(et_pass.getText());
                fname = binding.patientName.getText().toString();
                email = binding.emailPatient.getText().toString();
                password = binding.passwordPatient.getText().toString();
                age_str = binding.age.getText().toString();
                gender_str = binding.gender.getText().toString();
                addr = binding.addressPatient.getText().toString();
                num = binding.number.getText().toString();

                //firebase auth
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PatientSignUp.this, "signup success.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PatientSignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                if (!fname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !age_str.isEmpty() && !gender_str.isEmpty() && !addr.isEmpty() && !num.isEmpty()) {
                    Patient patient = new Patient(fname, email, password, age_str, gender_str, addr, num);
                    //instance for the db
                    db = FirebaseDatabase.getInstance();
                    //reference for the database, creates root node patients
                    reference = db.getReference("Patients");
                    //adding the patients(child nodes)
                    reference.child(fname).setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.patientName.setText("");
                            binding.emailPatient.setText("");
                            binding.passwordPatient.setText("");
                            binding.addressPatient.setText("");
                            binding.age.setText("");
                            binding.gender.setText("");
                            binding.number.setText("");
                            Toast.makeText(PatientSignUp.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(PatientSignUp.this, "Data fields shouldn't be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
