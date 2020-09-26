package com.example.ecoclan_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class RecyclerUpdateUserActivity extends AppCompatActivity {

    EditText name, email, address, city, contact, password;

    FirebaseAuth auth;
    FirebaseFirestore db;
    String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_update_user);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.textView10);
        email = findViewById(R.id.textView11);
        address = findViewById(R.id.textView12);
        city = findViewById(R.id.textView13);
        contact = findViewById(R.id.textView14);

        current_user = auth.getCurrentUser().getEmail();

        CollectionReference users = db.collection("Users");
        Query query = users.whereEqualTo("Email", current_user);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null) {}
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    String   firstName =  documentChange.getDocument().getData().get("FirstName").toString();
                    String   lastName =  documentChange.getDocument().getData().get("LastName").toString();
                    String add = documentChange.getDocument().getData().get("Address").toString();
                    String cit = documentChange.getDocument().getData().get("City").toString();
                    String cont = documentChange.getDocument().getData().get("Contact").toString();
                    name.setText(firstName + " " + lastName);
                    email.setText(current_user);
                    address.setText(add);
                    city.setText(cit);
                    contact.setText(cont);
                }
            }
        });

    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerProfileActivity.class);
        startActivity(intent);
    }
}