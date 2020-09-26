package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class RecyclerProfileActivity extends AppCompatActivity {

    TextView name, email, address, city, contact, password;

    FirebaseAuth auth;
    FirebaseFirestore db;
    String current_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_profile);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.textView10);
        email = findViewById(R.id.textView11);
        address = findViewById(R.id.textView12);
        city = findViewById(R.id.textView13);
        contact = findViewById(R.id.textView14);
        password = findViewById(R.id.textView15);

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
                    String pass = documentChange.getDocument().getData().get("Password").toString();
                    name.setText(firstName + " " + lastName);
                    email.setText(current_user);
                    address.setText(add);
                    city.setText(cit);
                    contact.setText(cont);
                    password.setText(pass);
                }
            }
        });


    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerHomeActivity.class);
        startActivity(intent);
    }

    public void goToRegisterCompanyPage(View view){
        Intent intent = new Intent(this, RecyclerRegisterCompanyActivity.class);
        startActivity(intent);
    }

    public void goToUpdateUserPage(View view){
        Intent intent = new Intent(this, RecyclerUpdateUserActivity.class);
        startActivity(intent);
    }
}