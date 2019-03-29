package com.kodemakers.charity.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.DonationDetailAdapter;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.DonationList;

import org.json.JSONException;
import org.json.JSONObject;

public class DonationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tvEmptyView;
    DonationDetailAdapter transactionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DonationActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        getFollowers();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void getFollowers() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("charity_id", PrefUtils.getUser(DonationActivity.this).getCharityId());
            Log.e("request", jsonObject.toString());
        } catch (JSONException e) {

        }
        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(DonationActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.TRANSACTIONS_LIST, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response for staff", response);
                    progressDialog.dismiss();

                    DonationList donationList = new GsonBuilder().create().fromJson(response, DonationList.class);
                    if (donationList.getStatus().equalsIgnoreCase("1")) {

                        transactionListAdapter = new DonationDetailAdapter(DonationActivity.this, donationList.getDonationDetails());
                        recyclerView.setAdapter(transactionListAdapter);

                        recyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void error(String error) {
//                    progressDialog.dismiss();
                    Toast.makeText(DonationActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(DonationActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Donation Details");
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setTitleTextColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getIntent().getBooleanExtra("from_notification", false)) {
                        Intent i = new Intent(DonationActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("from_notification", false)) {
            Intent i = new Intent(DonationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
