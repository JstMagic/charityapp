package com.kodemakers.charity.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.CharityResponse;
import com.kodemakers.charity.model.StatusResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    LinearLayout llUsers, llCharities, llStories, llStaff, llDonations, llAccount, llIntroSteppers, llNotifications;
    TextView tvCharityName,tvCharityStatusText;
    CharityResponse charityResponse;
    Switch switchCharityLive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        charityResponse = PrefUtils.getUser(MainActivity.this);
        setToolbar();
        initViews();
        loadData();

        //comment
    }
    void initViews() {
        switchCharityLive  =(Switch)findViewById(R.id.switchCharitylive);
        llUsers = findViewById(R.id.llUsers);
        llCharities = findViewById(R.id.llCharities);
        llStories = findViewById(R.id.llStories);
        llStaff = findViewById(R.id.llStaff);
        llDonations = findViewById(R.id.llDonations);
        llAccount = findViewById(R.id.llAccount);
        llIntroSteppers = findViewById(R.id.llIntroSteppers);
        llNotifications = findViewById(R.id.llNotifications);
        tvCharityName = findViewById(R.id.tvCharityNameDashboard);
        tvCharityStatusText = findViewById(R.id.tvCharityStatustext);
        if (charityResponse.getCharityName().length() != 0) {
            tvCharityName.setText(charityResponse.getCharityName());
        }
        if(charityResponse.getIsLive().equalsIgnoreCase("1")){
            tvCharityStatusText.setText("Charity is Live");
            switchCharityLive.setChecked(true);
        }
        else{
            tvCharityStatusText.setText("Make Charity Live");
            switchCharityLive.setChecked(false);
        }

    }

    private void loadData() {
        llUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UsersListActivity.class);
                startActivity(i);
            }
        });

        llCharities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UpdateCharityDetailsActivity.class);
                startActivity(i);
            }
        });

        llStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, StoriesActivity.class);
                startActivity(i);
            }
        });

        llStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, StaffListActivity.class);
                startActivity(i);
            }
        });

        llDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DonationActivity.class);
                startActivity(i);
            }
        });

        llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*/* Intent i = new Intent(MainActivity.this, UpdateCharityDetailsActivity.class);
                startActivity(i);*/
            }
        });

        llIntroSteppers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, IntroSteppersActivity.class);
                startActivity(i);
            }
        });

        llNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });
        switchCharityLive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    changecharitylivestatus("1",true,"Charity is live");
                }
                else{
                    changecharitylivestatus("0",false,"Make Charity live");
                }
            }
        });

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public void changecharitylivestatus(final String status, final boolean isChecked, final String livestring){
        if (isNetworkConnected()) {
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            String encoded2 = null;
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("charity_id", PrefUtils.getUser(MainActivity.this).getCharityId());
                jsonObject.put("status", status);
            } catch (JSONException e) {

            }
            new PostServiceCall(AppConstants.MAKE_CHARITY_LIVE, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
//                    progressDialog.dismiss();
                    StatusResponse feedsResponse = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(MainActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        charityResponse.setIsLive(status);
                        switchCharityLive.setChecked(isChecked);
                        tvCharityStatusText.setText(livestring);
                        PrefUtils.setUser(charityResponse,MainActivity.this);
                    } else {
                        switchCharityLive.setChecked(!isChecked);
                        Toast.makeText(MainActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void error(String error) {
                    switchCharityLive.setChecked(!isChecked);
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            switchCharityLive.setChecked(!isChecked);
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            try {
                if (charityResponse.getCharityName().length() == 0) {
                    toolbar.setTitle(charityResponse.getName());
                    if (charityResponse.getType().equalsIgnoreCase("staff")) {
                        toolbar.setSubtitle("Staff");
                    }else {
                        toolbar.setSubtitle("Moderator");
                    }
                } else {
                    toolbar.setTitle(charityResponse.getCharityName());
                }
            } catch (Exception e) {
                toolbar.setTitle(charityResponse.getName());
                if (charityResponse.getType().equalsIgnoreCase("staff")) {
                    toolbar.setSubtitle("Staff");
                } else {
                    toolbar.setSubtitle("Moderator");
                }
            }
            toolbar.setTitleTextColor(Color.parseColor("#000000"));
            toolbar.setSubtitleTextColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_log_out) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you really want to Logout this App ?").setCancelable(false)
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PrefUtils.clearCurrentUser(MainActivity.this);
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }

                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Logout");
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        charityResponse = PrefUtils.getUser(MainActivity.this);
    }
}
