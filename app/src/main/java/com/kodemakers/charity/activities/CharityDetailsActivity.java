package com.kodemakers.charity.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.CharityResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CharityDetailsActivity extends AppCompatActivity {

    TextView tvCharityName,tvEmail,tvMobile,tvAddress,
            tvLocation,tvCharityNameonAccount,tvCharityIfsc,
            tvCharityBankName,tvCharityAccountNumber,tvCharityPaypalEmail,tvUpdateCharity;
    ImageView ivCharityImage;
    CharityResponse charityResponse;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_details);
        charityResponse = PrefUtils.getUser(CharityDetailsActivity.this);
        setToolbar();
        initViews();
        loadData();
    }
    void initViews(){
        tvCharityName = findViewById(R.id.tvCharityName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvMobile);
        tvAddress = findViewById(R.id.tvAddress);
        ivCharityImage = findViewById(R.id.ivCharityImage);
        tvLocation = findViewById(R.id.tvCharityLocation);
        tvCharityNameonAccount = findViewById(R.id.tvCharityNameOnAccount);
        tvCharityIfsc = findViewById(R.id.tvCharityifsccode);
        tvCharityBankName = findViewById(R.id.tvCharityBankName);
        tvCharityAccountNumber = findViewById(R.id.tvCharityAccountNo);
        tvCharityPaypalEmail = findViewById(R.id.tvCharityPaypalEmail);
        tvUpdateCharity = findViewById(R.id.tvCharityUpdate);
        pb = findViewById(R.id.pb);
    }
    private void loadData(){
        tvCharityName.setText(charityResponse.getCharityName());
        tvEmail.setText(charityResponse.getEmail());
        tvMobile.setText(charityResponse.getMobile());
        tvAddress.setText(charityResponse.getCharityAddress());
        //Glide.with(CharityDetailsActivity.this).load(AppConstants.BASE_URL+charityResponse.getImage()).into(ivCharityImage);
        Picasso.with(CharityDetailsActivity.this).load(AppConstants.BASE_URL + charityResponse.getImage()).into(ivCharityImage, new Callback() {
            @Override
            public void onSuccess() {
                pb.setVisibility(View.GONE);
                ivCharityImage.setVisibility(View.VISIBLE);
            }
            @Override
            public void onError() {
                pb.setVisibility(View.GONE);
                ivCharityImage.setVisibility(View.VISIBLE);
            }
        });
        tvLocation.setText(charityResponse.getLatitude()+", "+charityResponse.getLongitude());
        tvCharityNameonAccount.setText(charityResponse.getCharitynameinaccount());
        tvCharityIfsc.setText(charityResponse.getCharityifsccode());
        tvCharityBankName.setText(charityResponse.getCharitybankname());
        tvCharityAccountNumber.setText(charityResponse.getCharityaccountno());
        tvCharityPaypalEmail.setText(charityResponse.getCharitypaypalemail());
        tvUpdateCharity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharityDetailsActivity.this,UpdateCharityDetailsActivity.class);
                intent.putExtra("charityResponse",charityResponse);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent i = new Intent(CharityDetailsActivity.this,UpdateCharityDetailsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Charity Details");
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
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        charityResponse = PrefUtils.getUser(CharityDetailsActivity.this);

    }
}
