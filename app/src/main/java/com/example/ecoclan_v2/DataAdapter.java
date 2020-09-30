package com.example.ecoclan_v2;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    public List<DataList> ListG;
    public DataAdapter(List<DataList> ListG){
        this.ListG = ListG;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.id.setText(ListG.get(position).getReqID());
        holder.material.setText(ListG.get(position).getCategory());
        holder.date.setText(ListG.get(position).getDate());
        holder.company.setText(ListG.get(position).getName());
        holder.contact.setText(ListG.get(position).getContact());
        holder.address.setText(ListG.get(position).getAddress());


        holder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DocumentReference docref = FirebaseFirestore.getInstance().collection("RecyclerRequests").document(ListG.get(position).getReqID());
                docref
                        .update("Status ", "AGREED",
                                "Collector", FirebaseAuth.getInstance().getCurrentUser().getEmail())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e("TAG", "DocumentSnapshot successfully updated!");

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TAG", "Error updating document", e);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListG.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        View mView;

        public TextView id, material, date, address, contact, company;
        public Button agree;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            id = (TextView) mView.findViewById(R.id.id);
            material = (TextView) mView.findViewById(R.id.material);
            date = (TextView) mView.findViewById(R.id.date);
            address = (TextView) mView.findViewById(R.id.address);
            contact = (TextView) mView.findViewById(R.id.contact);
            company = (TextView) mView.findViewById(R.id.company);
            
            agree = (Button) mView.findViewById(R.id.button);

        }
    }
}
