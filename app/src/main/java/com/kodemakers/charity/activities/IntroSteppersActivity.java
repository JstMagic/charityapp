package com.kodemakers.charity.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.IntroPagesAdapter;
import com.kodemakers.charity.adapter.StoryDetailsAdapter;
import com.kodemakers.charity.model.IntroPagesDetails;
import com.kodemakers.charity.model.StoryDetails;

import java.util.ArrayList;

public class IntroSteppersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    IntroPagesAdapter introPagesAdapter;
    private FloatingActionButton fabBtnAdd;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_steppers);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void loadData() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(IntroSteppersActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<IntroPagesDetails> newList = new ArrayList<>();

        newList.add(new IntroPagesDetails(R.drawable.charity_image));
        newList.add(new IntroPagesDetails(R.drawable.charity_image));
        newList.add(new IntroPagesDetails(R.drawable.charity_image));
        newList.add(new IntroPagesDetails(R.drawable.charity_image));

        introPagesAdapter =new IntroPagesAdapter(IntroSteppersActivity.this, newList);
        recyclerView.setAdapter(introPagesAdapter);

        fabBtnAdd = (FloatingActionButton)findViewById(R.id.fabBtnAdd);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(IntroSteppersActivity.this,AddIntroPageActivity.class);
                startActivity(i);
            }
        });
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
