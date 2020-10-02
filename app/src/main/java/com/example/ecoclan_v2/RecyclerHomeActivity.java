package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecyclerHomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    String current_user;
    TextView recyclerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerEmail = findViewById(R.id.LoginEmail);

        current_user = auth.getCurrentUser().getEmail();

        recyclerEmail.setText("[" + current_user + "]");

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

    public void signOut (View v) {
        auth.signOut();
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
    }
}