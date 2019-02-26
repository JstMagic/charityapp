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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.FileUtil;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.CharityResponse;
import com.kodemakers.charity.model.StatusResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpdateCharityDetailsActivity extends AppCompatActivity {

    EditText edtCharityName,edtEmail,edtMobile,edtAddress
            ,edtLocation2,edtNameonAccount,edtIfscCode,
            edtBankName,edtAccountNo,edtPaypalEmail;
    TextView tvUpdate;
    LinearLayout llUploadImage;
    ImageView imgUploadImage;

    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;

    CharityResponse charityResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_charity_details);
        setToolbar();
        charityResponse = (CharityResponse) getIntent().getSerializableExtra("charityResponse");
        initViews();
        loadData();
    }

    void initViews(){
        edtCharityName = findViewById(R.id.edtCharityName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtAddress = findViewById(R.id.edtAddress);
        edtLocation2 = findViewById(R.id.edtLocation2);
        edtNameonAccount = findViewById(R.id.edtUCharityNameOnAccount);
        edtIfscCode = findViewById(R.id.edtUCharityifsccode);
        edtBankName = findViewById(R.id.edtUCharityBankName);
        edtAccountNo = findViewById(R.id.edtUCharityAccountNo);
        edtPaypalEmail = findViewById(R.id.edtUCharityPaypalEmail);
        tvUpdate = findViewById(R.id.tvUpdateCharityDetails);
        llUploadImage = findViewById(R.id.llUploadImage);
        imgUploadImage = findViewById(R.id.imgUploadImage);
    }

    private void loadData(){
        edtCharityName.setText(charityResponse.getCharityName());
        edtEmail.setText(charityResponse.getEmail());
        edtMobile.setText(charityResponse.getMobile());
        edtAddress.setText(charityResponse.getCharityAddress());
        edtLocation2.setText(charityResponse.getLatitude()+", "+charityResponse.getLongitude());
        edtNameonAccount.setText(charityResponse.getCharitynameinaccount());
        edtIfscCode.setText(charityResponse.getCharityifsccode());
        edtBankName.setText(charityResponse.getCharitybankname());
        edtAccountNo.setText(charityResponse.getCharityaccountno());
        edtPaypalEmail.setText(charityResponse.getCharitypaypalemail());
        Glide.with(UpdateCharityDetailsActivity.this).load(AppConstants.BASE_URL+charityResponse.getImage()).into(imgUploadImage);
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupClick();
            }
        });
        llUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });
    }
    public void signupClick(){
        if(edtCharityName.getText().toString().length()==0){

        }else if(edtEmail.getText().toString().length()==0){

        }else if(edtMobile.getText().toString().length()==0){

        }else if(edtAddress.getText().toString().length()==0){

        }else if(edtNameonAccount.getText().toString().length()==0){

        }else if(edtIfscCode.getText().toString().length()==0){

        }else if(edtBankName.getText().toString().length()==0){

        }else if(edtAccountNo.getText().toString().length()==0){

        }else if(edtPaypalEmail.getText().toString().length()==0){

        }else {
            postimage();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private void postimage() {
        if (isNetworkConnected()) {
            final ProgressDialog progressDialog = new ProgressDialog(UpdateCharityDetailsActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            String encoded2 = null;
            try {
                encoded2 = null;
                byte[] b1 = new byte[0];
                try {
                    b1 = new byte[(int) compressedImage.length()];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(compressedImage);
                    fileInputStream2.read(b1);

                } catch (FileNotFoundException e) {
                    System.out.println("File Not Found.");
                    e.printStackTrace();
                } catch (IOException e1) {
                    System.out.println("Error Reading The File.");
                    e1.printStackTrace();
                }
                encoded2 = Base64.encodeToString(b1, Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("charity_id", PrefUtils.getUser(UpdateCharityDetailsActivity.this).getCharityId());
                jsonObject.put("charity_name", edtCharityName.getText().toString().trim());
                jsonObject.put("mobile", edtMobile.getText().toString().trim());
                jsonObject.put("charity_address", edtAddress.getText().toString().trim());
                jsonObject.put("nameonacc", edtNameonAccount.getText().toString().trim());
                jsonObject.put("ifsc", edtIfscCode.getText().toString().trim());
                jsonObject.put("bankname", edtBankName.getText().toString().trim());
                jsonObject.put("accountno", edtAccountNo.getText().toString().trim());
                jsonObject.put("paypalemail", edtPaypalEmail.getText().toString().trim());
                jsonObject.put("image", "data:image/jpeg;base64," + encoded2 + "");
            } catch (JSONException e) {

            }
            new PostServiceCall(AppConstants.UPDATE_CHARITY_DETAILS, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
//                    progressDialog.dismiss();
                    StatusResponse feedsResponse = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(UpdateCharityDetailsActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        charityResponse.setCharityName(edtCharityName.getText().toString().trim());
                        charityResponse.setMobile(edtMobile.getText().toString().trim());
                        charityResponse.setCharityAddress(edtAddress.getText().toString().trim());
                        charityResponse.setCharitynameinaccount(edtNameonAccount.getText().toString().trim());
                        charityResponse.setCharityifsccode(edtIfscCode.getText().toString().trim());
                        charityResponse.setCharitybankname(edtBankName.getText().toString().trim());
                        charityResponse.setCharityaccountno(edtAccountNo.getText().toString().trim());
                        charityResponse.setCharitypaypalemail(edtPaypalEmail.getText().toString().trim());
                        PrefUtils.setUser(charityResponse,UpdateCharityDetailsActivity.this);
                        Intent in = new Intent(UpdateCharityDetailsActivity.this,MainActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(in);
                    } else {
                        Toast.makeText(UpdateCharityDetailsActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void error(String error) {
                      progressDialog.dismiss();
                    Toast.makeText(UpdateCharityDetailsActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(UpdateCharityDetailsActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
            actualImage = FileUtil.from(UpdateCharityDetailsActivity.this, data.getData());
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
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please choose an image!", Toast.LENGTH_SHORT).show();

        } else {
            new Compressor(UpdateCharityDetailsActivity.this)
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            compressedImage = file;
                            Log.e("com","1");


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
            toolbar.setTitle("Update Details");
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
