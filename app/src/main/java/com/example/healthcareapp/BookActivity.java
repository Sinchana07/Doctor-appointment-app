package com.example.healthcareapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class BookActivity extends AppCompatActivity {

    private Spinner spinnerBooking;
    private Button btnContinue;
    private String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        if (intent.hasExtra("doctorName")) {
            doctorName = intent.getStringExtra("doctorName");
        }

        spinnerBooking = findViewById(R.id.spinnerBooking);
        btnContinue = findViewById(R.id.btnContinue);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.appointment_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerBooking.setAdapter(adapter);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle continue button click event
                String selectedOption = spinnerBooking.getSelectedItem().toString();
                if (selectedOption.equals("Consultation") || selectedOption.equals("Hospitalization")) {
                    // Redirect to DoctorCalendarActivity and pass the doctorName
                    Intent intent = new Intent(BookActivity.this, PatientCalendarActivity.class);
                    intent.putExtra("doctorName", doctorName);
                    startActivity(intent);
                }
            }
        });
    }
}
