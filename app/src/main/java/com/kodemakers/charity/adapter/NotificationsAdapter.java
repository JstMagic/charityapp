package com.kodemakers.charity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kodemakers.charity.R;
import com.kodemakers.charity.activities.LoginActivity;
import com.kodemakers.charity.activities.SplashActivity;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.NotificationDetailModelList;
import com.kodemakers.charity.model.NotificationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.RecViewHolder> {

    Context context;
    ArrayList<NotificationResponse> notificationDetailModels;
    NotificationDetailModelList notificationDetailModelList;

    public NotificationsAdapter(Context context, ArrayList<NotificationResponse> notificationDetailModels) {
        this.context = context;
        this.notificationDetailModels = notificationDetailModels;
    }

    @Override
    public NotificationsAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_notification, parent, false);


        return new NotificationsAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.RecViewHolder holder, final int position) {

        JSONObject json = null;
        JSONObject data = null;
        try {
            json = new JSONObject(notificationDetailModels.get(position).notificationData);
            data = json.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String title = null;

        String message = null;

        String timestamp = null;
        JSONObject payload = null;


        try {
            title = data.getString("title");
            message = data.getString("message");
            timestamp = data.getString("timestamp");
            payload = data.getJSONObject("payload");
        } catch (Exception e) {

        }

        holder.tvTitle.setText(title);
        holder.tvMessage.setText(message);
        holder.tvDate.setText(timestamp.substring(0, 10));

        final JSONObject finalData = data;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String click_action = null;
                JSONObject payload = null;

                try {
                    click_action = finalData.getString("click_action");
                    payload = finalData.getJSONObject("payload");
                } catch (Exception e) {
                }

                Intent resultIntent;
                if (click_action.equalsIgnoreCase("CHARITY_STATUS_NOTIFICATION")) {
                    resultIntent = new Intent(context, LoginActivity.class);
                    resultIntent.putExtra("from_notification", true);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                } else {
                    resultIntent = new Intent(context, SplashActivity.class);
                    resultIntent.putExtra("from_notification", true);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }

                try {
                    notificationDetailModelList = PrefUtils.getNotification(context);
                    notificationDetailModels = notificationDetailModelList.notificationResponseArrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                    notificationDetailModelList = new NotificationDetailModelList();
                    notificationDetailModels = new ArrayList<>();
                }

                try {
                    notificationDetailModels.remove(position);
                    notificationDetailModelList.notificationResponseArrayList = notificationDetailModels;
                    PrefUtils.setNotification(notificationDetailModelList, context);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", "adding");
                }
                context.startActivity(resultIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationDetailModels.size();
    }


    public class RecViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage, tvDate, tvTitle;

        public RecViewHolder(View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }


}
