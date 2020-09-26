package com.example.ecoclan_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RecyclerRegisterCompanyActivity extends AppCompatActivity {

    EditText name, address, email, contact, regNum;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String current_user = auth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_register_company);

        name = findViewById(R.id.editTextTextPersonName6);
        address = findViewById(R.id.editTextTextPostalAddress);
        email = findViewById(R.id.editTextTextEmailAddress);
        contact = findViewById(R.id.editTextPhone2);
        regNum = findViewById(R.id.editTextPhone3);

    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerProfileActivity.class);
        startActivity(intent);
    }

    public void registerCompany(View view){

        final String nam, add, em, co,rNum;
        nam = name.getText().toString();
        add = address.getText().toString();
        em = email.getText().toString();
        co = contact.getText().toString();
        rNum = regNum.getText().toString();

        Map<String, Object> company = new HashMap<>();
        company.put("Name", nam);
        company.put("Address", add);
        company.put("Email ", em);
        company.put("Contact", co);
        company.put("RegistrationNumber ", rNum);
        company.put("OwnerID ", current_user);

        db.collection("Companies").document(current_user).set(company);
        Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), RecyclerProfileActivity.class);
        startActivity(intent);

    }
}
