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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CharityStoriesAdapter extends RecyclerView.Adapter<CharityStoriesAdapter.RecViewHolder> {

    Context context;
    List<FeedsDetails> newList;
    private List<LikesDetails> likes;

    public CharityStoriesAdapter(Context context, List<FeedsDetails> newList, List<LikesDetails> likes) {
        this.context = context;
        this.newList = newList;
        this.likes =likes;
    }

    @Override
    public CharityStoriesAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_image_story, parent, false);
        return new CharityStoriesAdapter.RecViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final CharityStoriesAdapter.RecViewHolder holder, final int position) {

        holder.tvTitle.setText(newList.get(position).getTitle());
        Glide.with(context).load(AppConstants.BASE_URL + newList.get(position).getImageUrl()).into(holder.ivImage);
        holder.tvDate.setText(newList.get(position).getCreatedAt().substring(0,10));

        holder.ivDelete.setColorFilter(Color.parseColor("#03a9f4"));
        holder.ivEdit.setColorFilter(Color.parseColor("#03a9f4"));

        if(newList.get(position).getDetails().equalsIgnoreCase("")){
            holder.tvDetails.setVisibility(View.GONE);
        }else{
            holder.tvDetails.setVisibility(View.VISIBLE);
            holder.tvDetails.setText(newList.get(position).getDetails());
        }
        if(newList.get(position).getFeedType().equalsIgnoreCase("text")){
            holder.ivImage.setVisibility(View.GONE);
        }else {
            holder.ivImage.setVisibility(View.VISIBLE);
            if(newList.get(position).getFeedType().equalsIgnoreCase("image")){
                holder.ivPlayVideo.setVisibility(View.GONE);
            }else if(newList.get(position).getFeedType().equalsIgnoreCase("video")){
                holder.ivPlayVideo.setVisibility(View.VISIBLE);
                holder.ivPlayVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, PlayVideoActivity.class);
                        i.putExtra("video",AppConstants.BASE_URL + newList.get(position).getVideoUrl());
                        context.startActivity(i);
                    }
                });
            }
        }
        holder.tvLikesCount.setText("Likes : " +newList.get(position).getLikes());
        if(newList.get(position).getFeedType().equalsIgnoreCase("text")){
            holder.llEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, AddNewStoryActivity.class);
                    i.putExtra("FeedDetails",newList.get(position));
                    context.startActivity(i);
                }
            });
        }else {
            if(newList.get(position).getFeedType().equalsIgnoreCase("image")){
                holder.llEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context, AddNewStoryActivity.class);
                        i.putExtra("FeedDetails",newList.get(position));
                        i.putExtra("is_Image",true);
                        context.startActivity(i);
                    }
                });
            }else if(newList.get(position).getFeedType().equalsIgnoreCase("video")){
                holder.llEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context, AddVideoFeedActivity.class);
                        i.putExtra("FeedDetails",newList.get(position));
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
                                    requestObjet.put("feed_id", newList.get(position).getFeedId());
                                    requestObjet.put("charity_id", newList.get(position).getCharityId());

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
                                            newList.remove(position);
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
        return newList.size();
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
