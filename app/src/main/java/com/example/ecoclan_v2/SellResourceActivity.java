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
import com.google.firebase.firestore.DocumentChange;
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

public class SellResourceActivity extends AppCompatActivity {
    private List<DataList> dataList;
    private DataAdapter dataAdapter;
    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sell_resource);
        fStore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.mainList);
        dataList = new ArrayList<>();
        dataAdapter = new DataAdapter(dataList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(dataAdapter);

        fStore.collection("RecyclerRequests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                final DataList data = new DataList();
                                data.setReqID(document.getId());
                                Log.e(TAG, "DocumentSnapshot data: " + document.getData());
                                data.setCategory(document.getData().get("Weight").toString() + "Kg - " +document.getData().get("Category").toString());
                                data.setDate(document.getData().get("ExpectedDate ").toString());

                                DocumentReference docRef = fStore.collection("Companies").document(document.getData().get("RequesterID ").toString());
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
                                                dataAdapter.notifyDataSetChanged();
                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        /*fStore.collection("guide").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG, "Error" + e.getMessage());
                }

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){
                        //String title = doc.getDocument().getString("Title");
                        /*DataList data = doc.getDocument().toObject(DataList.class);
                        dataList.add(data);
                        dataAdapter.notifyDataSetChanged();


                    }
                }
            }
        });*/
    }

    public void PublishSellForm(View view) {
        Intent i = new Intent(SellResourceActivity.this,HomeActivity.class);
        Toast.makeText(getApplicationContext(), "Published Sell Request form to the Recycling Organization!", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

}