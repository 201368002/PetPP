package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tacademy.petpp.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginButton)
    void loginButton(View view){
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);

    }
}
