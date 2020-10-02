package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_receive);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.mainList);
        dataList = new ArrayList<>();
        dataAdapter = new RecyclerDataAdapter(dataList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(dataAdapter);

        db.collection("RecyclerRequests")
                .whereEqualTo("Status ", "AGREED")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Toast.makeText(RecyclerReceiveActivity.this, "EMPTY", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dataList.removeAll(dataList);
                        dataAdapter.notifyItemRangeRemoved(0, dataAdapter.getItemCount());

                        for (QueryDocumentSnapshot document : value) {
                            if (document.exists()) {
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

                    }
                });
    }

    public void goBack(View view){
        Intent intent = new Intent(this, RecyclerHomeActivity.class);
        startActivity(intent);
    }
}