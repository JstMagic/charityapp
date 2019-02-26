package com.kodemakers.charity.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
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
import com.kodemakers.charity.adapter.StaffDetailsAdapter;
import com.kodemakers.charity.adapter.UserDetailsAdapter;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.FollowersList;
import com.kodemakers.charity.model.StaffResult;
import com.kodemakers.charity.model.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    UserDetailsAdapter userDetailsAdapter;
    TextView tvEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(UsersListActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        getFollowers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_icon, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        ImageView searchClose = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_clear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

                userDetailsAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void getFollowers() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("charity_id", PrefUtils.getUser(UsersListActivity.this).getCharityId());
            Log.e("request for staff", jsonObject+"");
        } catch (JSONException e) {

        }
        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(UsersListActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.FOLLOWERS_LIST, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response for staff", response);
                    progressDialog.dismiss();

                    FollowersList feedsResponse = new GsonBuilder().create().fromJson(response, FollowersList.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        userDetailsAdapter =new UserDetailsAdapter(UsersListActivity.this, feedsResponse.getFollowers());
                        recyclerView.setAdapter(userDetailsAdapter);

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
                    Toast.makeText(UsersListActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(UsersListActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Followers");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
