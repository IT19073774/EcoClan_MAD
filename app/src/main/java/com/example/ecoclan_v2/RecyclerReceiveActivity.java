package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class RecyclerReceiveActivity extends AppCompatActivity {

    private List<DataList> dataList;
    private RecyclerDataAdapter dataAdapter;

    FirebaseAuth auth;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_receive);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser().getEmail();

        recyclerView = findViewById(R.id.mainList);
        dataList = new ArrayList<>();
        dataAdapter = new RecyclerDataAdapter(dataList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(dataAdapter);

        db.collection("RecyclerRequests")
                .whereEqualTo("Status ", "AGREED")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ////////////
                                if (document.getData().get("RequesterID ").toString().equals(currentUser)) {
                                    final DataList data = new DataList();
                                    data.setReqID(document.getId());
                                    data.setCategory(document.getData().get("Category").toString());
                                    data.setDate(document.getData().get("ExpectedDate ").toString());
                                    data.setTime(document.getData().get("ExpectedTime").toString());
                                    data.setWeight(document.getData().get("Weight").toString());
                                    data.setCollectorEmail(document.getData().get("Collector").toString());
                                    dataList.add(data);
                                    dataAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerHomeActivity.class);
        startActivity(intent);
    }
}