package com.example.tacademy.petpp.ui.main.act;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tacademy.petpp.LoginActivity;
import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.model.Member;
import com.example.tacademy.petpp.ui.WriteAcitivity;
import com.example.tacademy.petpp.ui.leftmenu.CalenderListActivity;
import com.example.tacademy.petpp.ui.leftmenu.ChatListActivity;
import com.example.tacademy.petpp.ui.leftmenu.PointActivity;
import com.example.tacademy.petpp.ui.leftmenu.ScheduleListActivity;
import com.example.tacademy.petpp.ui.leftmenu.SettingActivity;
import com.example.tacademy.petpp.ui.main.frag.MainMapFragment;
import com.example.tacademy.petpp.ui.main.frag.MainTLFragment;
import com.example.tacademy.petpp.ui.mypage.act.MyPageActivity;
import com.example.tacademy.petpp.util.GpsDetecting;
import com.example.tacademy.petpp.util.Log;
import com.example.tacademy.petpp.util.U;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.os.Build.VERSION_CODES.M;

public class MainTLActivity extends BaseActivity {

    // ==
    TabLayout maintablayout;
    ViewPager mainviewpager;
    FragmentAdapter fragmentAdapter;
    // ==

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tl);

        // ==
        maintablayout = (TabLayout)findViewById(R.id.maintablayout);
        mainviewpager = (ViewPager)findViewById(R.id.mainviewpager);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mainviewpager.setAdapter(fragmentAdapter);
        maintablayout.setupWithViewPager(mainviewpager);
        // ==

        // 1. 네트워크 체크->GPS On 체크
        // 2. 6.0이냐 아니냐 -> GPS 동의 여부
        // 3. 디텍팅 -> GPS (투트렙으로 체킹) -> 지오코더(GSP<->주소) -> 2초이내
        checkGpsOn();

    }

    // == 플래그 먼트 어댑터
    class FragmentAdapter extends FragmentPagerAdapter {

        // 프레그먼트 화면 개수 및 정의
        Fragment[] frags = new Fragment[]{
                new MainTLFragment(),
                new MainMapFragment()
        };

        // 프레그먼트 화면 제목 설정
        String[] titles = new String[]{
                "타임라인", "지도"
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


    // =================================================================

    RelativeLayout leftmenu_back;
    FrameLayout leftmenu;
    boolean isLeftOn;
    Button settingBtn;

    // 상단바 메뉴
    public void MENU(View view){
        {
            leftmenu_back = (RelativeLayout)findViewById(R.id.leftmenu_back);
            leftmenu = (FrameLayout)findViewById(R.id.leftmenu);

            Animation animation;
            leftmenu.setVisibility(View.VISIBLE);
            if(isLeftOn){
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right);
                leftmenu_back.setVisibility(View.INVISIBLE);
                isLeftOn = false;
            }else{
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left);
                leftmenu_back.setVisibility(View.VISIBLE);
                isLeftOn  = true;
            }
            leftmenu.startAnimation(animation);
            animation.setFillEnabled(true);
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.getInstance().menuLog("애니메이션 시작");
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.getInstance().menuLog("애니메이션 종료");
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        {
            leftmenu_back.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    leftmenu_reset();
                }
            });
        }
    }

    // 홈 메뉴
    public void HOME(View view){
        Intent intent = new Intent(this, MainTLActivity.class);
        startActivity(intent);
        actFinish();
    }

    // 왼쪽 메뉴 초기화
    public void leftmenu_reset(){
        leftmenu.setVisibility(View.INVISIBLE);
        leftmenu_back.setVisibility(View.INVISIBLE);
        isLeftOn = false;
    }

    // 글쓰기 버튼 클릭시
    public void onWriteBtn(View view){
        Intent intent = new Intent(MainTLActivity.this, WriteAcitivity.class);
        startActivity(intent);
    }

    // ======================== 오른쪽 메뉴 ==============================

    // 마이페이지
    public void onMypageImageBtn(View view){
        leftmenu_reset();
        U.getInstance().setMyPageType(true);
        Intent intent = new Intent(this, MyPageActivity.class);
        startActivity(intent);
    }

    // 포인트 상세보기
    public void onMyPointTV(View view){
        leftmenu_reset();
        Intent intent = new Intent(MainTLActivity.this, PointActivity.class);
        startActivity(intent);
    }

    // 채팅리스트
    public void onChatBtn(View view){
        leftmenu_reset();
        Intent intent = new Intent(MainTLActivity.this, ChatListActivity.class);
        startActivity(intent);
    }

    // 예약리스트
    public void onScheBtn(View view){
        leftmenu_reset();
        Intent intent = new Intent(MainTLActivity.this, ScheduleListActivity.class);
        startActivity(intent);
    }

    // 시터 등록하기
    public void onCalBtn(View view){
        leftmenu_reset();
        Intent intent = new Intent(MainTLActivity.this, CalenderListActivity.class);
        startActivity(intent);
    }

    // 설정
    public void onSettingBtn(View view){
        leftmenu_reset();
        Intent intent = new Intent(MainTLActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    // 로그아웃
    //=======================카카오 로그아웃
    // 세션이 열렸을때만 활성화 하고 -> 마이메뉴쪽 하단에 배치
    // 세션이 닫혀있으면 -> 로그인 버튼
    public void onLogoutBtn(View view) {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.getInstance().signLog("로그아웃 완료 : onClickLogout()");
                new Member(null, null, null, null, null, null, null, false);
                Intent intent = new Intent(MainTLActivity.this, LoginActivity.class);
                startActivity(intent);
                // 이전 액티비티 모두 종료하기 위하여.
                actFinish();
            }
        });
    }

    // ======================================================
    // 네트워크 체크 checkGpsOn ==============================

    int goType = 0;
    int PERMISSION_ACCESS_FINE_LOCATION = 1;

    // 네트워크 체크
    public void checkGpsOn(){
        String gps =
                android.provider.Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        // 구형 단말만 gps만 체크
        if( !(gps.matches(".*gps*.") || gps.matches(".*network*.")) ){
            // GPS를 사용할수 없습니다. 설정 화면으로 이동하시겠습니까?
            SweetAlertDialog alertDialog = new SweetAlertDialog(this);
            alertDialog.setTitle("알림");
            alertDialog.setContentText("GPS를 사용할수 없습니다. 설정 화면으로 이동하시겠습니까?");
            alertDialog.setConfirmText("예");
            alertDialog.setCancelText("아니오");
            alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    goType = 1;
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    // 만약 구동이 않되면 해당 코드 추가
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    // gps 사용 허가 체크(6.0 이상일때)
                    checkGpsUseOn();
                }
            });
            alertDialog.show();
        }else{
            // 설정되어있다 => 다음단계 이동
            checkGpsUseOn();
        }
    }

    // gps 사용 허가 체크
    public void checkGpsUseOn() {
        if( Build.VERSION.SDK_INT >= M ){
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)){
                    // 동의 되었다
                    checkGpsDetectingOn(1);
                }else{
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_ACCESS_FINE_LOCATION);
                }
            } else {
                // 동의후 6.0이상에서는 퍼미션을 동의 했으므로 바로 실행
                checkGpsDetectingOn(2);
            }
        }else{
            // 6.0 이하 단말기는 동의가 필요 없으므로 바로 실행
            checkGpsDetectingOn(3);
        }
    }

    // gps 동의 받았으므로 시작!
    public void checkGpsDetectingOn(int type) {
        Log.getInstance().gpsLog("타입 : " + type);

        if( type == 2 || type == 4 ){   // 6.0이상에서 GPS 동의자
            // GPS 획득 요청
            startService();
        }else if( type == 3){ // 6.0이하이므로 권한동의 없이 바로 GPS 요청
            // GPS 획득 요청
            startService();
        }else if( type == 5){ // 6.0이상에서 거부
            // 보류
            Log.getInstance().gpsLog("else if( type == 5){ // 6.0이상에서 거부");
        }
    }

    // checkGpsDetectingOn -> type == 2 || type == 4 (6.0이상에서 GPS 동의자) 일경 우
    public void startService() {
        Intent intent = new Intent(this, GpsDetecting.class);
        this.startService(intent);
        Log.getInstance().gpsLog("startService 함수에 들어옴.");
    }

    boolean flag;

    // GPS 최신 갱신 내용 수신
    @Subscribe
    public void FinishLoad(Location location){
        android.util.Log.i("GPS",
                "새로운위치정보:" + location.getLatitude() + "," + location.getLongitude());
        Toast.makeText(this,
                "새로운위치정보:" + location.getLatitude() + ","
                        + location.getLongitude(), Toast.LENGTH_SHORT).show();
        // 주소 획득
        getAddress( location );

        if(!flag)
        {
            flag = true;
            Intent intent = new Intent(this, MainMapFragment.class);
            startActivity(intent);
        }
    }

    // GPS를 입력받으면 주소 획득
    public void getAddress(Location location){
        if(location != null)
            getAddress(location.getLatitude(), location.getLongitude());
    }
    public void getAddress(double lat, double lng){
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> addressList = null;
        try{
            addressList  = geocoder.getFromLocation(lat, lng, 2);
            if( addressList!=null && addressList.size()>0){
                // 주소 정보 1개  획득
                for(Address address : addressList){
                    Log.getInstance().gpsLog("Address address : addressList : "+address.toString()+address.getThoroughfare());
                }
                //새로운위치정보:37.4663881,126.9605299
                // Address[
                // addressLines=[0:"대한민국 서울특별시 관악구 낙성대동 산4-8"],
                // feature=산4-8,
                // admin=서울특별시,
                // sub-admin=null,
                // locality=관악구,
                // thoroughfare=낙성대동,
                // postalCode=null,
                // countryCode=KR,
                // countryName=대한민국,
                // hasLatitude=true,
                // latitude=37.4651426,
                // hasLongitude=true,
                // longitude=126.959815,
                // phone=null,url=null,extras=null]

                // 자취방
                // 37.4679231,126.9407442,17
            }else{
                // 결과 없음
                Log.getInstance().gpsLog("결과 없음");
            }
        }catch (Exception e){
            Log.getInstance().gpsLog("예외상황" + e.getMessage());
        }
    }
}
