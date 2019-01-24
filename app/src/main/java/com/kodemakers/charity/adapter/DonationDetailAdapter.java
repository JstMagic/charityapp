package com.kodemakers.charity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.ContributorProfileActivity;
import com.kodemakers.charity.model.DonationDetails;
import com.kodemakers.charity.model.UserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonationDetailAdapter extends RecyclerView.Adapter<DonationDetailAdapter.RecViewHolder>{

    Context context;
    ArrayList<DonationDetails> newList;


    public DonationDetailAdapter(Context context, ArrayList<DonationDetails> newList) {
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

        holder.tvUserName.setText(newList.get(position).getContributorName());
        holder.tvDonationType.setText(newList.get(position).getDonationType());
        Glide.with(context).load(newList.get(position).getImage()).into(holder.civUserImage);

        holder.tvAmount.setText(newList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvUserName,tvDonationType,tvAmount;
        CircleImageView civUserImage;
        ImageView ivForwardArrow;

        public RecViewHolder(View itemView) {
            super(itemView);


            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDonationType = itemView.findViewById(R.id.tvDonationType);
            civUserImage = itemView.findViewById(R.id.civUserImage);
            ivForwardArrow = itemView.findViewById(R.id.ivForwardArrow);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }

}
