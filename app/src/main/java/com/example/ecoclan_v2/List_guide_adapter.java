package com.example.ecoclan_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class List_guide_adapter  extends RecyclerView.Adapter<List_guide_adapter.ViewHolder> {

    public List<List_guide> ListG;
    public List_guide_adapter(List<List_guide> ListG){
        this.ListG = ListG;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.list_title.setText(ListG.get(position).getTitle());
        holder.list_msg.setText(ListG.get(position).getMsg());
    }

    @Override
    public int getItemCount() {
        return ListG.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        View mView;

        public TextView list_title;
        public TextView list_msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            list_title = (TextView) mView.findViewById(R.id.list_title);
            list_msg = (TextView) mView.findViewById(R.id.list_msg);

        }
    }
}
