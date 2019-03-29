package com.kodemakers.charity.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.FileUtil;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.InterSlidersDetails;
import com.kodemakers.charity.model.StaffDetails;
import com.kodemakers.charity.model.StatusResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

public class AddStaffActivity extends AppCompatActivity {

    EditText edtName, edtEmail;
    TextView tvSetImage, tvAdd, tvModerator, tvStaff;
    CircleImageView imgUser;
    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;
    StaffDetails staffDetails;
    String type;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        setToolbar();
        type=null;
        initViews();
        loadData();
    }
    void initViews() {
        pb = findViewById(R.id.pb);
        edtName = findViewById(R.id.edtStaffName);
        edtEmail = findViewById(R.id.edtStaffEmail);
        tvSetImage = findViewById(R.id.tvSetImageStaff);
        imgUser = findViewById(R.id.imgStaff);
        tvAdd = findViewById(R.id.tvAddStaff);
        tvModerator  = findViewById(R.id.tvtypeModerator);
        tvStaff = findViewById(R.id.tvtypeStaff);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public void updateUI() {
        tvModerator.setBackground(getResources().getDrawable(R.drawable.blue_border));
        tvModerator.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvStaff.setBackground(getResources().getDrawable(R.drawable.blue_border));
        tvStaff.setTextColor(getResources().getColor(R.color.colorPrimary));
        if (type.equalsIgnoreCase("staff")) {
            tvStaff.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvStaff.setTextColor(getResources().getColor(R.color.white));
        } else {
            tvModerator.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvModerator.setTextColor(getResources().getColor(R.color.white));
        }
    }
    private void loadData() {

        tvModerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "moderator";
                updateUI();
            }
        });

        tvStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "staff";
                updateUI();
            }
        });

        if (getIntent().getStringExtra("type").equalsIgnoreCase("edit")) {
            staffDetails = (StaffDetails) getIntent().getSerializableExtra("details");
            edtName.setText(staffDetails.getName());
            edtEmail.setText(staffDetails.getEmail());
            type = staffDetails.getType();
            updateUI();
            tvAdd.setText("Edit");
            Glide.with(AddStaffActivity.this).load(AppConstants.BASE_URL + staffDetails.getImage()).into(imgUser);
//            Picasso.with(AddStaffActivity.this).load(AppConstants.BASE_URL + AddStaffActivity.this).into(imgUser, new Callback() {
//                @Override
//                public void onSuccess() {
//                    pb.setVisibility(View.GONE);
//                    imgUser.setVisibility(View.VISIBLE);
//                }
//                @Override
//                public void onError() {
//                    pb.setVisibility(View.GONE);
//                    imgUser.setVisibility(View.VISIBLE);
//                }
//            });
        }

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compressedImage == null && getIntent().getStringExtra("type").equalsIgnoreCase("add")) {
                    Toast.makeText(AddStaffActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                } else if (edtName.getText().toString().length() == 0) {
                    Toast.makeText(AddStaffActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                } else if (edtEmail.getText().toString().length() == 0) {
                    Toast.makeText(AddStaffActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                } else if (isValidEmail(edtEmail.getText().toString())) {
                    Toast.makeText(AddStaffActivity.this, "Please enter Valid  Email", Toast.LENGTH_SHORT).show();
                } else if (type == null) {
                    Toast.makeText(AddStaffActivity.this, "Please Select type", Toast.LENGTH_SHORT).show();
                }else {
                    if (getIntent().getStringExtra("type").equalsIgnoreCase("add")) {
                        add();
                    } else {
                        edit();
                    }
                }
            }
        });
        tvSetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });
    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void add() {

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
            requestObject.put("charity_id", PrefUtils.getUser(AddStaffActivity.this).getCharityId());
            requestObject.put("name", edtName.getText().toString());
            requestObject.put("type",type);
            requestObject.put("email", edtEmail.getText().toString());
            requestObject.put("image", "data:image/jpeg;base64," + encoded1 + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("response", requestObject.toString());

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(AddStaffActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.ADD_STAFF, requestObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    StatusResponse userData = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    Toast.makeText(AddStaffActivity.this, userData.getMessage() + "", Toast.LENGTH_SHORT).show();

                    if (userData.getStatus().equalsIgnoreCase("1")) {
                        finish();
                    }
                }
                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(AddStaffActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(AddStaffActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void edit() {

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
            requestObject.put("staff_id", staffDetails.getStaffId());
            requestObject.put("charity_id", PrefUtils.getUser(AddStaffActivity.this).getCharityId());
            requestObject.put("name", edtName.getText().toString());
            requestObject.put("type",type);
            requestObject.put("email", edtEmail.getText().toString());
            requestObject.put("image", "data:image/jpeg;base64," + encoded1 + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("response for edit", requestObject.toString());

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(AddStaffActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.EDIT_STAFF, requestObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    StatusResponse userData = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    Toast.makeText(AddStaffActivity.this, userData.getMessage() + "", Toast.LENGTH_SHORT).show();

                    if (userData.getStatus().equalsIgnoreCase("1")) {
                        finish();
                    }

                }

                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(AddStaffActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(AddStaffActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
            actualImage = FileUtil.from(AddStaffActivity.this, data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        compressImage();

    }

    private void setCompressedImage() {

        imgUser.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
    }

    public void compressImage() {
        if (actualImage == null) {
            Toast.makeText(AddStaffActivity.this, "Please choose an image!", Toast.LENGTH_SHORT).show();

        } else {
            new Compressor(AddStaffActivity.this)
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
            if (getIntent().getStringExtra("type").equalsIgnoreCase("edit")) {
                toolbar.setTitle("Edit Staff");
            }else {
                toolbar.setTitle("Add Staff");
            }
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
