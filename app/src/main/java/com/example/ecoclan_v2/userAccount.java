package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class userAccount extends AppCompatActivity {

    ImageView imgbtn1;
    TextView avbtxt,UPphone,UPadress,UPname,UPemail,icon1,icon2,icon3;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        imgbtn1 = findViewById(R.id.imgbtn1);
        avbtxt = findViewById(R.id.avbtxt);
        UPphone = findViewById(R.id.UPphone);
        UPadress = findViewById(R.id.UPaddress);
        UPname = findViewById(R.id.UPname);
        UPemail = findViewById(R.id.UPemail);
        editprofile = findViewById(R.id.editprofile);

        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fStore.collection("customers").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                UPname.setText(documentSnapshot.getString("UFullName"));
                UPemail.setText(documentSnapshot.getString("UEmail"));
                UPadress.setText(documentSnapshot.getString("UAddress"));
                UPphone.setText(documentSnapshot.getString("UPhone"));


            }
        });

        //edit profile
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditProfile.class);
                i.putExtra("UFullName", UPname.getText().toString());
                i.putExtra("UPhone", UPphone.getText().toString());
                i.putExtra("UAddress", UPadress.getText().toString());
                startActivity(i);

            }
        });

        //icons
        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ecoCal.class));
            }
        });

        icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Guide.class));
            }
        });


        //avb btn
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(userAccount.this, UserMap.class);
                startActivity(intent);
                finish();
                //avbtxt.setVisibility(View.VISIBLE);
            }
        });
    }

    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginUser.class));
        finish();

    }


}