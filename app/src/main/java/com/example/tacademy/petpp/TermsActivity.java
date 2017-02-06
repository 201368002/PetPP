package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tacademy.petpp.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.agreeButton)
    void agreeButton(View view){
        Intent intent = new Intent(this, PeopleProfileActivity.class);
        startActivity(intent);

    }

}

