package com.example.android.medipoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddApointment extends AppCompatActivity {
    Spinner genderSpinner;
    Spinner timeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apointment);
        setTitle("Make Appointment");
        genderSpinner=(Spinner) findViewById(R.id.gender_spinner);
        setupGenderSpinner(genderSpinner);
        timeSpinner=(Spinner) findViewById(R.id.spinner2);
        setupTimeSpinner(timeSpinner);

    }

    private void setupTimeSpinner(Spinner timeSpinner) {
        ArrayAdapter<CharSequence> time_spinner= ArrayAdapter.createFromResource(this,R.array.time_slot,android.R.layout.simple_spinner_item);
        time_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(time_spinner);
    }

    private void setupGenderSpinner(Spinner genderSpinner) {
        ArrayAdapter<CharSequence> gen_spinner= ArrayAdapter.createFromResource(this,R.array.gender_array_spinner,android.R.layout.simple_spinner_item);
        gen_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(gen_spinner);
    }
}