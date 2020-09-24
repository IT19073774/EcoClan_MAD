package com.example.ecoclan_v2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText EditAddress,EditPhone,EditName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button updbtn,deleteaccbtn;
    FirebaseUser user;
    ProgressBar progressBar2;
    TextView icon1,icon2,icon3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        deleteaccbtn = findViewById(R.id.deleteaccbtn);
        progressBar2 = findViewById(R.id.progressBar2);
        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);

        //icons
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), userAccount.class));
            }
        });
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

        //delete account
        deleteaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deletedialog = new AlertDialog.Builder(EditProfile.this);
                deletedialog.setTitle("Are you sure?");
                deletedialog.setMessage("Deleting this account will result in completely removing your account from the system and you won't be able to access the app.");

                //positive button
                deletedialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBar2.setVisibility(View.VISIBLE);

                             user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     progressBar2.setVisibility(View.GONE);
                                     if (task.isSuccessful()){
                                        Toast.makeText(EditProfile.this, "Account Deleted!", Toast.LENGTH_SHORT).show();
                                         FirebaseAuth.getInstance().signOut();
                                         startActivity(new Intent(getApplicationContext(),LoginUser.class));
                                         finish();

                                     }else {
                                         Toast.makeText(EditProfile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                    }
                });
                //negative button
                deletedialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = deletedialog.create();
                alertDialog.show();
            }
        });



        Intent data = getIntent();
        String editName = data.getStringExtra("UFullName");
        String editAddress = data.getStringExtra("UAddress");
        String editPhone = data.getStringExtra("UPhone");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        EditName = findViewById(R.id.EditName);
        EditAddress = findViewById(R.id.EditAddress);
        EditPhone = findViewById(R.id.EditPhone);

        updbtn= findViewById(R.id.updbtn);

        updbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditAddress.getText().toString().isEmpty() || EditPhone.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this,"One or many fields are empty.",Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference documentReference =fStore.collection("customers").document(user.getUid());
                Map<String,Object> updated = new HashMap<>();
                updated.put("UFullName",EditName.getText().toString());
                updated.put("UPhone",EditPhone.getText().toString());
                updated.put("UAddress",EditAddress.getText().toString());
                documentReference.update(updated).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfile.this, "ProfileUpdated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), userAccount.class));
                        finish();
                    }
                });

            }
        });

        EditName.setText(editName);
        EditAddress.setText(editAddress);
        EditPhone.setText(editPhone);
        Log.d(TAG,"onCreate: " + editAddress + " " + editPhone);



    }
}