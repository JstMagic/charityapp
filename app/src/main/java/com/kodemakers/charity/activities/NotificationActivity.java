package com.kodemakers.charity.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.NotificationsAdapter;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.NotificationResponse;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    NotificationsAdapter notificationsAdapter;
    TextView tvEmptyView;
    ArrayList<NotificationResponse> notificationDetailModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setToolbar();
        initViews();
        try {
            notificationDetailModels = PrefUtils.getNotification(NotificationActivity.this).notificationResponseArrayList;
        } catch (Exception e) {
            notificationDetailModels = new ArrayList<NotificationResponse>();
        }
        loadData();
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyView = findViewById(R.id.tvEmptyView);
    }

    private void loadData() {
        if (notificationDetailModels.size() > 0) {

            notificationsAdapter = new NotificationsAdapter(NotificationActivity.this, notificationDetailModels);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(notificationsAdapter);

            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);

        } else {
            recyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Notifications");
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setTitleTextColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

}
