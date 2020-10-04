package com.example.ecoclan_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addNew extends AppCompatActivity {

    EditText adminName, adminMail, adminPhone, adminPassword;
    Button adminSavebtn;
    ProgressBar adminProgress;
    FirebaseAuth FAuth;
    FirebaseFirestore fstore;
    String adminUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

                adminName = findViewById(R.id.adminName);
                adminMail = findViewById(R.id.adminMail);
                adminPhone = findViewById(R.id.adminPhone);
                adminPassword = findViewById(R.id.adminMail);
                adminSavebtn = findViewById(R.id.adminSavebtn);


                FAuth = FirebaseAuth.getInstance();
                fstore =FirebaseFirestore.getInstance();
             adminProgress = findViewById(R.id.adminProgress);




             adminSavebtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     final String mail= adminMail.getText().toString().trim();
                     final String Password= adminPassword.getText().toString().trim();
                  final String Name = adminName.getText().toString();
                     final String PhoneNumber = adminPhone.getText().toString();




                     if(TextUtils.isEmpty(mail)) {
                        adminMail.setError("Email is Required");
                         return;
                     }
                     if(TextUtils.isEmpty(Password)) {
                         adminPassword.setError("Password is Required");
                         return;

                     }
                     if(adminPassword.length()  < 6){
                         adminPassword.setError("password must be greater than 6 chracters");
                         return;
                     }

                     adminProgress.setVisibility(View.VISIBLE);

                     FAuth.createUserWithEmailAndPassword(mail,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(addNew.this,"Admin created", Toast.LENGTH_SHORT).show();
                                 adminUserId = FAuth.getCurrentUser().getUid();
                                 DocumentReference documentReference = fstore.collection("admin_").document(adminUserId);
                                 Map<String,Object> admin = new HashMap<>();
                                 admin.put("fname",Name);
                                 admin.put("email",mail);
                                 admin.put("pnumber",PhoneNumber);
                                 admin.put("password",Password);

                                 documentReference.set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {

                                     @Override
                                     public void onSuccess(Void aVoid) {
                                         Log.d("TAG","onSuccess:user profile is created for"+  adminUserId);
                                     }
                                 });


                                 startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));

                             } else {
                                 Toast.makeText(addNew.this, "Error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                             }

                         }
                     });

                     }


             });
    }
    }
