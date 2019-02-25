package com.kodemakers.charity.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.CharityStoriesAdapter;
import com.kodemakers.charity.adapter.IntroPagesAdapter;
import com.kodemakers.charity.adapter.StaffDetailsAdapter;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.FeedsResponse;
import com.kodemakers.charity.model.StaffResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StaffListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    StaffDetailsAdapter staffDetailsAdapter;
    TextView tvEmptyView;
    private FloatingActionButton fabBtnAdd;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyView = findViewById(R.id.tvEmptyView);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void getCharityStaff() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("charity_id", PrefUtils.getUser(StaffListActivity.this).getCharityId());
            Log.e("request for staff", jsonObject+"");
        } catch (JSONException e) {

        }
        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(StaffListActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.GET_STAFF, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response for staff", response);
                    progressDialog.dismiss();

                    StaffResult feedsResponse = new GsonBuilder().create().fromJson(response, StaffResult.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        staffDetailsAdapter = new StaffDetailsAdapter(StaffListActivity.this, feedsResponse.getResult());
                        recyclerView.setAdapter(staffDetailsAdapter);
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
                    Toast.makeText(StaffListActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(StaffListActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(StaffListActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        getCharityStaff();
        fabBtnAdd = (FloatingActionButton)findViewById(R.id.fabBtnAdd);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StaffListActivity.this,AddStaffActivity.class);
                i.putExtra("type", "add");
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_icon, menu);
        this.menu = menu;

        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        getCharityStaff();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            MenuItem search = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
            search(searchView);

            ImageView searchClose = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
            searchClose.setImageResource(R.drawable.ic_clear);
            return true;
        }



        return super.onOptionsItemSelected(item);

    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//                rvPosts.getFilter().filter(newText);
                return true;
            }
        });
    }


    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Staff List");
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
