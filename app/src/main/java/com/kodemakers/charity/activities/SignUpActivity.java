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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kodemakers.charity.R;
import com.kodemakers.charity.adapter.RoleSpinnerAdapter;
import com.kodemakers.charity.custom.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {

    EditText edtMobile,edtName,edtEmail,edtPassword,edtConfirmPwd;
    Spinner spRole;
    TextView tvSignUp;
    LinearLayout llUploadImage;
    ImageView imgUploadImage;

    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews(){
        edtMobile = findViewById(R.id.edtMobile);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPwd = findViewById(R.id.edtConfirmPwd);
        spRole =findViewById(R.id.spRole);
        tvSignUp = findViewById(R.id.tvSignUp);
        llUploadImage = findViewById(R.id.llUploadImage);
        imgUploadImage = findViewById(R.id.imgUploadImage);
    }

    private void loadData(){
        loadSpinner();
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(i);
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

    private void loadSpinner(){
        ArrayList<String> role = new ArrayList<>();

        role.add("Admin");
        role.add("Moderator");
        role.add("Staff");

        spRole.setAdapter(new RoleSpinnerAdapter(SignUpActivity.this,role));
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
            actualImage = FileUtil.from(SignUpActivity.this, data.getData());
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
            Toast.makeText(SignUpActivity.this, "Please choose an image!", Toast.LENGTH_SHORT).show();

        } else {
            new Compressor(SignUpActivity.this)
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
            toolbar.setTitle("Sign Up");
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
