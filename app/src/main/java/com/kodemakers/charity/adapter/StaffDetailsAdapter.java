package com.kodemakers.charity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;
import com.kodemakers.charity.model.StaffDetails;
import com.kodemakers.charity.model.StoryDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffDetailsAdapter extends RecyclerView.Adapter<StaffDetailsAdapter.RecViewHolder>{

    Context context;
    ArrayList<StaffDetails> newList;


    public StaffDetailsAdapter(Context context, ArrayList<StaffDetails> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public StaffDetailsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_charity_staff,parent,false);


        return new StaffDetailsAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffDetailsAdapter.RecViewHolder holder, final int position) {

        holder.tvUserName.setText(newList.get(position).getName());
        holder.tvRoleName.setText(newList.get(position).getRole());
        Glide.with(context).load(newList.get(position).getImage()).into(holder.civUserImage);
        holder.ivForwardArrow.setColorFilter(Color.parseColor("#03a9f4"));

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvUserName,tvRoleName;
        CircleImageView civUserImage;
        ImageView ivForwardArrow;

        public RecViewHolder(View itemView) {
            super(itemView);


            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvRoleName = itemView.findViewById(R.id.tvRoleName);
            civUserImage = itemView.findViewById(R.id.civUserImage);
            ivForwardArrow = itemView.findViewById(R.id.ivForwardArrow);
        }
    }

}
