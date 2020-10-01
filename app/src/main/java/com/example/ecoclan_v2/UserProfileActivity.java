package com.example.ecoclan_v2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import android.os.Parcelable;

import java.text.DateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class UserProfileActivity extends AppCompatActivity {
    TextView name, address, email, contact, city;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String TAG = "CHECKER";
    String currentUser;
    private List<DataList> dataList;
    private AgreementDataAdapter agreementdataAdapter;
    private List<TransactionList> transactionList;
    private TransactionAdapter transactionDataAdapter;
    RecyclerView recyclerView;
    Button agreement, transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        city = findViewById(R.id.city);
        agreement = findViewById(R.id.agreement);
        transaction = findViewById(R.id.transaction);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        dataList = new ArrayList<>();
        agreementdataAdapter = new AgreementDataAdapter(dataList);
        transactionList = new ArrayList<>();
        transactionDataAdapter = new TransactionAdapter(transactionList);
        currentUser = auth.getCurrentUser().getEmail();

        DocumentReference docRef = db.collection("Users").document(currentUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name.setText(document.getData().get("FirstName").toString() + " " + document.getData().get("LastName").toString());
                        address.setText(document.getData().get("Address").toString());
                        contact.setText(document.getData().get("Contact").toString());
                        email.setText(currentUser);
                        city.setText(document.getData().get("City").toString());
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        transactionView();

        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: I AM IN" );
                Resources res = getResources();
                Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.design9, null);
                agreement.setBackground(drawable);
                agreement.setTextColor(res.getColor(R.color.grey));
                drawable = ResourcesCompat.getDrawable(res, R.drawable.design5, null);
                transaction.setBackground(drawable);
                transaction.setTextColor(res.getColor(R.color.blurgrey));
                agreementView();
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources res = getResources();
                Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.design9, null);
                transaction.setBackground(drawable);
                transaction.setTextColor(res.getColor(R.color.grey));
                drawable = ResourcesCompat.getDrawable(res, R.drawable.design5, null);
                agreement.setBackground(drawable);
                agreement.setTextColor(res.getColor(R.color.blurgrey));
                transactionView();
            }
        });

    }

    public void transactionView() {
        transactionList.removeAll(transactionList);
        transactionDataAdapter.notifyItemRangeRemoved(0,transactionDataAdapter.getItemCount());
        recyclerView = findViewById(R.id.mainList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(transactionDataAdapter);
        Log.e(TAG, "STARTED READING.");

        db.collection("Payment")
                .orderBy("Date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ////////////
                                if (document.getData().get("PaymentBy").toString().equals(currentUser)) {

                                    final TransactionList data = new TransactionList();
                                    data.setEmail(document.getData().get("PaymentTo").toString());
                                    data.setAmount("- Rs." + document.getData().get("Amount").toString());
                                    data.setColor("red");
                                    data.setType(document.getData().get("Type").toString());
                                    data.setMaterial(document.getData().get("Weight").toString() + "Kg - " + document.getData().get("Material").toString());
                                    Timestamp t = (Timestamp) document.getData().get("Date");
                                    Date date = t.toDate();
                                    android.text.format.DateFormat df = new android.text.format.DateFormat();
                                    data.setDate(df.format("yyyy-MM-dd hh:mm:ss a", date).toString());
                                    Log.e(TAG, "onComplete: " + df.format("yyyy-MM-dd hh:mm:ss a", date));

                                    transactionList.add(data);
                                    transactionDataAdapter.notifyDataSetChanged();
                                }
                                else if (document.getData().get("PaymentTo").toString().equals(currentUser)) {

                                    final TransactionList data = new TransactionList();
                                    data.setEmail(document.getData().get("PaymentBy").toString());
                                    data.setAmount("Rs." + document.getData().get("Amount").toString());
                                    data.setColor("green");
                                    data.setType(document.getData().get("Type").toString());
                                    data.setMaterial(document.getData().get("Weight").toString() + "Kg - " + document.getData().get("Material").toString());
                                    Timestamp t = (Timestamp) document.getData().get("Date");
                                    Date date = t.toDate();
                                    android.text.format.DateFormat df = new android.text.format.DateFormat();
                                    data.setDate(df.format("yyyy-MM-dd hh:mm:ss a", date).toString());
                                    Log.e(TAG, "onComplete: " + df.format("yyyy-MM-dd hh:mm:ss a", date));

                                    transactionList.add(data);
                                    transactionDataAdapter.notifyDataSetChanged();
                                }
                            }
                            Log.e(TAG, "onComplete: " + transactionList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        Log.e(TAG, "FINISHED READING.");

    }

    public void agreementView() {
        dataList.removeAll(dataList);
        agreementdataAdapter.notifyItemRangeRemoved(0,agreementdataAdapter.getItemCount());
        recyclerView = findViewById(R.id.mainList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(agreementdataAdapter);
        Log.e(TAG, "STARTED READING.");

        db.collection("RecyclerRequests")
                .whereEqualTo("Status ", "AGREED")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ////////////
                                if (document.getData().get("Collector").toString().equals(currentUser)) {
                                    final DataList data = new DataList();
                                    data.setReqID(document.getId());
                                    Log.e(TAG, "DocumentSnapshot data: " + document.getData());
                                    data.setCategory(document.getData().get("Weight").toString() + "Kg - " + document.getData().get("Category").toString());
                                    data.setDate(document.getData().get("ExpectedDate ").toString());

                                    DocumentReference docRef = db.collection("Companies").document(document.getData().get("RequesterID ").toString());
                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document2 = task.getResult();
                                                if (document2.exists()) {
                                                    Log.d(TAG, "DocumentSnapshot data: " + document2.getData());
                                                    data.setAddress(document2.getData().get("Address").toString());
                                                    data.setName(document2.getData().get("Name").toString());
                                                    data.setContact(document2.getData().get("Contact").toString());

                                                    dataList.add(data);
                                                    agreementdataAdapter.notifyDataSetChanged();
                                                } else {
                                                    Log.d(TAG, "No such document");
                                                }
                                            } else {
                                                Log.d(TAG, "get failed with ", task.getException());
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        Log.e(TAG, "FINISHED READING.");

    }

    public void profileClose(View view) {
        Intent i = new Intent(UserProfileActivity.this,HomeActivity.class);
        startActivity(i);
    }

    public void resetpsw(View view) {
        final EditText resetMail = new EditText(view.getContext());

        AlertDialog.Builder passRestDialog = new AlertDialog.Builder(view.getContext());
        passRestDialog.setTitle("Rest Password?");
        passRestDialog.setMessage("Enter Your Email to Receive Rest Link.  ");
        passRestDialog.setView(resetMail);


        passRestDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //get Email and send reset link

                String mail = resetMail.getText().toString();
                auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserProfileActivity.this, "Rest Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserProfileActivity.this, "Error! Try Again" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        passRestDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close the dialog

            }
        });

        passRestDialog.create().show();
    }


    public void deleteUser(View view) {


        AlertDialog.Builder passRestDialog = new AlertDialog.Builder(view.getContext());
        passRestDialog.setTitle("Deactivate Account");
        passRestDialog.setMessage("Are you sure that you want to delete your account permanently?");



        passRestDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.collection("Users").document(currentUser)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                                auth.getCurrentUser().delete();
                                Toast.makeText(getApplicationContext(), "User Profile Successfully Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });


            }
        });
        passRestDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close the dialog

            }
        });

        passRestDialog.create().show();
    }

}