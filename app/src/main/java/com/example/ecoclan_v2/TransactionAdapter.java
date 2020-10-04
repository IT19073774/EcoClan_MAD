package com.example.ecoclan_v2;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    public List<TransactionList> ListG;
    public TransactionAdapter(List<TransactionList> ListG){
        this.ListG = ListG;
    }
    ProgressDialog dialog;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialog =new ProgressDialog(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.email.setText(ListG.get(position).getEmail());
        holder.datetime.setText(ListG.get(position).getDate());
        holder.type.setText(ListG.get(position).getType());

        holder.material.setText(ListG.get(position).getMaterial());
        holder.amount.setText(ListG.get(position).getAmount());
        if (ListG.get(position).getColor().equals("red")) {
            holder.amount.setTextColor(Color.RED);
            holder.type.setTextColor(Color.RED);
        } else {
            holder.amount.setTextColor(Color.GREEN);
            holder.type.setTextColor(Color.GREEN);
        }


    }

    @Override
    public int getItemCount() {
        return ListG.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        View mView;

        public TextView email, datetime, type, material, amount;
        public Button agree;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            email = (TextView) mView.findViewById(R.id.email);
            datetime = (TextView) mView.findViewById(R.id.datetime);
            type = (TextView) mView.findViewById(R.id.type);
            material = (TextView) mView.findViewById(R.id.material);
            amount = (TextView) mView.findViewById(R.id.amount);

        }
    }
}
