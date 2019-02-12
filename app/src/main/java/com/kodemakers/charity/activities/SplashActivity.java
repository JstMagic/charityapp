package com.kodemakers.charity.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kodemakers.charity.R;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        permissions = new String[]{
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                INTERNET,
                ACCESS_NETWORK_STATE,
                READ_EXTERNAL_STORAGE,
        };

        if (!checkPermission()) {
            requestPermission();
        } else {
            nextIntent();
        }

    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    public boolean checkPermission() {
        int result0 = ContextCompat.checkSelfPermission(SplashActivity.this, permissions[0]);
        int result1 = ContextCompat.checkSelfPermission(SplashActivity.this, permissions[1]);
        int result2 = ContextCompat.checkSelfPermission(SplashActivity.this, permissions[2]);
        int result3 = ContextCompat.checkSelfPermission(SplashActivity.this, permissions[3]);
        int result4 = ContextCompat.checkSelfPermission(SplashActivity.this, permissions[4]);

        return result0 == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED &&
                result4 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean permission0 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permission1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean permission2 = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean permission3 = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean permission4 = grantResults[4] == PackageManager.PERMISSION_GRANTED;

                    if (permission0 && permission1 && permission2 && permission3 && permission4) {
                        Toast.makeText(SplashActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();

                        nextIntent();

                    } else {
                        Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void nextIntent() {
        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
}
