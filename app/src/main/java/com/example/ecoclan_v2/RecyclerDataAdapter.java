package com.example.ecoclan_v2;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {
    public List<DataList> ListG;
    public RecyclerDataAdapter(List<DataList> ListG){
        this.ListG = ListG;
    }
    ProgressDialog dialog;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialog =new ProgressDialog(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_single_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.id.setText(ListG.get(position).getReqID());
        holder.cat.setText(ListG.get(position).getCategory());
        holder.dat.setText(ListG.get(position).getDate());
        holder.tim.setText(ListG.get(position).getTime());
        holder.ema.setText(ListG.get(position).getCollectorEmail());
        holder.wei.setText(ListG.get(position).getWeight());


    }

    @Override
    public int getItemCount() {
        return ListG.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        View mView;

        public TextView id, cat, wei, dat, tim, ema;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            id = (TextView) mView.findViewById(R.id.company);
            cat = (TextView) mView.findViewById(R.id.address);
            wei = (TextView) mView.findViewById(R.id.contact);
            dat = (TextView) mView.findViewById(R.id.material);
            tim = (TextView) mView.findViewById(R.id.date);
            ema = (TextView) mView.findViewById(R.id.id);

        }
    }
}
