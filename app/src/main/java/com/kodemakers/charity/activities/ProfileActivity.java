package com.kodemakers.charity.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.app.Config;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.FileUtil;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.model.StatusResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {

    EditText edtMobileNo;
    ImageView ivBackArrow;
    TextView tvSave, tvSetprofilepic;
    CircleImageView imgUploadImage;
    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;
    //notification id
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setToolbar();

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
        edtMobileNo = findViewById(R.id.edtMobileNo);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        tvSave = findViewById(R.id.tvSave);
        tvSetprofilepic = findViewById(R.id.tvSetprofilepic);
        imgUploadImage = findViewById(R.id.imgUploadImage);
    }

    private void loadData() {

        tvSetprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compressedImage == null) {
                    Toast.makeText(ProfileActivity.this, "Please select profile image", Toast.LENGTH_SHORT).show();
                } else if (edtMobileNo.getText().toString().length() == 0) {
                    Toast.makeText(ProfileActivity.this, "Please enter mobile", Toast.LENGTH_SHORT).show();
                } else {
                    register();
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void register() {

        JSONObject requestObject = new JSONObject();

        String encoded1 = null;
        try {
            encoded1 = null;
            byte[] b1 = new byte[0];
            try {
                b1 = new byte[(int) compressedImage.length()];
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                FileInputStream fileInputStream1 = new FileInputStream(compressedImage);
                fileInputStream1.read(b1);


            } catch (FileNotFoundException e) {
                System.out.println("File Not Found.");
                e.printStackTrace();
            } catch (IOException e1) {
                System.out.println("Error Reading The File.");
                e1.printStackTrace();
            }
            encoded1 = Base64.encodeToString(b1, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            requestObject.put("charity_name", getIntent().getStringExtra("name"));
            requestObject.put("email", getIntent().getStringExtra("email"));
            requestObject.put("mobile", edtMobileNo.getText().toString());
            requestObject.put("notification_id", regId + "");
            requestObject.put("image", "data:image/jpeg;base64," + encoded1 + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("response", requestObject.toString());

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.REGISTRATION, requestObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    StatusResponse userData = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    Toast.makeText(ProfileActivity.this, userData.getMessage() + "", Toast.LENGTH_SHORT).show();

                    if (userData.getStatus().equalsIgnoreCase("1")) {
                        Intent i = new Intent(ProfileActivity.this, RegistrationSuccessScreenActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }

                }

                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(ProfileActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        try {
            actualImage = FileUtil.from(ProfileActivity.this, data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        compressImage();
    }

    private void setCompressedImage() {
        imgUploadImage.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
    }

    public void compressImage() {
        if (actualImage == null) {
            Toast.makeText(ProfileActivity.this, "Please choose an image!", Toast.LENGTH_SHORT).show();

        } else {
            new Compressor(ProfileActivity.this)
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            compressedImage = file;
                            Log.e("com", "1");


                            setCompressedImage();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            Log.e("error", throwable.getMessage());
//                            showError(throwable.getMessage());
                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Profile");
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
