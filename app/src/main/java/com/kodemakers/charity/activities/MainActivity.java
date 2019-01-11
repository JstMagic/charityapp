package com.kodemakers.charity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.kodemakers.charity.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout llUsers,llCharities,llStories,llStaff,llDonations,llAccount,llIntroSteppers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews(){
        llUsers = findViewById(R.id.llUsers);
        llCharities = findViewById(R.id.llCharities);
        llStories = findViewById(R.id.llStories);
        llStaff = findViewById(R.id.llStaff);
        llDonations = findViewById(R.id.llDonations);
        llAccount = findViewById(R.id.llAccount);
        llIntroSteppers = findViewById(R.id.llIntroSteppers);

    }

    private void loadData(){
        llUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,UsersListActivity.class);
                startActivity(i);
            }
        });

        llCharities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CharityDetailsActivity.class);
                startActivity(i);
            }
        });

        llStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,StoriesActivity.class);
                startActivity(i);
            }
        });

        llStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,StaffListActivity.class);
                startActivity(i);
            }
        });

        llDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DonationActivity.class);
                startActivity(i);
            }
        });

        llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AccountDetailsActivity.class);
                startActivity(i);
            }
        });

        llIntroSteppers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,IntroSteppersActivity.class);
                startActivity(i);
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Charity Name");
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
