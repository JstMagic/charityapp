package com.kodemakers.charity.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.NotificationsAdapter;
import com.kodemakers.charity.model.NotificationData;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    NotificationsAdapter notificationsAdapter;
    TextView tvEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyView = findViewById(R.id.tvEmptyView);
    }



    private void loadData() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(NotificationActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<NotificationData> newList = new ArrayList<>();

        newList.add(new NotificationData("Notification Title","Notification Message","21-01-2019"));
        newList.add(new NotificationData("Title","Message","21-01-2019"));
        newList.add(new NotificationData("Title","Message","21-01-2019"));
        newList.add(new NotificationData("Title","Message","21-01-2019"));


        notificationsAdapter =new NotificationsAdapter(NotificationActivity.this, newList);
        recyclerView.setAdapter(notificationsAdapter);


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
