package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    String info, infosplit[];
    FirebaseFirestore db;
    TextView payment;
    EditText nameoncard, cardnumber, expmonth, expyear, cvc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);

        info = getIntent().getStringExtra("INFO");
        infosplit = info.split(",");
        payment = findViewById(R.id.paymentamount);
        payment.setText("Rs." + infosplit[2]);
        db = FirebaseFirestore.getInstance();
        nameoncard = findViewById(R.id.nameoncard);
        cardnumber = findViewById(R.id.cardnumber);
        expmonth = findViewById(R.id.expmonth);
        expyear = findViewById(R.id.expyear);
        cvc = findViewById(R.id.cvc);
        /*current_user = auth.getCurrentUser().getEmail();
        db = FirebaseFirestore.getInstance();

        final String TAG = "TAG";

        if (infosplit[0].equals("COLLECTOR")) {
            weight = infosplit[3];
            DocumentReference docRef = db.collection("Resources").document(infosplit[1]);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        final DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            reciever = document.getData().get("giverID").toString();
                            material = document.getData().get("Material").toString();

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });


        } else {
            //////////////////////////////////////////////////////////////////
        }*/
    }

    public void backToHome(View view) {


        if (cvc.getText().toString().equals("") || expyear.getText().toString().equals("") || expmonth.getText().toString().equals("") ||
                cardnumber.getText().toString().equals("") || nameoncard.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "ERROR -> Fields cannot be Empty!", Toast.LENGTH_SHORT).show();
        } else {
            Date currentDateandTime = new Date();
            Map<String, Object> payment = new HashMap<>();
            payment.put("PaymentBy", infosplit[6]);
            payment.put("PaymentTo", infosplit[5]);
            payment.put("Amount", infosplit[2]);
            payment.put("Material", infosplit[4]);
            payment.put("Weight", infosplit[3]);
            payment.put("Date", currentDateandTime);
            payment.put("Type", "CREDIT CARD");


            db.collection("Payment").document().set(payment);
            Toast.makeText(getApplicationContext(), "Resource Collected Successfully", Toast.LENGTH_SHORT).show();
            if (infosplit[0].equals("COLLECTOR")) {
                db.collection("Resources").document(infosplit[1])
                        .delete();
                Intent i = new Intent(PaymentActivity.this, HomeActivity.class);
                startActivity(i);
            } else {
                ////////////////////////////////
            }
        }
    }

    public void cancel(View v) {
        if (infosplit[0].equals("COLLECTOR")) {
            Intent i = new Intent(PaymentActivity.this, HomeActivity.class);
            startActivity(i);
        } else {
            /////////////////////////////////////////
        }
    }
}