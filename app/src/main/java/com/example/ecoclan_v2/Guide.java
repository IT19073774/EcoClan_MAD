package com.example.ecoclan_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Guide extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView icon4,icon5;

    private List<List_guide> Instructions;
    private List_guide_adapter guideAdapter;
    FirebaseFirestore fStore;
    RecyclerView mainList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_user);
        setContentView(R.layout.activity_guide);

        //nav icons
        icon4 = findViewById(R.id.icon4);
        icon5 = findViewById(R.id.icon5);

        icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), userAccount.class));
            }
        });

        icon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ecoCal.class));
            }
        });



        fStore = FirebaseFirestore.getInstance();
        Instructions = new ArrayList<>();
        guideAdapter = new List_guide_adapter(Instructions);
        mainList = findViewById(R.id.mainList);
        mainList.setHasFixedSize(true);
        mainList.setLayoutManager(new LinearLayoutManager(this));
        mainList.setAdapter(guideAdapter);

        fStore.collection("guide").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG, "Error" + e.getMessage());
                }

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){
                        List_guide list_guide = doc.getDocument().toObject(List_guide.class);
                        Instructions.add(list_guide);

                        guideAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }
}