package com.example.ecoclan_v2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class customerN extends AppCompatActivity {


    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private  FirestoreRecyclerAdapter adapter;
    Button adminupdatebtn ,admindeletebtn;
    DocumentReference documentReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_n);
        documentReference = firebaseFirestore.collection("customers").document("X4YhKxDwxueFV9PYDq7W9Nomv943");


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

            adminupdatebtn = itemView.findViewById(R.id.adminupdatebtn);
            admindeletebtn  = itemView.findViewById(R.id.admindeletebtn);


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

public void DeleteProfile(View view){
        ShowDialog();

}

    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(customerN.this);
        builder.setTitle("DELETE");
        builder.setMessage("Are you sure to  delete this account?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface  dialogInterface, int i) {

                documentReference.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(customerN.this,"Account deleted", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });




            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();


        admindeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();



            }
        });
    }


}


