package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RecyclerRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_request);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerHomeActivity.class);
        startActivity(intent);
    }

}