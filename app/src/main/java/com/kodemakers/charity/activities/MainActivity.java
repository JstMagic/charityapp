package com.kodemakers.charity.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.CharityResponse;

public class MainActivity extends AppCompatActivity {

    LinearLayout llUsers, llCharities, llStories, llStaff, llDonations, llAccount, llIntroSteppers, llNotifications;
    TextView tvCharityName;
    CharityResponse charityResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        charityResponse = PrefUtils.getUser(MainActivity.this);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews() {
        llUsers = findViewById(R.id.llUsers);
        llCharities = findViewById(R.id.llCharities);
        llStories = findViewById(R.id.llStories);
        llStaff = findViewById(R.id.llStaff);
        llDonations = findViewById(R.id.llDonations);
        llAccount = findViewById(R.id.llAccount);
        llIntroSteppers = findViewById(R.id.llIntroSteppers);
        llNotifications = findViewById(R.id.llNotifications);
        tvCharityName = findViewById(R.id.tvCharityNameDashboard);
        if (charityResponse.getCharityName().length() != 0) {
            tvCharityName.setText(charityResponse.getCharityName());
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
                Intent i = new Intent(MainActivity.this, CharityDetailsActivity.class);
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
                Intent i = new Intent(MainActivity.this, AccountDetailsActivity.class);
                startActivity(i);
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
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            try {
                if (charityResponse.getCharityName().length() == 0) {
                    toolbar.setTitle(charityResponse.getName());
                    if (charityResponse.getType().equalsIgnoreCase("staff")) {
                        toolbar.setSubtitle("Staff");
                    } else {
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
