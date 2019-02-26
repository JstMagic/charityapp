package com.kodemakers.charity.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.AddIntroPageActivity;
import com.kodemakers.charity.activities.AddStaffActivity;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.model.StaffDetails;
import com.kodemakers.charity.model.StatusResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffDetailsAdapter extends RecyclerView.Adapter<StaffDetailsAdapter.RecViewHolder>{

    Context context;
    List<StaffDetails> newList;
    List<StaffDetails> mFilteredList;


    public StaffDetailsAdapter(Context context, List<StaffDetails> newList) {
        this.context = context;
        this.newList = newList;
        this.mFilteredList = newList;
    }

    @Override
    public StaffDetailsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_charity_staff,parent,false);
        return new StaffDetailsAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffDetailsAdapter.RecViewHolder holder, final int position) {
        holder.ivUpdateStaff.setColorFilter(Color.parseColor("#000000"));
        holder.ivDeleteStaff.setColorFilter(Color.parseColor("#000000"));
        holder.tvUserName.setText(newList.get(position).getName());
        holder.tvRoleName.setText(newList.get(position).getType());
        Glide.with(context).load(AppConstants.BASE_URL+newList.get(position).getImage()).into(holder.civUserImage);
        holder.ivDeleteStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CLick ","Yes");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to delete this staff").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final ProgressDialog progressDialog;
                                progressDialog = new ProgressDialog(context);
                                progressDialog.setMessage("Deleting...");
                                progressDialog.show();

                                JSONObject requestObjet = new JSONObject();
                                try {
                                    requestObjet.put("staff_id", newList.get(position).getStaffId());
                                    requestObjet.put("charity_id", newList.get(position).getCharityId());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                new PostServiceCall(AppConstants.DELETE_STAFF, requestObjet) {

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
                dialog.setTitle("Delete Staff");
                dialog.show();
            }
        });
        holder.ivUpdateStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddStaffActivity.class);
                i.putExtra("type", "edit");
                i.putExtra("details", newList.get(position));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{

        TextView tvUserName,tvRoleName;
        CircleImageView civUserImage;
        ImageView ivForwardArrow,ivUpdateStaff,ivDeleteStaff;

        public RecViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvRoleName = itemView.findViewById(R.id.tvRoleName);
            civUserImage = itemView.findViewById(R.id.civUserImage);
            ivForwardArrow = itemView.findViewById(R.id.ivForwardArrow);
            ivUpdateStaff = itemView.findViewById(R.id.ivUpdateStaff);
            ivDeleteStaff  = itemView.findViewById(R.id.ivDeleteStaff);
        }
    }

}
