package com.example.ecoclan_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class customerN extends AppCompatActivity {


    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private  FirestoreRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_n);


    firebaseFirestore = FirebaseFirestore.getInstance();
    recyclerView = findViewById(R.id.recyclerView);

        //query
        Query query = firebaseFirestore.collection("customers");

        //recyclerOptions
    FirestoreRecyclerOptions<ActivityTwoRV> options = new FirestoreRecyclerOptions.Builder<ActivityTwoRV>()
                .setQuery(query , ActivityTwoRV.class)
               .build();

        adapter = new FirestoreRecyclerAdapter<ActivityTwoRV, customersViewHolder>(options) {
            @NonNull
            @Override
            public customersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single_customer,parent ,false);
                return new customersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull customersViewHolder holder, int position, @NonNull ActivityTwoRV model) {
             holder.customerAddress.setText(model.getUAddress());
                holder.customerMail.setText(model.getUEmail());
                holder.customerName.setText(model.getUFullName());
                holder.customerPhone.setText(model.getUPhone());

            }
        };

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }


    private class customersViewHolder  extends  RecyclerView.ViewHolder{
        private TextView customerAddress;

        private TextView customerMail;

        private TextView customerName;

        private TextView customerPhone;



        public customersViewHolder(@NonNull View itemView) {
            super(itemView);
            customerAddress = itemView.findViewById(R.id.customerAddress);
            customerMail = itemView.findViewById(R.id.customerMail);

            customerName = itemView.findViewById(R.id.customerName);

            customerPhone = itemView.findViewById(R.id.customerPhone);


        }

    }
    @Override
    protected  void onStop(){
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected  void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
