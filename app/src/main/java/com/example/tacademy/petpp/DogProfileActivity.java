package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.model.ChatMember;
import com.example.tacademy.petpp.model.Member;
import com.example.tacademy.petpp.ui.main.act.MainTLActivity;
import com.example.tacademy.petpp.util.Log;
import com.example.tacademy.petpp.util.StorageHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DogProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // actList에 B를 추가해줍니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);
    }

    // 모두 입력 확인 후 서버에 저장하고 메인 화면으로 이동
    public void onDProfileNext(View view){

        onUserSaved();

    }

    // 서버에 저장하지 않고 메인 화면으로 이동
    public void onTLNext(View view){
        Intent intent = new Intent(this, MainTLActivity.class);
        startActivity(intent);
    }

    // 회원 정보 디비에 저장
    public void onUserSaved(){

        // 1. 로딩
        showProgress("회원가입중...");
        // 2. 디비에 회원정보 입력

        // 회원정보 생성
        String kakaoId  = Member.getInstance().getKakaoId();
        String name     = Member.getInstance().getName();
        ChatMember chatMember = new ChatMember(kakaoId, name);

        // 디비 입력
        databaseReference.child("users").child(kakaoId).setValue(chatMember)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideProgress();
                        if(task.isSuccessful()){
                            // 디비 저장 완료. 메인화면으로 이동

                            // 다음 앱에 들어올 때에 타임라인으로 바로 가기 위한 작업
                            StorageHelper.getInstance().setBoolean(DogProfileActivity.this, "login", true);
                            Log.getInstance().log("자동로그인 상태 true인가여?" + StorageHelper.getInstance().getBoolean(DogProfileActivity.this, "login"));

                            Log.getInstance().signLog("회원정보 입력 모두 완료, 메인화면으로 이동");

                            Intent intent = new Intent(DogProfileActivity.this, MainTLActivity.class);
                            startActivity(intent);
                            actFinish();
                        }
                        else{
                            // 회원정보 디비 저장 실패
                            Log.getInstance().signLog("회원정보 디비 저장 실패");
                        }
                    }
                });
    }
}
