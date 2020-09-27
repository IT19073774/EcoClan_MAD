package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResourceDetailActivity extends AppCompatActivity {
    String[] titleSplit;
    FirebaseFirestore db;
    TextView resid, resname, senemail, senaddress, sencontact, reslat, reslng, restype, resesti, colrate, cost;
    String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resource_detail);
        db = FirebaseFirestore.getInstance();
        String title = getIntent().getStringExtra("resID");

        titleSplit = title.split(" :: ");
        resid = findViewById(R.id.resid);
        resname = findViewById(R.id.resname);
        senemail = findViewById(R.id.senemail);

        senaddress = findViewById(R.id.senaddress);
        sencontact = findViewById(R.id.sencontact);

        reslat = findViewById(R.id.reslat);
        reslng = findViewById(R.id.reslng);
        restype = findViewById(R.id.restype);
        resesti = findViewById(R.id.resesti);

        colrate = findViewById(R.id.colrate);
        cost = findViewById(R.id.cost);

        DocumentReference docRef = db.collection("Resources").document(titleSplit[0]);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    final DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        resid.setText(document.getData().get("resourceID").toString());
                        resname.setText(document.getData().get("resource").toString());
                        senemail.setText(document.getData().get("giverID").toString());
                        reslat.setText(document.getData().get("Latitude").toString());
                        reslng.setText(document.getData().get("Longitude").toString());
                        restype.setText(document.getData().get("Material").toString());
                        resesti.setText(document.getData().get("EWeight").toString() + ".0 Kg");

                        DocumentReference docRef = db.collection("CollectorRates").document("material");
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        colrate.setText("Rs. " + document2.getData().get(document.getData().get("Material").toString()).toString());
                                        Double col = Double.parseDouble(document2.getData().get(document.getData().get("Material").toString()).toString());
                                        Double eweight = Double.parseDouble(document.getData().get("EWeight").toString());

                                        Double costcal = col * eweight;
                                        cost.setText("Rs. " + costcal.toString());

                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                        DocumentReference docRef2 = db.collection("customers").document(document.getData().get("UserID").toString());
                        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document3 = task.getResult();
                                    if (document3.exists()) {
                                        senaddress.setText(document3.getData().get("UAddress").toString());
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

    public void CollectForm(View v) {
        Intent i = new Intent(ResourceDetailActivity.this,CollectFormActivity.class);
        i.putExtra("resID", titleSplit[0]);
        startActivity(i);
    }

    public void back(View v) {
        Intent i = new Intent(ResourceDetailActivity.this,HomeActivity.class);
        startActivity(i);
    }
}