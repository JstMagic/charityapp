package com.kodemakers.charity.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.AddIntroPageActivity;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.model.InterSlidersDetails;
import com.kodemakers.charity.model.StatusResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class IntroPagesAdapter extends RecyclerView.Adapter<IntroPagesAdapter.RecViewHolder> {

    Context context;
    List<InterSlidersDetails> newList;

    public IntroPagesAdapter(Context context, List<InterSlidersDetails> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public IntroPagesAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_intro_pages, parent, false);
        return new IntroPagesAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IntroPagesAdapter.RecViewHolder holder, final int position) {

        Glide.with(context).load(AppConstants.BASE_URL + newList.get(position).getImage()).into(holder.ivImage);

        holder.ivDelete.setColorFilter(Color.parseColor("#03a9f4"));
        holder.ivEdit.setColorFilter(Color.parseColor("#03a9f4"));

        holder.llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddIntroPageActivity.class);
                i.putExtra("type", "edit");
                i.putExtra("details", newList.get(position));
                context.startActivity(i);
            }
        });

        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Delete Intro Slider");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do You want to delete this Intro Slider?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                final ProgressDialog progressDialog;
                                progressDialog = new ProgressDialog(context);
                                progressDialog.setMessage("Deleting...");
                                progressDialog.show();

                                JSONObject requestObjet = new JSONObject();
                                try {
                                    requestObjet.put("id", newList.get(position).getId());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                new PostServiceCall(AppConstants.DELETE_INTRO_SLIDER, requestObjet) {

                                    @Override
                                    public void response(String response) {
                                        progressDialog.dismiss();
                                        StatusResponse categoryList = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                                        Toast.makeText(context, categoryList.getMessage(), Toast.LENGTH_SHORT).show();
                                        if (categoryList.getStatus().equalsIgnoreCase("1")) {
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
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

        });
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage, ivDelete, ivEdit;
        LinearLayout llDelete, llEdit;

        public RecViewHolder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            llEdit = itemView.findViewById(R.id.llEdit);
            llDelete = itemView.findViewById(R.id.llDelete);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);

        }
    }

}
