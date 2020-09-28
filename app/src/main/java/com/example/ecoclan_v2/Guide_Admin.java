package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Guide_Admin extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView title_guide , message_guide;
    Button add_Guide;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide__admin);

        title_guide =  findViewById(R.id.title_guide);
        message_guide =  findViewById(R.id.message_guide);
        add_Guide =  findViewById(R.id.add_Guide);

        fStore = FirebaseFirestore.getInstance();


        add_Guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> guide = new HashMap<>();
                guide.put("Title", title_guide.getText().toString());
                guide.put("msg", message_guide.getText().toString());

                fStore.collection("guide").document().set(guide).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "data saved", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"added!");
                            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                        }
                    }
                });
            }
        });





    }
}