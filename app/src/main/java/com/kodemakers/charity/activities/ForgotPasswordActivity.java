package com.kodemakers.charity.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.PostServiceCall;
import com.kodemakers.charity.model.StatusResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView tvSubmit;
    ImageView ivBackArrow;
    EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tvSubmit = findViewById(R.id.tvSubmit);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        edtEmail = findViewById(R.id.edtEmail);

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().length() == 0) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (isValidEmail(edtEmail.getText().toString())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else {
                    forgotPassword();
                }
            }
        });
    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void forgotPassword() {

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("email", edtEmail.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            new PostServiceCall(AppConstants.FORGOT_PASSWORD, requestObject) {

                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    StatusResponse userData = new GsonBuilder().create().fromJson(response, StatusResponse.class);
                    Toast.makeText(ForgotPasswordActivity.this, userData.getMessage() + "", Toast.LENGTH_SHORT).show();
                    if (userData.getStatus().equalsIgnoreCase("1")) {
                        finish();
                    }

                }

                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "Technical Problem, try again later", Toast.LENGTH_SHORT).show();
                }
            }.call();
        } else {
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
}
