package com.kodemakers.charity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.ContributorProfileActivity;
import com.kodemakers.charity.model.UserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.RecViewHolder>{

    Context context;
    ArrayList<UserDetails> newList;


    public UserDetailsAdapter(Context context, ArrayList<UserDetails> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public UserDetailsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_user,parent,false);


        return new UserDetailsAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserDetailsAdapter.RecViewHolder holder, final int position) {

        holder.tvUserName.setText(newList.get(position).getName());
        holder.tvDate.setText(newList.get(position).getDate());
        Glide.with(context).load(newList.get(position).getImage()).into(holder.civUserImage);
        holder.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ContributorProfileActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvUserName,tvDate;
        CircleImageView civUserImage;
        LinearLayout llDetails;

        public RecViewHolder(View itemView) {
            super(itemView);


            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
            civUserImage = itemView.findViewById(R.id.civUserImage);
            llDetails = itemView.findViewById(R.id.llDetails);
        }
    }


}
