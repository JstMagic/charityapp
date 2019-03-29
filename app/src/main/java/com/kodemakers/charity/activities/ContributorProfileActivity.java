package com.kodemakers.charity.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.model.FollowersItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContributorProfileActivity extends AppCompatActivity {

    CircleImageView ivProfileImage;
    FollowersItem followersDetails;
    TextView tvContributorName;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_profile);

        followersDetails = (FollowersItem) getIntent().getSerializableExtra("followersDetails");

        setToolbar();

        initViews();
        loadData();
    }

    void initViews() {
        pb = findViewById(R.id.pb);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvContributorName = findViewById(R.id.tvContributorName);
    }

    private void loadData() {
        tvContributorName.setText(followersDetails.getUsername());
        Picasso.with(ContributorProfileActivity.this).load(AppConstants.BASE_URL + followersDetails.getImage()).into(ivProfileImage, new Callback() {
            @Override
            public void onSuccess() {
                pb.setVisibility(View.GONE);
                ivProfileImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                pb.setVisibility(View.GONE);
                ivProfileImage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Profile");
            toolbar.setTitleTextColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
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
