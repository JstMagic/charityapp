package com.kodemakers.charity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kodemakers.charity.R;

public class AccountDetailsActivity extends AppCompatActivity {
    EditText edtNameOnAccount,edtIfscCode,edtAddress,edtBankName,edtAccountType;
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        setToolbar();
        initViews();
        loadData();
    }

    void initViews(){
        edtNameOnAccount = findViewById(R.id.edtNameOnAccount);
        edtIfscCode = findViewById(R.id.edtIfscCode);
        edtAddress = findViewById(R.id.edtAddress);
        edtBankName = findViewById(R.id.edtBankName);
        edtAccountType = findViewById(R.id.edtAccountType);
        tvSave = findViewById(R.id.tvSave);
    }

    private void loadData(){
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountDetailsActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Account Details");
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
