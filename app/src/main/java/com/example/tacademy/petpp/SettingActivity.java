package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tacademy.petpp.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    // 버전정보
    public void onVersion(View view){
        Intent intent = new Intent(this, VersionActivity.class);
        startActivity(intent);
    }
}
