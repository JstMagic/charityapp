package com.kodemakers.charity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kodemakers.charity.R;
import com.kodemakers.charity.model.NotificationData;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.RecViewHolder>{

    Context context;
    ArrayList<NotificationData> newList;


    public NotificationsAdapter(Context context, ArrayList<NotificationData> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public NotificationsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_notification,parent,false);


        return new NotificationsAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.RecViewHolder holder, final int position) {

        holder.tvTitle.setText(newList.get(position).getTitle());
        holder.tvMessage.setText(newList.get(position).getMessage());
        holder.tvDate.setText(newList.get(position).getDate());
//        holder.llDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, AdDetailActivity.class);
//                context.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvMessage,tvDate,tvTitle;
        ImageView ivForwardArrow;
        LinearLayout llDetails;

        public RecViewHolder(View itemView) {
            super(itemView);


            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }


}
