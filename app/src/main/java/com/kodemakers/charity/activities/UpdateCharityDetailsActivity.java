package com.kodemakers.charity.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
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
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpdateCharityDetailsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    EditText edtCharityName, edtEmail, edtMobile, edtAddress, edtLocation2, edtLocation1, edtNameonAccount, edtIfscCode,
            edtBankName, edtAccountNo, edtPaypalEmail;
    TextView tvUpdate, tvAdoption, tvDonation, tvCause;
    LinearLayout llUploadImage, llcharityType;
    ImageView imgUploadImage;
    ProgressDialog progressDialog;
    private int SELECT_FILE = 1;
    private File actualImage;
    private File compressedImage;
    String type;

    CharityResponse charityResponse;
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest locationRequest;
    public LatLng gps;
    String latitude = "", longitude = "";
    boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_charity_details);
        progressDialog = new ProgressDialog(UpdateCharityDetailsActivity.this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setToolbar();
        //charityResponse = (CharityResponse) getIntent().getSerializableExtra("charityResponse");
        charityResponse = PrefUtils.getUser(UpdateCharityDetailsActivity.this);
        type = charityResponse.getType();
        initViews();
        loadData();
        edtLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                getCurrentLocation();
            }
        });

        edtLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }

    public void updateUI() {
        tvAdoption.setBackground(getResources().getDrawable(R.drawable.blue_border));
        tvAdoption.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvDonation.setBackground(getResources().getDrawable(R.drawable.blue_border));
        tvDonation.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvCause.setBackground(getResources().getDrawable(R.drawable.blue_border));
        tvCause.setTextColor(getResources().getColor(R.color.colorPrimary));

        if (type.equalsIgnoreCase("1")) {
            tvAdoption.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvAdoption.setTextColor(getResources().getColor(R.color.white));
        } else if (type.equalsIgnoreCase("2")) {
            tvDonation.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvDonation.setTextColor(getResources().getColor(R.color.white));
        } else if (type.equalsIgnoreCase("3")) {
            tvCause.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvCause.setTextColor(getResources().getColor(R.color.white));
        }
    }


    public void getLocation() {
        List<com.google.android.libraries.places.api.model.Place.Field> fields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ADDRESS, Place.Field.LAT_LNG);
//
//        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, 123);
    }

    private void getCurrentLocation() {

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (gps != null) {

                    progressDialog.dismiss();

                    latitude = gps.latitude + "";
                    longitude = gps.longitude + "";

                    edtLocation2.setText(latitude + ", " + longitude);

                } else {
                    if (!isFirst) {
                        isFirst = true;
                        EnableGPSAutoMatically();
                    }

                    if (ActivityCompat.checkSelfPermission(UpdateCharityDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UpdateCharityDetailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());

                    new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            isFirst = false;
                            getCurrentLocation();
                        }
                    }.start();
                }
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void EnableGPSAutoMatically() {
        GoogleApiClient googleApiClient = null;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:

                            Log.e("SUCCESS", "yes ");
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.e("RESOLUTION_REQUIRED", "yes");

//                            toast("GPS is not on");
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(UpdateCharityDetailsActivity.this, 1000);

                            } catch (Exception e) {

                                Log.e("Exception", e.toString());

                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.e("SETTINGS_CHANGE", "yes");

//                            toast("Setting change not allowed");
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                mLastLocation = locationResult.getLastLocation();
                gps = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }
        }
    };

    void initViews() {
        edtCharityName = findViewById(R.id.edtCharityName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtAddress = findViewById(R.id.edtAddress);
        edtLocation1 = findViewById(R.id.edtLocation1);
        edtLocation2 = findViewById(R.id.edtLocation2);
        edtNameonAccount = findViewById(R.id.edtUCharityNameOnAccount);
        edtIfscCode = findViewById(R.id.edtUCharityifsccode);
        edtBankName = findViewById(R.id.edtUCharityBankName);
        edtAccountNo = findViewById(R.id.edtUCharityAccountNo);
        edtPaypalEmail = findViewById(R.id.edtUCharityPaypalEmail);
        tvUpdate = findViewById(R.id.tvUpdateCharityDetails);
        llUploadImage = findViewById(R.id.llUploadImage);
        imgUploadImage = findViewById(R.id.imgUploadImage);
        tvAdoption = findViewById(R.id.tvAdoption);
        tvDonation = findViewById(R.id.tvDonation);
        tvCause = findViewById(R.id.tvCause);
        llcharityType = findViewById(R.id.llcharityType);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loadData() {
        edtCharityName.setText(charityResponse.getCharityName());
        edtEmail.setText(charityResponse.getEmail());
        edtMobile.setText(charityResponse.getMobile());
        edtAddress.setText(charityResponse.getCharityAddress());
        edtLocation2.setText(charityResponse.getLatitude() + ", " + charityResponse.getLongitude());
//        edtLocation1.setText(charityResponse.getLatitude()+", "+charityResponse.getLongitude());
        edtNameonAccount.setText(charityResponse.getCharitynameinaccount());
        edtIfscCode.setText(charityResponse.getCharityifsccode());
        edtBankName.setText(charityResponse.getCharitybankname());
        edtAccountNo.setText(charityResponse.getCharityaccountno());
        edtPaypalEmail.setText(charityResponse.getCharitypaypalemail());
        Glide.with(UpdateCharityDetailsActivity.this).load(AppConstants.BASE_URL + charityResponse.getImage()).into(imgUploadImage);

        if (charityResponse.getCharityType().equalsIgnoreCase("1")) {
            tvAdoption.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvAdoption.setTextColor(getResources().getColor(R.color.white));
        } else if (charityResponse.getCharityType().equalsIgnoreCase("2")) {
            tvDonation.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvDonation.setTextColor(getResources().getColor(R.color.white));
        } else if (charityResponse.getCharityType().equalsIgnoreCase("3")) {
            tvCause.setBackground(getResources().getDrawable(R.drawable.button_background));
            tvCause.setTextColor(getResources().getColor(R.color.white));
        }

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

        tvAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "1";
                updateUI();
            }
        });

        tvDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "2";
                updateUI();
            }
        });

        tvCause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "3";
                updateUI();
            }
        });

    }

    public void signupClick() {
        if (edtCharityName.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Name", Toast.LENGTH_SHORT).show();
        } else if (edtEmail.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Email", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(edtEmail.getText().toString())) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Valid Email", Toast.LENGTH_SHORT).show();
        } else if (edtMobile.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Mobile", Toast.LENGTH_SHORT).show();
        } else if (edtAddress.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Address", Toast.LENGTH_SHORT).show();
        } else if (edtNameonAccount.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Account Name", Toast.LENGTH_SHORT).show();
        } else if (edtIfscCode.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Ifsc Code", Toast.LENGTH_SHORT).show();
        } else if (edtBankName.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Bank Name", Toast.LENGTH_SHORT).show();
        } else if (edtAccountNo.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity Account No", Toast.LENGTH_SHORT).show();
        } else if (edtPaypalEmail.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter Charity paypal email", Toast.LENGTH_SHORT).show();
        } else if (edtLocation1.getText().toString().length() == 0 & edtLocation2.getText().toString().length() == 0) {
            Toast.makeText(UpdateCharityDetailsActivity.this, "Please enter location or get current location", Toast.LENGTH_SHORT).show();
        } else {
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
                jsonObject.put("charity_type", type);
                jsonObject.put("charity_name", edtCharityName.getText().toString().trim());
                jsonObject.put("mobile", edtMobile.getText().toString().trim());
                jsonObject.put("charity_address", edtAddress.getText().toString().trim());
                jsonObject.put("nameonacc", edtNameonAccount.getText().toString().trim());
                jsonObject.put("ifsc", edtIfscCode.getText().toString().trim());
                jsonObject.put("bankname", edtBankName.getText().toString().trim());
                jsonObject.put("accountno", edtAccountNo.getText().toString().trim());
                jsonObject.put("paypalemail", edtPaypalEmail.getText().toString().trim());
                jsonObject.put("image", "data:image/jpeg;base64," + encoded2 + "");
                jsonObject.put("latitude", latitude);
                jsonObject.put("longitude", longitude);
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
                        charityResponse.setCharityType(type);
                        charityResponse.setIs_profile_updated("1");
                        PrefUtils.setUser(charityResponse, UpdateCharityDetailsActivity.this);
                        Intent in = new Intent(UpdateCharityDetailsActivity.this, MainActivity.class);
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

        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("Location", "Place: " + place.getAddress());

                edtLocation1.setText(place.getAddress().toString());
                latitude = place.getLatLng().latitude + "";
                longitude = place.getLatLng().longitude + "";
                edtLocation2.setText(latitude + ", " + longitude);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("Location", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

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
