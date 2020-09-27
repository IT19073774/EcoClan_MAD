package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InvoiceActivity extends AppCompatActivity {

    String info, infosplit[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_invoice);

        info = getIntent().getStringExtra("INFO");
        infosplit = info.split(",");


    }

    public void payNow(View view) {
        Intent i = new Intent(InvoiceActivity.this,PaymentActivity.class);
        startActivity(i);
    }
}