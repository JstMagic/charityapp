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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.ContributorProfileActivity;
import com.kodemakers.charity.model.StoryDetails;
import com.kodemakers.charity.model.UserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryDetailsAdapter extends RecyclerView.Adapter<StoryDetailsAdapter.RecViewHolder> implements Filterable {

    Context context;
    ArrayList<StoryDetails> newList;
    ArrayList<StoryDetails> mFilteredList;

    public StoryDetailsAdapter(Context context, ArrayList<StoryDetails> newList) {
        this.context = context;
        this.newList = newList;
        mFilteredList = newList;
    }

    @Override
    public StoryDetailsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_image_story,parent,false);


        return new StoryDetailsAdapter.RecViewHolder(view);
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

                    ArrayList<StoryDetails> filteredList = new ArrayList<>();

                    for (StoryDetails categoryResult : newList) {

                        if (categoryResult.getName().toLowerCase().contains(charString)) {
                            filteredList.add(categoryResult);
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
                mFilteredList = (ArrayList<StoryDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public void onBindViewHolder(StoryDetailsAdapter.RecViewHolder holder, final int position) {

        holder.tvTitle.setText(mFilteredList.get(position).getName());
        holder.tvDate.setText(mFilteredList.get(position).getDate());
        Glide.with(context).load(mFilteredList.get(position).getImage()).into(holder.ivStoryImage);

        holder.ivDelete.setColorFilter(Color.parseColor("#03a9f4"));
        holder.ivEdit.setColorFilter(Color.parseColor("#03a9f4"));
         }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder{


        TextView tvTitle,tvDate;
        ImageView ivStoryImage,ivEdit,ivDelete;


        public RecViewHolder(View itemView) {
            super(itemView);


            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivStoryImage = itemView.findViewById(R.id.ivStoryImage);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);

        }
    }


}
