package com.kodemakers.charity.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.AddNewStoryActivity;
import com.kodemakers.charity.activities.AddVideoFeedActivity;
import com.kodemakers.charity.activities.PlayVideoActivity;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.model.FeedsDetails;
import com.kodemakers.charity.model.LikesDetails;
import com.kodemakers.charity.model.StatusResponse;
import com.kodemakers.charity.model.FeedsDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CharityStoriesAdapter extends RecyclerView.Adapter<CharityStoriesAdapter.RecViewHolder> implements Filterable {

    Context context;
    List<FeedsDetails> newList;
    private List<LikesDetails> likes;
    List<FeedsDetails> mFilteredList;

    public CharityStoriesAdapter(Context context, List<FeedsDetails> newList, List<LikesDetails> likes) {
        this.context = context;
        this.newList = newList;
        this.likes =likes;
        mFilteredList = newList;
    }

    @Override
    public CharityStoriesAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_image_story, parent, false);
        return new CharityStoriesAdapter.RecViewHolder(view);
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

                    ArrayList<FeedsDetails> filteredList = new ArrayList<>();

                    for (FeedsDetails categoryResult : newList) {

                        if (categoryResult.getTitle().toLowerCase().contains(charString)) {
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
                mFilteredList = (ArrayList<FeedsDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(final CharityStoriesAdapter.RecViewHolder holder, final int position) {

        holder.tvTitle.setText(mFilteredList.get(position).getTitle());
        Glide.with(context).load(AppConstants.BASE_URL + mFilteredList.get(position).getImageUrl()).into(holder.ivImage);
        holder.tvDate.setText(mFilteredList.get(position).getCreatedAt().substring(0,10));

        holder.ivDelete.setColorFilter(Color.parseColor("#03a9f4"));
        holder.ivEdit.setColorFilter(Color.parseColor("#03a9f4"));

        if(mFilteredList.get(position).getDetails().equalsIgnoreCase("")){
            holder.tvDetails.setVisibility(View.GONE);
        }else{
            holder.tvDetails.setVisibility(View.VISIBLE);
            holder.tvDetails.setText(mFilteredList.get(position).getDetails());
        }
        if(mFilteredList.get(position).getFeedType().equalsIgnoreCase("text")){
            holder.ivImage.setVisibility(View.GONE);
        }else {
            holder.ivImage.setVisibility(View.VISIBLE);
            if(mFilteredList.get(position).getFeedType().equalsIgnoreCase("image")){
                holder.ivPlayVideo.setVisibility(View.GONE);
            }else if(mFilteredList.get(position).getFeedType().equalsIgnoreCase("video")){
                holder.ivPlayVideo.setVisibility(View.VISIBLE);
                holder.ivPlayVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, PlayVideoActivity.class);
                        i.putExtra("video",AppConstants.BASE_URL + mFilteredList.get(position).getVideoUrl());
                        context.startActivity(i);
                    }
                });
            }
        }
        holder.tvLikesCount.setText("Likes : " +mFilteredList.get(position).getLikes());
        if(mFilteredList.get(position).getFeedType().equalsIgnoreCase("text")){
            holder.llEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, AddNewStoryActivity.class);
                    i.putExtra("FeedDetails",mFilteredList.get(position));
                    context.startActivity(i);
                }
            });
        }else {
            if(mFilteredList.get(position).getFeedType().equalsIgnoreCase("image")){
                holder.llEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context, AddNewStoryActivity.class);
                        i.putExtra("FeedDetails",mFilteredList.get(position));
                        i.putExtra("is_Image",true);
                        context.startActivity(i);
                    }
                });
            }else if(mFilteredList.get(position).getFeedType().equalsIgnoreCase("video")){
                holder.llEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context, AddVideoFeedActivity.class);
                        i.putExtra("FeedDetails",mFilteredList.get(position));
                        i.putExtra("type", "edit");
                        context.startActivity(i);
                    }
                });
            }
        }


        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CLick ","Yes");

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Do you really want to delete this feed").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final ProgressDialog progressDialog;
                                progressDialog = new ProgressDialog(context);
                                progressDialog.setMessage("Deleting...");
                                progressDialog.show();

                                JSONObject requestObjet = new JSONObject();
                                try {
                                    requestObjet.put("feed_id", mFilteredList.get(position).getFeedId());
                                    requestObjet.put("charity_id", mFilteredList.get(position).getCharityId());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                new PostServiceCall(AppConstants.DELETE_FEEDS, requestObjet) {

                                    @Override
                                    public void response(String response) {
                                        progressDialog.dismiss();
                                        StatusResponse statusChangeResponse = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                                        Toast.makeText(context, statusChangeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        if (statusChangeResponse.getStatus().equalsIgnoreCase("1")) {
                                            mFilteredList.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    }
                                    @Override
                                    public void error(String error) {
                                        progressDialog.dismiss();
                                    }
                                }.call();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.setTitle("Delete Feed");
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDate,tvDetails,tvLikesCount;
        ImageView ivImage,ivShare,ivPlayVideo,ivEdit,ivDelete;
        LinearLayout llEdit,llDelete;

        public RecViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivPlayVideo = itemView.findViewById(R.id.ivPlayVideo);
            llEdit = itemView.findViewById(R.id.llEdit);
            llDelete = itemView.findViewById(R.id.llDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }



}
