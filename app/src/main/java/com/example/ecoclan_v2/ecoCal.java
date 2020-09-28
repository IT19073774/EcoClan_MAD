package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ecoCal extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView icon4,icon6;
    FirebaseFirestore fStore;
    Double  Plastic,Cloth,Metal,Glass,Paper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_user);
        setContentView(R.layout.activity_eco_cal);

        icon4 = findViewById(R.id.icon4);
        icon6 = findViewById(R.id.icon6);
        fStore = FirebaseFirestore.getInstance();
        //iconsssss
        //icons
        icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), userAccount.class));
            }
        });

        icon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Guide.class));
            }
        });

        //get data

        DocumentReference docRef = fStore.collection("CollectorRates").document("material");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Glass = documentSnapshot.getDouble("Glass");
                Plastic = documentSnapshot.getDouble("Plastic");
                Cloth = documentSnapshot.getDouble("Cloth");
                Metal = documentSnapshot.getDouble("Metal");
                Paper = documentSnapshot.getDouble("Paper");
            }
        });


    }


    //calculate button
    public void onBtnTotal(View v){
        EditText Plasticfill,Clothfill,Metalfill,Glassfill,Paperfill;
        TextView total;

        double P,O,M,G,Pap = 0;


        Plasticfill = findViewById(R.id.Plasticfill);
        if(Plasticfill.getText().toString().isEmpty()){
            P = 0;
        }else {
            P = Float.parseFloat(Plasticfill.getText().toString());
        }

        Clothfill = findViewById(R.id.Clothfill);
        if(Clothfill.getText().toString().isEmpty()){
            O = 0;
        }else {
            O = Float.parseFloat(Clothfill.getText().toString());
        }

        Metalfill = findViewById(R.id.Metalfill);
        if(Metalfill.getText().toString().isEmpty()){
            M = 0;
        }else {
            M = Float.parseFloat(Metalfill.getText().toString());
        }

        Glassfill = findViewById(R.id.Glassfill);
        if(Glassfill.getText().toString().isEmpty()){
            G = 0;
        }else {
            G = Float.parseFloat(Glassfill.getText().toString());
        }

        Paperfill = findViewById(R.id.Paperfill);
        if (Paperfill.getText().toString().isEmpty()){
            Pap = 0;
        }else {
            Pap = Float.parseFloat(Paperfill.getText().toString());
        }

        total = findViewById(R.id.total);

        //prices
        double Plasticsum = Plastic*P;
        double Clothsum = Cloth*O;
        double Metalsum = Metal*M;
        double Glasssum = Glass*G;
        double Papersum = Paper*Pap;

        //calculation

        double sum =  Plasticsum +  Clothsum +  Metalsum + Glasssum +  Papersum;
        total.setText(Double.toString(sum));



    }


}