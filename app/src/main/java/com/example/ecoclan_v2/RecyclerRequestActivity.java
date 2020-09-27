package com.example.ecoclan_v2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecyclerRequestActivity extends AppCompatActivity {

    Button btnDate, btnTime, btnAdd, btnSub;
    TextView date, time;
    EditText weight;
    Spinner spinner;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_request);

        btnDate = findViewById(R.id.button9);
        btnTime = findViewById(R.id.button12);
        btnAdd = findViewById(R.id.button1);
        btnSub = findViewById(R.id.button3);

        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextDate3);
        weight = findViewById(R.id.editTextTextPersonName1);


    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerHomeActivity.class);
        startActivity(intent);
    }

    public void pickDate(View view){
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                date.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
            }
        }, day, month, year);
        datePickerDialog.show();
    }

    public void pickTime (View view){
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                time.setText(mHour + ":" + mMinute);
            }
        }, hour,minute,false);
        timePickerDialog.show();

    }

    public void add(View view){
        float num = Float.parseFloat(weight.getText().toString());
        weight.setText(String.valueOf(num+1));
    }

    public void sub(View view){
        float num = Float.parseFloat(weight.getText().toString());
        weight.setText(String.valueOf(num-1));
    }



}