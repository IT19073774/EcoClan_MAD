package com.example.ecoclan_v2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity {

    EditText logemail, logpassword;
    Button loginBtn,admin,RegBtn;
    TextView  fgtpass,employeeloginredrict;
    ProgressBar progressBarlogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_user);

        logemail = findViewById(R.id.logemail);
        logpassword = findViewById(R.id.logpassword);
        loginBtn = findViewById(R.id.loginBtn);
        RegBtn = findViewById(R.id.RegBtn);
        fgtpass = findViewById(R.id.fgtpass);
        employeeloginredrict =findViewById(R.id.employeeloginredrict);
        progressBarlogin = findViewById(R.id.progressBarlogin);
        fAuth = FirebaseAuth.getInstance();



        employeeloginredrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = logemail.getText().toString().trim();
                String password = logpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    logemail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    logpassword.setError("Password is Required");
                    return;
                }

                if(password.length() <6){
                    logpassword.setError("Password Must be more than 6 characters.");
                    return;
                }

                progressBarlogin.setVisibility(View.VISIBLE);

                //authenticate user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginUser.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), userAccount.class));
                        }else{
                            Toast.makeText(LoginUser.this, "Error!" +  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarlogin.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });


        //forgot password
        fgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passRestDialog = new AlertDialog.Builder(view.getContext());
                passRestDialog.setTitle("Rest Password?");
                passRestDialog.setMessage("Enter Your Email to Receive Rest Link.  ");
                passRestDialog.setView(resetMail);

                passRestDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get Email and send reset link

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginUser.this, "Rest Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginUser.this, "Error! Try Again" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                passRestDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close the dialog

                    }
                });

                passRestDialog.create().show();
            }
        });

    }

    public void onBtnReg(View v) {
        startActivity(new Intent(getApplicationContext(), RegisterUser.class));
    }

}