package com.example.ecoclan_v2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerRequestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button btnDate, btnTime, btnAdd, btnSub;
    TextView date, time, cost;
    EditText weight;
    String category;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Spinner spin;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String current_user = auth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_request);

        btnDate = findViewById(R.id.button9);
        btnTime = findViewById(R.id.button12);
        btnAdd = findViewById(R.id.button1);
        btnSub = findViewById(R.id.button3);

        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextDate3);
        cost = findViewById(R.id.editTextDate4);
        weight = findViewById(R.id.editTextTextPersonName1);

        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        List<String> cat = new ArrayList<>();
        cat.add("Paper");
        cat.add("Glass");
        cat.add("Plastic");
        cat.add("Cloth");
        cat.add("Metal");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = spin.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void request(View view){

        final String cat, wei, da,ti;
        wei = weight.getText().toString();
        da = date.getText().toString();
        ti = time.getText().toString();

        DocumentReference dRef = db.collection("RecyclerCounter").document("Count");
        dRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        final Integer Count =  Integer.parseInt(doc.getData().get("Count").toString());

                        Map<String, Object> request = new HashMap<>();
                        request.put("Category", category);
                        request.put("Weight", wei);
                        request.put("ExpectedDate ", da);
                        request.put("ExpectedTime", ti);
                        request.put("RequesterID ", current_user);
                        request.put("Status ", "PENDING");

                        final Map<String, Object> count = new HashMap<>();
                        count.put("Count", Count+1);
                        count.put("GiverID", "RECYCLER");

                        db.collection("RecyclerRequests").document("RES0" + Count).set(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    db.collection("RecyclerCounter").document("Count").set(count).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Request Sent", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), RecyclerHomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    }
                }
            }
        });

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


    public void getEstimate(View view){
        final Double num = Double.parseDouble(weight.getText().toString());

        DocumentReference dRef = db.collection("RecyclerRates").document("lmeObPqMsxVzO9XrPyRe");
        dRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {

                        Double Count =  Double.parseDouble(doc.getData().get(category).toString());

                        Double cos = num * Count;

                        cost.setText(String.valueOf(cos));

                    }
                }
            }
        });

    }

}