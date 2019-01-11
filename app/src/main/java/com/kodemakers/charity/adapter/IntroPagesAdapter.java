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
import com.kodemakers.charity.model.IntroPagesDetails;
import com.kodemakers.charity.model.StoryDetails;

import java.util.ArrayList;

public class IntroPagesAdapter extends RecyclerView.Adapter<IntroPagesAdapter.RecViewHolder>{

    Context context;
    ArrayList<IntroPagesDetails> newList;


    public IntroPagesAdapter(Context context, ArrayList<IntroPagesDetails> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public IntroPagesAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_intro_pages,parent,false);


        return new IntroPagesAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IntroPagesAdapter.RecViewHolder holder, final int position) {

        Glide.with(context).load(newList.get(position).getImage()).into(holder.ivImage);

        holder.ivDelete.setColorFilter(Color.parseColor("#03a9f4"));
        holder.ivEdit.setColorFilter(Color.parseColor("#03a9f4"));
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        ImageView ivImage,ivEdit,ivDelete;


        public RecViewHolder(View itemView) {
            super(itemView);


            ivImage = itemView.findViewById(R.id.ivImage);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);

        }
    }

}
