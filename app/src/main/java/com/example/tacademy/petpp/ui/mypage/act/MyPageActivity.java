package com.example.tacademy.petpp.ui.mypage.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.petpp.ChatActivity;
import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.ui.mypage.frag.MyProfileFragment;
import com.example.tacademy.petpp.ui.mypage.frag.MyReviewFragment;
import com.example.tacademy.petpp.ui.mypage.frag.MyViewFragment;
import com.example.tacademy.petpp.util.Log;
import com.example.tacademy.petpp.util.U;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MyPageActivity extends BaseActivity {

    TabLayout myPageTabLayout;
    ViewPager myPageView;
    FragmentAdapter fragmentAdapter;

    Button scheduleBtn, messageBtn, editProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        myPageTabLayout = (TabLayout) findViewById(R.id.myPageTabLayout);
        myPageView = (ViewPager)findViewById(R.id.myPageView);

        scheduleBtn = (Button)findViewById(R.id.scheduleBtn);
        messageBtn = (Button)findViewById(R.id.messageBtn);
        editProfileBtn = (Button)findViewById(R.id.editProfileBtn);

        // ======================= 이벤트 리스너 =============================

        // 산책 예약하기 버튼
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 예약 수락 거절 창 필요
                // 채팅 화면으로 이동
                Intent intent = new Intent(MyPageActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        // 메시지 전송 버튼
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 채팅 화면으로 이동
            }
        });

        // 프로필 수정 버튼
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 프로필 수정 화면으로 이동.
            }
        });

        // ==================================================================
        if(U.getInstance().getMyPageType() == true){   // 오른쪽 메뉴에서 마이피드페이지 넘어올 경우
            // 산책예약버튼, 메시지보내기 버튼 숨기기
            scheduleBtn.setVisibility(INVISIBLE);
            messageBtn.setVisibility(INVISIBLE);
            // 프로필 수정 버튼 보이기
            editProfileBtn.setVisibility(VISIBLE);
        }else{
            // 산책예약버튼, 메시지보내기 버튼 보이기
            scheduleBtn.setVisibility(VISIBLE);
            messageBtn.setVisibility(VISIBLE);
            // 프로필 수정 버튼 숨기기
            editProfileBtn.setVisibility(INVISIBLE);
        }
        // ===================================================================

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        myPageView.setAdapter(fragmentAdapter);
        myPageTabLayout.setupWithViewPager(myPageView);
    }

    // 프래그먼트 어댑터
    class FragmentAdapter extends FragmentPagerAdapter {

        // 프레그먼트 화면 개수 및 정의
        Fragment[] frags = new Fragment[]{
                new MyViewFragment(),
                new MyProfileFragment(),
                new MyReviewFragment()
        };

        // 프레그먼트 화면 제목 설정
        String[] titles = new String[]{
                "사진첩", "프로필", "산책후기"
        };

        // 생성자
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }

        // 오버라이드 getPageTitle
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }


    }

    // 이전 버튼
    public void onBACK(){
        Log.getInstance().log("백버튼.......");
    }

}
