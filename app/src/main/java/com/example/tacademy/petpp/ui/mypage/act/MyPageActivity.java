package com.example.tacademy.petpp.ui.mypage.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tacademy.petpp.ChatActivity;
import com.example.tacademy.petpp.DogProfileActivity;
import com.example.tacademy.petpp.PeopleProfileActivity;
import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.ui.WriteAcitivity;
import com.example.tacademy.petpp.ui.mypage.frag.MyProfileFragment;
import com.example.tacademy.petpp.ui.mypage.frag.MyReviewFragment;
import com.example.tacademy.petpp.ui.mypage.frag.MyViewFragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.tacademy.petpp.R.mipmap.btn_gallery_off;
import static com.example.tacademy.petpp.R.mipmap.btn_gallery_on;
import static com.example.tacademy.petpp.R.mipmap.btn_profile_off;
import static com.example.tacademy.petpp.R.mipmap.btn_profile_on;
import static com.example.tacademy.petpp.R.mipmap.btn_review_off;
import static com.example.tacademy.petpp.R.mipmap.btn_review_on;

public class MyPageActivity extends BaseActivity {

    Button scheduleBtn, messageBtn, editPProfileBtn, editDProfileBtn;

    ImageButton myGalleryBtn, myProfileBtn, myReviewBtn;

    ImageButton writeBtn;
    TextView myFeedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        // 상단바
        writeBtn = (ImageButton)findViewById(R.id.writeBtn);
        myFeedText = (TextView)findViewById(R.id.myFeedText);

        // [ UI 구성 ] =======================================================

        scheduleBtn     = (Button)findViewById(R.id.scheduleBtn);
        messageBtn      = (Button)findViewById(R.id.messageBtn);
        editPProfileBtn = (Button)findViewById(R.id.editPProfileBtn);
        editDProfileBtn = (Button)findViewById(R.id.editDProfileBtn);

        myGalleryBtn    = (ImageButton)findViewById(R.id.myGalleryBtn);
        myProfileBtn    = (ImageButton)findViewById(R.id.myProfileBtn);
        myReviewBtn     = (ImageButton)findViewById(R.id.myReviewBtn);

        MyViewFragment myViewFragment       = new MyViewFragment();
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        MyReviewFragment myReviewFragment   = new MyReviewFragment();

        replaceFragment(myViewFragment);

        // ==================================================================

        Intent intent = getIntent();
        Boolean type = intent.getExtras().getBoolean("type");

        if(type == true){
            // 오른쪽 메뉴에서 마이피드페이지 넘어올 경우
            // 산책예약버튼, 메시지보내기 버튼 숨기기
            scheduleBtn.setVisibility(INVISIBLE);
            messageBtn.setVisibility(INVISIBLE);
            // 프로필 수정 버튼 보이기
            editPProfileBtn.setVisibility(VISIBLE);
            editDProfileBtn.setVisibility(VISIBLE);
            // 상단바 게시글 작성 버튼 보이기
            writeBtn.setVisibility(VISIBLE);
            // 상단바 타이틀 이름 설정
            myFeedText.setText("My Feed");
        }else{
            // 산책예약버튼, 메시지보내기 버튼 보이기
            scheduleBtn.setVisibility(VISIBLE);
            messageBtn.setVisibility(VISIBLE);
            // 프로필 수정 버튼 숨기기
            editPProfileBtn.setVisibility(INVISIBLE);
            editDProfileBtn.setVisibility(INVISIBLE);
            // 상단바 게시글 작성 버튼 숨기기
            writeBtn.setVisibility(INVISIBLE);
            // 상단바 타이틀 상대방 견주 이름으로 설정
            myFeedText.setText("다른 견주 페이지");
        }

        // [ 마이페이지 프레그먼트 이동 ] ======================================

        myGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGalleryBtn.setImageResource(btn_gallery_on);
                myProfileBtn.setImageResource(btn_profile_off);
                myReviewBtn.setImageResource(btn_review_off);
                replaceFragment(myViewFragment);
            }
        });
        myProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGalleryBtn.setImageResource(btn_gallery_off);
                myProfileBtn.setImageResource(btn_profile_on);
                myReviewBtn.setImageResource(btn_review_off);
                replaceFragment(myProfileFragment);
            }
        });
        myReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGalleryBtn.setImageResource(btn_gallery_off);
                myProfileBtn.setImageResource(btn_profile_off);
                myReviewBtn.setImageResource(btn_review_on);
                replaceFragment(myReviewFragment);
            }
        });


        // [ 버튼 이벤트 리스너 ] =============================================

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
                Intent intent = new Intent(MyPageActivity.this, ChatActivity.class);
                startActivity(intent);

            }
        });

        // 사람 프로필 수정 버튼
        editPProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사람프로필 화면으로 이동
                Intent intent = new Intent(MyPageActivity.this, PeopleProfileActivity.class);
                intent.putExtra("type", true);
                startActivity(intent);
            }
        });

        // 반려견 프로필 수정 화면으로 이동.
        editDProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 반려견프로필 화면으로 이동
                Intent intent = new Intent(MyPageActivity.this, DogProfileActivity.class);
                intent.putExtra("type", true);
                startActivity(intent);
            }
        });

        // ===================================================================

    }

    // 프레그 먼트로 이동
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    // 글쓰기 버튼 클릭시
    public void onWriteBtn(View view){
        Intent intent = new Intent(MyPageActivity.this, WriteAcitivity.class);
        startActivity(intent);
    }

}
