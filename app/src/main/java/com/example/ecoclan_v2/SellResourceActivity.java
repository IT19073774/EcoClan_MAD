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

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class SellResourceActivity extends AppCompatActivity {
    private List<List_guide> Instructions;
    private List_guide_adapter guideAdapter;
    FirebaseFirestore fStore;
    RecyclerView mainList;
    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sell_resource);

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
                        //String title = doc.getDocument().getString("Title");
                        List_guide list_guide = doc.getDocument().toObject(List_guide.class);
                        Instructions.add(list_guide);


                        guideAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void PublishSellForm(View view) {
        Intent i = new Intent(SellResourceActivity.this,HomeActivity.class);
        Toast.makeText(getApplicationContext(), "Published Sell Request form to the Recycling Organization!", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

}