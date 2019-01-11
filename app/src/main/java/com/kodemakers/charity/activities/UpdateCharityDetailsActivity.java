package com.kodemakers.charity.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.FileUtil;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpdateCharityDetailsActivity extends AppCompatActivity {

    EditText edtCharityName,edtEmail,edtMobile,edtAddress;
    TextView tvUpdate;
    LinearLayout llUploadImage;
    ImageView imgUploadImage;

    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_charity_details);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews(){
        edtCharityName = findViewById(R.id.edtCharityName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtAddress = findViewById(R.id.edtAddress);
        tvUpdate = findViewById(R.id.tvUpdate);
        llUploadImage = findViewById(R.id.llUploadImage);
        imgUploadImage = findViewById(R.id.imgUploadImage);
    }

    private void loadData(){

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
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
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

}
