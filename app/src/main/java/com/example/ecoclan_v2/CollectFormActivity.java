package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CollectFormActivity extends AppCompatActivity {

    EditText weightet;
    String w, resID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collect_form);
        resID = getIntent().getStringExtra("resID");
        weightet = findViewById(R.id.weightactual);

    }

    public void generateInvoice(View v) {

        w =  weightet.getText().toString();
        if (w.equals("")) {
            Toast.makeText(getApplicationContext(), "WARNING: Actual Weight Data Entry Required!", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(CollectFormActivity.this, InvoiceActivity.class);
            i.putExtra("INFO", "COLLECTOR," + resID + "," + w);
            startActivity(i);
        }
    }

    public void cancel(View v) {

        Intent i = new Intent(CollectFormActivity.this,HomeActivity.class);
        startActivity(i);
    }
}
