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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodemakers.charity.R;

public class CharityDetailsActivity extends AppCompatActivity {

    TextView tvCharityName,tvEmail,tvMobile,tvAddress;
    ImageView ivCharityImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_details);
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
    }

    private void loadData(){
        tvCharityName.setText("ABC Charity");
        tvEmail.setText("abc@gmail.com");
        tvMobile.setText("1010101010");
        tvAddress.setText("abc,xyz");
        Glide.with(CharityDetailsActivity.this).load(R.drawable.charity_image).into(ivCharityImage);
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
}
