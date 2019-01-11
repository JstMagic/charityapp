package com.kodemakers.charity.activities;

import android.content.Intent;
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
import com.kodemakers.charity.adapter.StaffDetailsAdapter;
import com.kodemakers.charity.adapter.StoryDetailsAdapter;
import com.kodemakers.charity.model.StaffDetails;
import com.kodemakers.charity.model.StoryDetails;

import java.util.ArrayList;

public class StaffListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    StaffDetailsAdapter staffDetailsAdapter;
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
    }

    private void loadData() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(StaffListActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<StaffDetails> newList = new ArrayList<>();

        newList.add(new StaffDetails("ABC",R.drawable.hourse,"moderator"));
        newList.add(new StaffDetails("XYZ",R.drawable.hourse,"staff"));
        newList.add(new StaffDetails("PQR",R.drawable.hourse,"admin"));
        newList.add(new StaffDetails("STU",R.drawable.hourse,"moderator"));
        newList.add(new StaffDetails("ASD",R.drawable.hourse,"staff"));


        staffDetailsAdapter =new StaffDetailsAdapter(StaffListActivity.this, newList);
        recyclerView.setAdapter(staffDetailsAdapter);

        fabBtnAdd = (FloatingActionButton)findViewById(R.id.fabBtnAdd);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i =new Intent(StaffListActivity.this,AddNewStoryActivity.class);
//                startActivity(i);
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
            toolbar.setTitle("Staff List");
            setSupportActionBar(toolbar);
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
