package com.kodemakers.charity.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.StoryDetailsAdapter;
import com.kodemakers.charity.adapter.UserDetailsAdapter;
import com.kodemakers.charity.model.StoryDetails;
import com.kodemakers.charity.model.UserDetails;

import java.util.ArrayList;

public class StoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    StoryDetailsAdapter storyDetailsAdapter;
    private FloatingActionButton fabBtnAdd;
    Menu menu;

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

        ArrayList<StoryDetails> newList = new ArrayList<>();

        newList.add(new StoryDetails("Story 1",R.drawable.charity_image,"09-01-2019"));
        newList.add(new StoryDetails("Story 2",R.drawable.charity_image,"09-01-2019"));
        newList.add(new StoryDetails("Story 3",R.drawable.charity_image,"09-01-2019"));
        newList.add(new StoryDetails("Story 4",R.drawable.charity_image,"09-01-2019"));
        newList.add(new StoryDetails("Story 5",R.drawable.charity_image,"09-01-2019"));


        storyDetailsAdapter =new StoryDetailsAdapter(StoriesActivity.this, newList);
        recyclerView.setAdapter(storyDetailsAdapter);

        fabBtnAdd = (FloatingActionButton)findViewById(R.id.fabBtnAdd);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StoriesActivity.this,AddNewStoryActivity.class);
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
            toolbar.setTitle("Stories");
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
