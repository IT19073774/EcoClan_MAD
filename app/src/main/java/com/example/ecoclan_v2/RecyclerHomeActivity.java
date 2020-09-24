package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RecyclerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_home);
    }

    public void goToRequestPage(View view){
        Intent intent = new Intent(this, RecyclerRequestActivity.class);
        startActivity(intent);
    }
    public void goToReceivePage(View view){
        Intent intent = new Intent(this, RecyclerReceiveActivity.class);
        startActivity(intent);
    }
    public void goToSearchPage(View view){
        Intent intent = new Intent(this, RecyclerSearchActivity.class);
        startActivity(intent);
    }
    public void goToProfilePage(View view){
        Intent intent = new Intent(this, RecyclerProfileActivity.class);
        startActivity(intent);
    }
}