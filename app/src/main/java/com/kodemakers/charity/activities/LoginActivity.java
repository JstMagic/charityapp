package com.kodemakers.charity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kodemakers.charity.R;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail,edtPwd;
    TextView tvSignIn;
    LinearLayout llnewuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        loadData();
    }

    void initViews(){
        edtEmail = findViewById(R.id.edtEmail);
        edtPwd = findViewById(R.id.edtPwd);
        tvSignIn = findViewById(R.id.tvSignIn);
        llnewuser = findViewById(R.id.llnewuser);
    }

    private void loadData(){

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        llnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }
}
