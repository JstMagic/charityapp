package com.kodemakers.charity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;
import com.kodemakers.charity.model.DonationDetails;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonationDetailAdapter extends RecyclerView.Adapter<DonationDetailAdapter.RecViewHolder>{

    Context context;
    List<DonationDetails> newList;


    public DonationDetailAdapter(Context context, List<DonationDetails> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public DonationDetailAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_donation_contributor,parent,false);


        return new DonationDetailAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DonationDetailAdapter.RecViewHolder holder, final int position) {

        holder.tvUserName.setText(newList.get(position).getUsername());
        holder.tvComments.setText(newList.get(position).getComment());
        holder.tvDate.setText(newList.get(position).getCreatedAt().substring(0,10));

        holder.tvAmount.setText(context.getString(R.string.dollar_symbol)  + " " + newList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvUserName,tvComments,tvAmount,tvDate;

        public RecViewHolder(View itemView) {
            super(itemView);


            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvComments = itemView.findViewById(R.id.tvComments);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }

}
