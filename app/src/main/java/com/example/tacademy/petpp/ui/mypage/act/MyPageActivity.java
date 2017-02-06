package com.example.tacademy.petpp.ui.mypage.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.ui.mypage.frag.MyProfileFragment;
import com.example.tacademy.petpp.ui.mypage.frag.MyReviewFragment;
import com.example.tacademy.petpp.ui.mypage.frag.MyViewFragment;

public class MyPageActivity extends BaseActivity {

    TabLayout myPageTabLayout;
    ViewPager myPageView;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        myPageTabLayout = (TabLayout) findViewById(R.id.myPageTabLayout);
        myPageView = (ViewPager)findViewById(R.id.myPageView);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        myPageView.setAdapter(fragmentAdapter);
        myPageTabLayout.setupWithViewPager(myPageView);
    }

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
        finish();
    }

}
