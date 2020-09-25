package com.example.ecoclan_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecyclerUpdateUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_update_user);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerProfileActivity.class);
        startActivity(intent);
    }
}