package com.example.ecoclan_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {

    String info, infosplit[];
    TextView senemail, sencontact, resmaterial, rescolrate, resweight, total;
    FirebaseFirestore db;
    RadioButton radioButton;
    RadioGroup radioGroup;
    FirebaseAuth auth;
    String current_user, reciever, amount,  material,  weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_invoice);
        auth = FirebaseAuth.getInstance();
        current_user = auth.getCurrentUser().getEmail();
        info = getIntent().getStringExtra("INFO");
        infosplit = info.split(",");
        db = FirebaseFirestore.getInstance();
        senemail = findViewById(R.id.cusemail);
        sencontact = findViewById(R.id.cuscontact);
        resmaterial = findViewById(R.id.resmaterial);
        rescolrate = findViewById(R.id.rescolrate);
        resweight = findViewById(R.id.resweight);
        total = findViewById(R.id.total);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        final String TAG = "TAG";


        if (infosplit[0].equals("COLLECTOR")) {
            resweight.setText(infosplit[2]);
            weight = infosplit[2];
            DocumentReference docRef = db.collection("Resources").document(infosplit[1]);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        final DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            reciever = document.getData().get("giverID").toString();
                            senemail.setText(reciever);
                            resmaterial.setText(document.getData().get("Material").toString());
                            material = document.getData().get("Material").toString();
                            DocumentReference docRef = db.collection("CollectorRates").document("material");
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document2 = task.getResult();
                                        if (document2.exists()) {
                                            rescolrate.setText("Rs. " + document2.getData().get(document.getData().get("Material").toString()).toString());
                                            Double col = Double.parseDouble(document2.getData().get(document.getData().get("Material").toString()).toString());
                                            Double eweight = Double.parseDouble(infosplit[2]);

                                            Double costcal = col * eweight;
                                            amount = costcal.toString();
                                            total.setText("Rs. " + costcal.toString());

                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });

                            DocumentReference docRef3 = db.collection("customers").document(document.getData().get("UserID").toString());
                            docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document3 = task.getResult();
                                        if (document3.exists()) {
                                            sencontact.setText(document3.getData().get("UPhone").toString());
                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });


        }

        else {
            DocumentReference docRef = db.collection("RecyclerRequests").document(infosplit[1]);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        final DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            senemail.setText(document.getData().get("Collector").toString());
                            material = document.getData().get("Category").toString();
                            resmaterial.setText(material);


                            DocumentReference docRef = db.collection("RecyclerRates").document("lmeObPqMsxVzO9XrPyRe");
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document2 = task.getResult();
                                        if (document2.exists()) {
                                            rescolrate.setText("Rs. " + document2.getData().get(material).toString());
                                            Double col = Double.parseDouble(document2.getData().get(material).toString());

                                            Double weigt = Double.parseDouble(document.getData().get("Weight").toString());

                                            resweight.setText(String.valueOf(weigt));
                                            Double costcal = col * weigt;
                                            amount = costcal.toString();
                                            total.setText("Rs. " + costcal.toString());

                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });

                            DocumentReference docRef3 = db.collection("Users").document(document.getData().get("Collector").toString());
                            docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document3 = task.getResult();
                                        if (document3.exists()) {
                                            sencontact.setText(document3.getData().get("Contact").toString());
                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }



    }

    public void payNow(View view) {
        final String TAG = "-----------------------";
        int radid= radioGroup.getCheckedRadioButtonId();
        radioButton= (RadioButton) findViewById(radid);
        if (radioButton.getText().equals("Credit Card")) {
            Intent i = new Intent(InvoiceActivity.this,PaymentActivity.class);
            i.putExtra("INFO", infosplit[0]+"," + infosplit[1] + "," + amount + "," + infosplit[2]+ "," + material+ "," + reciever+ "," + current_user);
            startActivity(i);

        } else {
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            //String currentDateandTime = sdf.format(new Date());
            Date currentDateandTime = new Date();

            Map<String, Object> payment = new HashMap<>();
            payment.put("PaymentBy", current_user);
            payment.put("PaymentTo", reciever);
            payment.put("Amount", amount);
            payment.put("Material", material);
            payment.put("Weight", weight);
            payment.put("Date", currentDateandTime);
            payment.put("Type", "CASH");


            db.collection("Payment").document().set(payment);
            Toast.makeText(getApplicationContext(), "Resource Collected Successfully", Toast.LENGTH_SHORT).show();
            if (infosplit[0].equals("COLLECTOR")) {
                db.collection("Resources").document(infosplit[1])
                        .delete();
                Intent i = new Intent(InvoiceActivity.this, HomeActivity.class);
                startActivity(i);
            } else {
                ////////////////////////////////
            }
        }

    }

    public void cancel(View v) {
        if (infosplit[0].equals("COLLECTOR")) {
            Intent i = new Intent(InvoiceActivity.this, HomeActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(InvoiceActivity.this, RecyclerReceiveActivity.class);
            startActivity(i);
        }
    }
}