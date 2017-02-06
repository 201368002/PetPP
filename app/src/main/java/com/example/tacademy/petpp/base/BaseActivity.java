package com.example.tacademy.petpp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Tacademy on 2017-01-24.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // 공통 기능 1
    // primitive 타입
    // byte, short, int, long
    // float, double
    // boolean
    // char
    // 프로그레스바 선언
    private ProgressDialog pd;
    // 프로그레스바 처리
    // 프로그레스바 보이기
    public void showProgress(String msg)
    {
        // 객체를 1회만 생성한다.
        if( pd == null ) {
            // 생성한다.
            pd = new ProgressDialog(this);
            // 백키로 닫는 기능을 제거한다.
            pd.setCancelable(false);
        }
        // 원하는 메시지를 세팅한다.
        pd.setMessage(msg);
        // 화면에 띠워라
        pd.show();
    }
    // 프로그레스바 숨기기
    public void hideProgress()
    {
        // 닫는다 : 객체가 존재하고, 보일때만
        if( pd != null && pd.isShowing() ) {
            pd.dismiss();
        }
    }





//
//    private final long FINISH_INTERVAL_TIME = 2000;
//    private long backPressedTime = 0;
//
//    @Override
//    public void onBackPressed() {
//        long tempTime = System.currentTimeMillis();
//        long intervalTime = tempTime - backPressedTime;
//
//        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
//        {
//            super.onBackPressed();
//        }
//        else
//        {
//            backPressedTime = tempTime;
//            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누르면 꺼버린다.", Toast.LENGTH_SHORT).show();
//        }
//    }
}