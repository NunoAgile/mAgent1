package com.example.magentdev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    private List<MovementDetails> movementDetailsList;

    public MyAdapter(Context ct, List<MovementDetails> movDetList ){
        context = ct;
        movementDetailsList = movDetList;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.mov_item,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mstTv.setText(movementDetailsList.get(position).getMst());
        holder.vdtTv.setText(movementDetailsList.get(position).getVdt());
        holder.descTv.setText(movementDetailsList.get(position).getMds());
        holder.mvlTv.setText(movementDetailsList.get(position).getMvl());
    }

    @Override
    public int getItemCount() {
        return movementDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mstTv, vdtTv, descTv, mvlTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mstTv = itemView.findViewById(R.id.mstTv);
            vdtTv = itemView.findViewById(R.id.vdtTv);
            descTv = itemView.findViewById(R.id.descTv);
            mvlTv = itemView.findViewById(R.id.mvlTv);
        }
    }



}
