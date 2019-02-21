package com.kodemakers.charity.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.app.Config;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.CharityResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPwd;
    TextView tvSignIn, tvForgotPwd;
    LinearLayout llnewuser;
    //notification id
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String regId;
    String type = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            if (PrefUtils.getUser(LoginActivity.this).getCharityId().length() != 0) {
                Intent y = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(y);
                finish();
            }
        } catch (Exception e) {

        }

        //getting notification id
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    regId = pref.getString("regId", null);

                }
            }
        };

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        initViews();
        loadData();
    }

    void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPwd = findViewById(R.id.edtPwd);
        tvSignIn = findViewById(R.id.tvSignIn);
        llnewuser = findViewById(R.id.llnewuser);
//        tvAdmin = findViewById(R.id.tvAdmin);
//        tvModerator = findViewById(R.id.tvModerator);
//        tvStaff = findViewById(R.id.tvStaff);
        tvForgotPwd = findViewById(R.id.tvForgotPwd);
    }

    private void loadData() {

//        tvAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = "admin";
//                updateUI();
//            }
//        });
//
//        tvModerator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = "moderator";
//                updateUI();
//            }
//        });
//
//        tvStaff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = "staff";
//                updateUI();
//            }
//        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
                } else if (edtPwd.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    if (type.equalsIgnoreCase("admin")) {
                        loginAdmin();
                    } else {
                        loginOther();
                    }
                }
            }
        });

        llnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }

//    public void updateUI() {
//        tvAdmin.setBackground(getResources().getDrawable(R.drawable.blue_border));
//        tvAdmin.setTextColor(getResources().getColor(R.color.colorPrimary));
//        tvModerator.setBackground(getResources().getDrawable(R.drawable.blue_border));
//        tvModerator.setTextColor(getResources().getColor(R.color.colorPrimary));
//        tvStaff.setBackground(getResources().getDrawable(R.drawable.blue_border));
//        tvStaff.setTextColor(getResources().getColor(R.color.colorPrimary));
//        if (type.equalsIgnoreCase("admin")) {
//            tvAdmin.setBackground(getResources().getDrawable(R.drawable.button_background));
//            tvAdmin.setTextColor(getResources().getColor(R.color.white));
//        } else if (type.equalsIgnoreCase("staff")) {
//            tvStaff.setBackground(getResources().getDrawable(R.drawable.button_background));
//            tvStaff.setTextColor(getResources().getColor(R.color.white));
//        } else {
//            tvModerator.setBackground(getResources().getDrawable(R.drawable.button_background));
//            tvModerator.setTextColor(getResources().getColor(R.color.white));
//        }
//    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void loginAdmin() {

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("email", edtEmail.getText().toString());
            requestObject.put("password", edtPwd.getText().toString());
            requestObject.put("notification_id", regId + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.LOGIN, requestObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    CharityResponse userData = new GsonBuilder().create().fromJson(response, CharityResponse.class);
                    Toast.makeText(LoginActivity.this, userData.getMessage() + "", Toast.LENGTH_SHORT).show();

                    if (userData.getStatus().equalsIgnoreCase("1")) {
                        PrefUtils.setUser(userData, LoginActivity.this);
                        Intent y = new Intent(LoginActivity.this, MainActivity.class);
                        y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(y);
                        finish();
                    }

                }

                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginOther() {

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("email", edtEmail.getText().toString());
            requestObject.put("password", edtPwd.getText().toString());
            requestObject.put("type", type + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.LOGIN_OTHER, requestObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    CharityResponse userData = new GsonBuilder().create().fromJson(response, CharityResponse.class);
                    Toast.makeText(LoginActivity.this, userData.getMessage() + "", Toast.LENGTH_SHORT).show();

                    if (userData.getStatus().equalsIgnoreCase("1")) {
                        PrefUtils.setUser(userData, LoginActivity.this);
                        Intent y = new Intent(LoginActivity.this, MainActivity.class);
                        y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(y);
                        finish();
                    }

                }

                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

}
