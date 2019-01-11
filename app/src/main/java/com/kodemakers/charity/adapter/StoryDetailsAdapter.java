package com.kodemakers.charity.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.kodemakers.charity.model.StoryDetails;
import com.kodemakers.charity.model.UserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryDetailsAdapter extends RecyclerView.Adapter<StoryDetailsAdapter.RecViewHolder>{

    Context context;
    ArrayList<StoryDetails> newList;


    public StoryDetailsAdapter(Context context, ArrayList<StoryDetails> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public StoryDetailsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_image_story,parent,false);


        return new StoryDetailsAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryDetailsAdapter.RecViewHolder holder, final int position) {

        holder.tvTitle.setText(newList.get(position).getName());
        holder.tvDate.setText(newList.get(position).getDate());
        Glide.with(context).load(newList.get(position).getImage()).into(holder.ivStoryImage);


         }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvTitle,tvDate;
        ImageView ivStoryImage;


        public RecViewHolder(View itemView) {
            super(itemView);


            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivStoryImage = itemView.findViewById(R.id.ivStoryImage);

        }
    }


}
