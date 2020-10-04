package com.example.ecoclan_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RecyclerUpdateUserActivity extends AppCompatActivity {

    EditText nameF, nameL, address, city, contact;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String current_user = auth.getCurrentUser().getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_update_user);

        nameF = findViewById(R.id.textView10);
        nameL = findViewById(R.id.textView22);
        address = findViewById(R.id.textView12);
        city = findViewById(R.id.textView13);
        contact = findViewById(R.id.textView14);

        CollectionReference users = db.collection("Users");
        Query query = users.whereEqualTo("Email", current_user);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                }
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    String firstName = documentChange.getDocument().getData().get("FirstName").toString();
                    String lastName = documentChange.getDocument().getData().get("LastName").toString();
                    String add = documentChange.getDocument().getData().get("Address").toString();
                    String cit = documentChange.getDocument().getData().get("City").toString();
                    String cont = documentChange.getDocument().getData().get("Contact").toString();
                    nameF.setText(firstName);
                    nameL.setText(lastName);
                    address.setText(add);
                    city.setText(cit);
                    contact.setText(cont);
                }
            }
        });

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, RecyclerProfileActivity.class);
        startActivity(intent);
    }

    public void updateUser(View view) {

        final String name_first, name_last, add_, ci, co;

        name_first = nameF.getText().toString();
        name_last = nameL.getText().toString();
        add_ = address.getText().toString();
        ci = city.getText().toString();
        co = contact.getText().toString();

        if (name_first.isEmpty() || name_last.isEmpty() || add_.isEmpty() || ci.isEmpty() || co.isEmpty()) {
            Toast.makeText(this, "Empty Fields.", Toast.LENGTH_SHORT).show();
        } else {
            DocumentReference documentReference = db.collection("Users").document(current_user);
            Map<String, Object> newRecycler = new HashMap<>();
            newRecycler.put("FirstName", name_first);
            newRecycler.put("LastName", name_last);
            newRecycler.put("Address", add_);
            newRecycler.put("City", ci);
            newRecycler.put("Contact", co);
            documentReference.update(newRecycler).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "User Information Saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), RecyclerProfileActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

}

