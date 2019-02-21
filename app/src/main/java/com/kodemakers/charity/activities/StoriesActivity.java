package com.kodemakers.charity.activities;

import android.app.Dialog;
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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.CharityStoriesAdapter;
import com.kodemakers.charity.adapter.StoryDetailsAdapter;
import com.kodemakers.charity.adapter.UserDetailsAdapter;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.ContextMenuAdapter;
import com.kodemakers.charity.custom.ContextMenuItem;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.FeedsResponse;
import com.kodemakers.charity.model.StoryDetails;
import com.kodemakers.charity.model.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    CharityStoriesAdapter charityStoriesAdapter;
    private FloatingActionButton fabBtnAdd;
    Menu menu;
    Dialog customDialog;
    LayoutInflater inflater;
    View child;
    ListView listView;
    List<ContextMenuItem> contextMenuItems;
    ContextMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void loadData() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(StoriesActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        getCharityFeeds();

        fabBtnAdd = (FloatingActionButton)findViewById(R.id.fabBtnAdd);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void getCharityFeeds() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("charity_id", PrefUtils.getUser(StoriesActivity.this).getCharityId());
            jsonObject.put("user_id", PrefUtils.getUser(StoriesActivity.this).getCharityId());
        } catch (JSONException e) {

        }

        if (isNetworkConnected()) {

//            progressDialog = new ProgressDialog(getContext());
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
            new PostServiceCall(AppConstants.GET_FEEDS, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
//                    progressDialog.dismiss();

                    FeedsResponse feedsResponse = new GsonBuilder().create().fromJson(response, FeedsResponse.class);
                    charityStoriesAdapter = new CharityStoriesAdapter(StoriesActivity.this, feedsResponse.getFeeds(),feedsResponse.getLikes());
                    recyclerView.setAdapter(charityStoriesAdapter);

                }

                @Override
                public void error(String error) {
//                    progressDialog.dismiss();
                    Toast.makeText(StoriesActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(StoriesActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
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
        getCharityFeeds();
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
            toolbar.setTitle("Feeds");
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

    void customDialog(){
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        child = inflater.inflate(R.layout.listview_context_menu, null);
        listView = (ListView) child.findViewById(R.id.listView_context_menu);

        contextMenuItems = new ArrayList<ContextMenuItem>();
        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(
                R.drawable.photo), "Image"));

        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(
                R.drawable.text), "Message"));

        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(
                R.drawable.video), "Video"));


        adapter = new ContextMenuAdapter(StoriesActivity.this,
                contextMenuItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                customDialog.dismiss();
                Intent i;
                switch (position) {
                    case 0:
                        i = new Intent(StoriesActivity.this, AddNewStoryActivity.class);
                        i.putExtra("is_Image",true);
                        i.putExtra("is_Add",true);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(StoriesActivity.this, AddNewStoryActivity.class);
                        i.putExtra("is_Add",true);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(StoriesActivity.this, AddVideoFeedActivity.class);
                        i.putExtra("type","add");
                        startActivity(i);
                        break;
                    default:
                }
            }
        });
        customDialog = new Dialog(StoriesActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(child);
        customDialog.show();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_post, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        //find out which menu item was pressed
        Intent i;
        switch (item.getItemId()) {
            case R.id.gallery:
                i = new Intent(StoriesActivity.this, AddNewStoryActivity.class);
                startActivity(i);
                return true;
            case R.id.text:
                i = new Intent(StoriesActivity.this, AddNewStoryActivity.class);
                startActivity(i);
                return true;
            case R.id.video:
                i = new Intent(StoriesActivity.this, AddVideoFeedActivity.class);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }



}
