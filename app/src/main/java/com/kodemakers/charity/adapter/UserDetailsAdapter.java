package com.kodemakers.charity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.ContributorProfileActivity;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.model.FollowersItem;
import com.kodemakers.charity.model.StoryDetails;
import com.kodemakers.charity.model.UserDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.RecViewHolder> implements Filterable {

    Context context;
    List<FollowersItem> newList;
    List<FollowersItem> mFilteredList;


    public UserDetailsAdapter(Context context, List<FollowersItem> newList) {
        this.context = context;
        this.newList = newList;
        mFilteredList = newList;
    }

    @Override
    public UserDetailsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_user,parent,false);
        return new UserDetailsAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserDetailsAdapter.RecViewHolder holder, final int position) {

        holder.tvUserName.setText(mFilteredList.get(position).getUsername());
        holder.tvDate.setText(mFilteredList.get(position).getCreatedAt());
//        Glide.with(context).load(AppConstants.BASE_URL + mFilteredList.get(position).getImage()).into(holder.civUserImage);
        Picasso.with(context).load(AppConstants.BASE_URL + mFilteredList.get(position).getImage()).into(holder.civUserImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.pb.setVisibility(View.GONE);
                holder.civUserImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                holder.pb.setVisibility(View.GONE);
                holder.civUserImage.setVisibility(View.VISIBLE);
            }
        });

        holder.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ContributorProfileActivity.class);
                i.putExtra("followersDetails",mFilteredList.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = newList;
                } else {

                    List<FollowersItem> filteredList = new ArrayList<>();

                    for (FollowersItem followersItem : newList) {

                        if (followersItem.getUsername().toLowerCase().contains(charString)) {
                            filteredList.add(followersItem);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<FollowersItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvUserName,tvDate;
        CircleImageView civUserImage;
        LinearLayout llDetails;
        ProgressBar pb;

        public RecViewHolder(View itemView) {
            super(itemView);

            pb = itemView.findViewById(R.id.pb);
            tvUserName = itemView.findViewById(R.id.tvFollowerName);
            tvDate = itemView.findViewById(R.id.tvDate);
            civUserImage = itemView.findViewById(R.id.civUserImage);
            llDetails = itemView.findViewById(R.id.llDetails);
        }
    }


}
