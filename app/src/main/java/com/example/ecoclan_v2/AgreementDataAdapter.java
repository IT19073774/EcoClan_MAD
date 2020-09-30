package com.example.ecoclan_v2;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AgreementDataAdapter extends RecyclerView.Adapter<AgreementDataAdapter.ViewHolder> {
    public List<DataList> ListG;
    public AgreementDataAdapter(List<DataList> ListG){
        this.ListG = ListG;
    }
    ProgressDialog dialog;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialog =new ProgressDialog(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agreement_list_item, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return ListG.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        View mView;

        public TextView id, material, date, address, contact, company;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            id = (TextView) mView.findViewById(R.id.id);
            material = (TextView) mView.findViewById(R.id.material);
            date = (TextView) mView.findViewById(R.id.date);
            address = (TextView) mView.findViewById(R.id.address);
            contact = (TextView) mView.findViewById(R.id.contact);
            company = (TextView) mView.findViewById(R.id.company);

        }
    }
}
