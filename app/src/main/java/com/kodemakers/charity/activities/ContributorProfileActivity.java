package com.kodemakers.charity.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContributorProfileActivity extends AppCompatActivity {
    CircleImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_profile);
        setToolbar();

        initViews();
        loadData();
    }

    void initViews(){
        ivProfileImage = findViewById(R.id.ivProfileImage);
    }

    private void loadData(){
        Glide.with(ContributorProfileActivity.this).load(R.drawable.dummy_user).into(ivProfileImage);

    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("User Profile");
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
