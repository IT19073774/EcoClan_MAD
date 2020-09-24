package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText UserFullName,UserEmail,UserPassword,UserConPassword,UserPhone,UserAddress;
    Button RegBtn;
    TextView redLog;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        UserFullName = findViewById(R.id.UserFullName);
        UserEmail = findViewById(R.id.UserEmail);
        UserPassword = findViewById(R.id.UserPassword);
        UserConPassword = findViewById(R.id.UserConPassword);
        UserPhone = findViewById(R.id.UserPhone);
        UserAddress = findViewById(R.id.UserAddress);

        RegBtn = findViewById(R.id.RegBtn);
        redLog = findViewById(R.id.redLog);
        progressBar = findViewById(R.id.progressBar);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

       if(fAuth.getCurrentUser() != null){
           startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        RegBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String email = UserEmail.getText().toString().trim();
                String password = UserPassword.getText().toString().trim();
                String passwordCon = UserConPassword.getText().toString().trim();

                final String FullName = UserFullName.getText().toString().trim();
                final String Phone = UserPhone.getText().toString().trim();
                final String Address = UserAddress.getText().toString().trim();



                if (TextUtils.isEmpty(email)){
                        UserEmail.setError("Email Required.");
                        return;
                }
                if (TextUtils.isEmpty(password)){
                    UserPassword.setError("Password Required.");
                    return;
                }

                if(password.length() <6){
                    UserPassword.setError("Password Must be more than 6 characters.");
                    return;
                }

                if(!password.equals(passwordCon)){
                    UserPassword.setError("Passwords aren't matching.");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                //register user
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterUser.this, "User Created!", Toast.LENGTH_SHORT).show();
                            UserID = fAuth.getCurrentUser().getUid();

                            //store user data
                            DocumentReference documentReference = fStore.collection("customers").document(UserID);
                            Map<String, Object> user = new HashMap<>();
                                user.put("UFullName", FullName);
                                user.put("UEmail", email);
                                user.put("UPhone", Phone);
                                user.put("UAddress", Address);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSucess : User Profile is created for" + UserID);
                                    }
                                });

                            startActivity(new Intent(getApplicationContext(), userAccount.class));

                        }else {
                            Toast.makeText(RegisterUser.this, "Error! " +  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);


                        }
                    }
                });
            }
        });

        redLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginUser.class));
            }
        });


    }
}