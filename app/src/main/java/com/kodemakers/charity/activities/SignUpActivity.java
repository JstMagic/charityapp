package com.kodemakers.charity.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kodemakers.charity.R;
import com.kodemakers.charity.app.Config;

public class SignUpActivity extends AppCompatActivity {

    EditText edtName, edtEmail;
    TextView tvRegister;
    ImageView ivBackArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        loadData();
    }

    void initViews() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        tvRegister = findViewById(R.id.tvRegister);
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }

    private void loadData() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                } else if (edtEmail.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(SignUpActivity.this, ProfileActivity.class);
                    i.putExtra("name", edtName.getText().toString());
                    i.putExtra("email", edtEmail.getText().toString());
                    startActivity(i);
                }
            }
        });

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
