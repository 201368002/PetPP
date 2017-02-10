package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tacademy.petpp.base.BaseActivity;

public class TermsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // actList에 B를 추가해줍니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }

    public void onAgreeBtn(View view){
        Intent intent = new Intent(this, PeopleProfileActivity.class);
        startActivity(intent);

    }

}

