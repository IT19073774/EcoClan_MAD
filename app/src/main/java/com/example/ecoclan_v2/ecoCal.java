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
        total = findViewById(R.id.total);

        double UserInput_Plastic,UserInput_Cloth,UserInput_Metal,UserInput_Glass,UserInput_Paper = 0;


        Plasticfill = findViewById(R.id.Plasticfill);
        if(Plasticfill.getText().toString().isEmpty()){
            UserInput_Plastic = 0;
        }else {
            UserInput_Plastic = Float.parseFloat(Plasticfill.getText().toString());
        }

        Clothfill = findViewById(R.id.Clothfill);
        if(Clothfill.getText().toString().isEmpty()){
            UserInput_Cloth = 0;
        }else {
            UserInput_Cloth = Float.parseFloat(Clothfill.getText().toString());
        }

        Metalfill = findViewById(R.id.Metalfill);
        if(Metalfill.getText().toString().isEmpty()){
            UserInput_Metal = 0;
        }else {
            UserInput_Metal = Float.parseFloat(Metalfill.getText().toString());
        }

        Glassfill = findViewById(R.id.Glassfill);
        if(Glassfill.getText().toString().isEmpty()){
            UserInput_Glass = 0;
        }else {
            UserInput_Glass = Float.parseFloat(Glassfill.getText().toString());
        }

        Paperfill = findViewById(R.id.Paperfill);
        if (Paperfill.getText().toString().isEmpty()){
            UserInput_Paper = 0;
        }else {
            UserInput_Paper = Float.parseFloat(Paperfill.getText().toString());
        }

        double Final_SUM = ecoCal_sum(Plastic,Cloth,Metal,Glass,Paper,UserInput_Plastic,UserInput_Cloth,UserInput_Metal,UserInput_Glass,UserInput_Paper);
        total.setText(Double.toString(Final_SUM));

    }

    public static double ecoCal_sum(double Plastic,double Cloth,double Metal,double Glass,double Paper,double UserInput_Plastic,double UserInput_Cloth,double UserInput_Metal,double UserInput_Glass,double UserInput_Paper){


        double Plasticsum = Plastic*UserInput_Plastic;
        double Clothsum = Cloth*UserInput_Cloth;
        double Metalsum = Metal*UserInput_Metal;
        double Glasssum = Glass*UserInput_Glass;
        double Papersum = Paper*UserInput_Paper;

        //calculation

        double sum =  Plasticsum +  Clothsum +  Metalsum + Glasssum +  Papersum;
        return (sum);
    }

}