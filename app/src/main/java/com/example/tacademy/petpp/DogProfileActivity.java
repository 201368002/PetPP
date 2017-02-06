package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.ui.main.act.MainTLActivity;

public class DogProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);
    }

    // 모두 입력 확인 후 서버에 저장하고 메인 화면으로 이동
    public void onDProfileNext(View view){
        Intent intent = new Intent(this, MainTLActivity.class);
        startActivity(intent);
    }

    // 서버에 저장하지 않고 메인 화면으로 이동
    public void onTLNext(View view){
        Intent intent = new Intent(this, MainTLActivity.class);
        startActivity(intent);
    }

}
