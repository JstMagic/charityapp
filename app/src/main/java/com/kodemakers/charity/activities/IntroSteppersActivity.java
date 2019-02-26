package com.kodemakers.charity.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.kodemakers.charity.adapter.IntroPagesAdapter;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.InterSlidersResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class IntroSteppersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    IntroPagesAdapter introPagesAdapter;
    private FloatingActionButton fabBtnAdd;
    TextView tvEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_steppers);
        setToolbar();
        initViews();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyView = findViewById(R.id.tvEmptyView);
        fabBtnAdd = findViewById(R.id.fabBtnAdd);
    }

    private void loadData() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(IntroSteppersActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void getData() {

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("charity_id", PrefUtils.getUser(IntroSteppersActivity.this).getCharityId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("response", requestObject.toString());

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(IntroSteppersActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.GET_INTRO_SLIDER, requestObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    final InterSlidersResponse userData = new GsonBuilder().create().fromJson(response, InterSlidersResponse.class);
                    if (userData.getStatus() == 1) {
                        introPagesAdapter = new IntroPagesAdapter(IntroSteppersActivity.this, userData.getResult());
                        recyclerView.setAdapter(introPagesAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);
                    }

                    fabBtnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (userData.getTotal() == 4) {
                                Toast.makeText(IntroSteppersActivity.this, "You can add maximum 4 intro sliders", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent i = new Intent(IntroSteppersActivity.this, AddIntroPageActivity.class);
                                i.putExtra("type", "add");
                                startActivity(i);
                            }
                        }
                    });
                }

                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(IntroSteppersActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(IntroSteppersActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Intro Stepper Detail");
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setTitleTextColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
