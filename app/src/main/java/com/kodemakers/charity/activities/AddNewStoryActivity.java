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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.CharityStoriesAdapter;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.FileUtil;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.FeedsDetails;
import com.kodemakers.charity.model.FeedsResponse;
import com.kodemakers.charity.model.StatusResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

public class AddNewStoryActivity extends AppCompatActivity {

    TextView tvAdd;
    EditText edtTitle,edtDetails;
    LinearLayout llUploadImage;
    ImageView imgUploadImage;
    FeedsDetails list;
    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;
    boolean is_Image;
    boolean is_Add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_story);
        is_Add = getIntent().getBooleanExtra("is_Add",false);
        is_Image = getIntent().getBooleanExtra("is_Image",false);
        setToolbar();
        initViews();
        loadData();

    }

    void initViews(){
//        pb = findViewById(R.id.pb);
        edtTitle = findViewById(R.id.edtTitle);
        edtDetails = findViewById(R.id.edtDetails);
        tvAdd = findViewById(R.id.tvAdd);
        llUploadImage = findViewById(R.id.llUploadImage);
        imgUploadImage = findViewById(R.id.imgUploadImage);
        if(is_Add) {
            if (is_Image) {
                llUploadImage.setVisibility(View.VISIBLE);
                tvAdd.setText("Add Image Feed");
            } else {
                llUploadImage.setVisibility(View.GONE);
                tvAdd.setText("Add Text Feed");
            }
        }
        else{
            list = (FeedsDetails) getIntent().getSerializableExtra("FeedDetails");
            if (is_Image) {
                llUploadImage.setVisibility(View.VISIBLE);
                Glide.with(AddNewStoryActivity.this).load(AppConstants.BASE_URL+list.getImageUrl()).into(imgUploadImage);
//                Picasso.with(AddNewStoryActivity.this).load(AppConstants.BASE_URL + list.getImageUrl()).into(imgUploadImage, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        pb.setVisibility(View.GONE);
//                        imgUploadImage.setVisibility(View.VISIBLE);
//                    }
//                    @Override
//                    public void onError() {
//                        pb.setVisibility(View.GONE);
//                        imgUploadImage.setVisibility(View.VISIBLE);
//                    }
//                });
                tvAdd.setText("Edit Image Feed");
            } else {
                llUploadImage.setVisibility(View.GONE);
                tvAdd.setText("Edit Text Feed");
            }
            edtTitle.setText(list.getTitle());
            edtDetails.setText(list.getDetails());
        }
    }

    private void loadData(){

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupclick();
            }
        });

        llUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });
    }
    private void signupclick(){
       /* if(is_Add) {
            if (is_Image) {
                if (compressedImage == null) {
                    Toast.makeText(AddNewStoryActivity.this, "please select an image", Toast.LENGTH_SHORT).show();
                } else if (edtTitle.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the title", Toast.LENGTH_SHORT).show();
                } else if (edtDetails.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the details", Toast.LENGTH_SHORT).show();
                } else {
                    postimage();
                }
            } else {
                if (edtTitle.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the title", Toast.LENGTH_SHORT).show();
                } else if (edtDetails.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the details", Toast.LENGTH_SHORT).show();
                } else {
                    posttext();
                }
            }
        }
        else{
            if (is_Image) {
                if (edtTitle.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the title", Toast.LENGTH_SHORT).show();
                } else if (edtDetails.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the details", Toast.LENGTH_SHORT).show();
                } else {
                    postUpdateimage();
                }
            } else {
                if (edtTitle.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the title", Toast.LENGTH_SHORT).show();
                } else if (edtDetails.length() == 0) {
                    Toast.makeText(AddNewStoryActivity.this, "please enter the details", Toast.LENGTH_SHORT).show();
                } else {
                    postUpdatetext();
                }
            }
        }*/
         if (edtTitle.length() == 0) {
            Toast.makeText(AddNewStoryActivity.this, "please enter the title", Toast.LENGTH_SHORT).show();
        } else if (edtDetails.length() == 0) {
            Toast.makeText(AddNewStoryActivity.this, "please enter the details", Toast.LENGTH_SHORT).show();
        }else {
            if (is_Add) {
                if (is_Image) {
                    if (compressedImage == null) {
                        Toast.makeText(AddNewStoryActivity.this, "please select an image", Toast.LENGTH_SHORT).show();
                    }else {
                        postimage();
                    }
                } else {
                        posttext();
                }
            } else {
                if (is_Image) {
                        postUpdateimage();
                } else {
                        postUpdatetext();
                }
            }
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private void postimage() {

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(AddNewStoryActivity.this);
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
                jsonObject.put("charity_id", PrefUtils.getUser(AddNewStoryActivity.this).getCharityId());
                jsonObject.put("title", edtTitle.getText().toString().trim());
                jsonObject.put("details", edtDetails.getText().toString().trim());
                jsonObject.put("image", "data:image/jpeg;base64," + encoded2 + "");
            } catch (JSONException e) {

            }
            new PostServiceCall(AppConstants.IMAGE_FEEDS, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
//                    progressDialog.dismiss();
                    StatusResponse feedsResponse = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    
                }

                @Override
                public void error(String error) {
//                    progressDialog.dismiss();
                    Toast.makeText(AddNewStoryActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(AddNewStoryActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
    private void posttext() {

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(AddNewStoryActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("charity_id", PrefUtils.getUser(AddNewStoryActivity.this).getCharityId());
                jsonObject.put("title", edtTitle.getText().toString().trim());
                jsonObject.put("details", edtDetails.getText().toString().trim());
            } catch (JSONException e) {

            }
            new PostServiceCall(AppConstants.TEXT_FEEDS, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
//                    progressDialog.dismiss();
                    StatusResponse feedsResponse = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void error(String error) {
//                    progressDialog.dismiss();
                    Toast.makeText(AddNewStoryActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(AddNewStoryActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
    private void postUpdateimage() {
        if (isNetworkConnected()) {
            final ProgressDialog progressDialog = new ProgressDialog(AddNewStoryActivity.this);
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
                jsonObject.put("feed_id", list.getFeedId());
                jsonObject.put("charity_id", PrefUtils.getUser(AddNewStoryActivity.this).getCharityId());
                jsonObject.put("title", edtTitle.getText().toString().trim());
                jsonObject.put("details", edtDetails.getText().toString().trim());
                jsonObject.put("image", "data:image/jpeg;base64," + encoded2 + "");
            } catch (JSONException e) {

            }
            new PostServiceCall(AppConstants.EDIT_IMAGE_FEEDS, jsonObject) {
                @Override
                public void response(String response) {
                    Log.e("response", response);
//                    progressDialog.dismiss();
                    StatusResponse feedsResponse = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void error(String error) {
//                    progressDialog.dismiss();
                    Toast.makeText(AddNewStoryActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(AddNewStoryActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
    private void postUpdatetext() {

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(AddNewStoryActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("feed_id", list.getFeedId());
                jsonObject.put("charity_id", PrefUtils.getUser(AddNewStoryActivity.this).getCharityId());
                jsonObject.put("title", edtTitle.getText().toString().trim());
                jsonObject.put("details", edtDetails.getText().toString().trim());
            } catch (JSONException e) {

            }
            new PostServiceCall(AppConstants.EDIT_TEXT_FEEDS, jsonObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
//                    progressDialog.dismiss();
                    StatusResponse feedsResponse = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    if (feedsResponse.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddNewStoryActivity.this, feedsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void error(String error) {
//                    progressDialog.dismiss();
                    Toast.makeText(AddNewStoryActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(AddNewStoryActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
            actualImage = FileUtil.from(AddNewStoryActivity.this, data.getData());
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
            Toast.makeText(AddNewStoryActivity.this, "Please choose an image!", Toast.LENGTH_SHORT).show();

        } else {
            new Compressor(AddNewStoryActivity.this)
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
            if(is_Add) {
                if (is_Image) {
                    toolbar.setTitle("Add Image Feed");
                } else {
                    toolbar.setTitle("Add Text Feed");
                }
            }
            else {
                if (is_Image) {
                    toolbar.setTitle("Edit Image Feed");
                } else {
                    toolbar.setTitle("Edit Text Feed");
                }
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
