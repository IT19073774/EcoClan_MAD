package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onBtnUser(View v) {
        startActivity(new Intent(getApplicationContext(), LoginUser.class));
    }

    public void onBtnEmployee(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
