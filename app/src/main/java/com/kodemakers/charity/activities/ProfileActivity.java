package com.kodemakers.charity.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.FileUtil;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {
    EditText edtMobileNo,edtAddress;
    ImageView ivBackArrow;
    TextView tvSave,tvSetprofilepic,tvAdmin,tvModerator,tvStaff;
    CircleImageView imgUploadImage;
    String gender = "";

    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setToolbar();

        initViews();
        loadData();
        setMaleFemale();
    }

    void initViews(){
        edtAddress = findViewById(R.id.edtAddress);
        edtMobileNo = findViewById(R.id.edtMobileNo);
        ivBackArrow =findViewById(R.id.ivBackArrow);
        tvSave = findViewById(R.id.tvSave);
        tvSetprofilepic = findViewById(R.id.tvSetprofilepic);
        imgUploadImage = findViewById(R.id.imgUploadImage);
        tvAdmin = findViewById(R.id.tvAdmin);
        tvModerator = findViewById(R.id.tvModerator);
        tvStaff = findViewById(R.id.tvStaff);
    }

    private void loadData(){

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

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

        tvAdmin.setBackground(getResources().getDrawable(R.drawable.button_background));
        tvAdmin.setTextColor(getResources().getColor(R.color.white));
        tvModerator.setBackground(getResources().getDrawable(R.drawable.blue_border));
        tvModerator.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvStaff.setBackground(getResources().getDrawable(R.drawable.blue_border));
        tvStaff.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void setMaleFemale() {

        tvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tvAdmin.setBackground(getResources().getDrawable(R.drawable.button_background));
                    tvAdmin.setTextColor(getResources().getColor(R.color.white));
                    tvModerator.setBackground(getResources().getDrawable(R.drawable.blue_border));
                    tvModerator.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvStaff.setBackground(getResources().getDrawable(R.drawable.blue_border));
                    tvStaff.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                gender = "admin";
            }
        });
        tvModerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tvAdmin.setBackground(getResources().getDrawable(R.drawable.blue_border));
                    tvAdmin.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvModerator.setBackground(getResources().getDrawable(R.drawable.button_background));
                    tvModerator.setTextColor(getResources().getColor(R.color.white));
                    tvStaff.setBackground(getResources().getDrawable(R.drawable.blue_border));
                    tvStaff.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                gender = "moderator";
            }
        });

        tvStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tvAdmin.setBackground(getResources().getDrawable(R.drawable.blue_border));
                    tvAdmin.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvModerator.setBackground(getResources().getDrawable(R.drawable.blue_border));
                    tvModerator.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvStaff.setBackground(getResources().getDrawable(R.drawable.button_background));
                    tvStaff.setTextColor(getResources().getColor(R.color.white));
                }
                gender = "staff";
            }
        });
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
