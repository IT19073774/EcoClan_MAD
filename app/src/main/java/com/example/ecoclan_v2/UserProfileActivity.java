package com.example.ecoclan_v2;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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
            }
        });

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
}