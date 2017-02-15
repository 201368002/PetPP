package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;

import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.model.Member;
import com.example.tacademy.petpp.ui.main.act.MainTLActivity;
import com.example.tacademy.petpp.util.Log;
import com.example.tacademy.petpp.util.StorageHelper;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class LoginActivity extends BaseActivity {

    SessionCallback callback;

    // 액티비티가 생성되면 세션에 아답터를 등록하고 체킹에 들어간다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // actList에 B를 추가해줍니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 카카오 로그인에 대한 세션 체킹을 위한 아답터 생성
        callback = new SessionCallback();
        // 세션 객체에 등록
        Session.getCurrentSession().addCallback(callback);
        // 세션 체킹!!
        Session.getCurrentSession().checkAndImplicitOpen();

    }

    // 로그인 수행 후 돌아왔을 떄 호출된다(데이터를 가지고)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.getInstance().signLog("requestCode : " + requestCode);
        Log.getInstance().signLog("resultCode : " + resultCode);

        if(data!=null)
            Log.getInstance().signLog("data : " + data.toString());

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // 액티비티가 소멸되면 세션에 등록된 아답터를 제거한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        // access token을 성공적으로 발급 받아 valid access token을 가지고 있는 상태.
        // 일반적으로 로그인 후의 다음 activity로 이동한다.
        @Override
        public void onSessionOpened() {
            Log.getInstance().signLog("onSessionOpened() call");
            redirectSignupActivity();
        }

        // memory와 cache에 session 정보가 전혀 없는 상태.
        // 일반적으로 로그인 버튼이 보이고 사용자가 클릭시 동의를 받아 access token 요청을 시도한다.
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Log.getInstance().signLog("onSessionOpenFailed() call" + exception.getMessage());
                Logger.e(exception);
            }
        }
    }

    // 간단하게 로그인 하는 액티비티로 이동
    protected void redirectSignupActivity() {
        Log.getInstance().signLog("redirectSignupActivity() call : 간단하게 로그인 하는 액티비티로 이동");
        requestAccessTokenInfo();
    }

    private void requestAccessTokenInfo() {
        AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                //redirectLoginActivity(self);
            }

            @Override
            public void onNotSignedUp() {
                // not happened
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e("failed to get access token info. msg=" + errorResult);
            }

            @Override
            public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                long userId = accessTokenInfoResponse.getUserId();
                Logger.d("this access token is for userId=" + userId);
                Member.getInstance().setKakaoId(""+userId);
                // userId와 프로필 정도를 보내주면 됨.
                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Logger.d("this access token expires after " + expiresInMilis + " milliseconds.");

                requestMe();    // 동적으로 실행
            }
        });
    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            // 로그인 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }

            // 로그인 성공
            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                Log.getInstance().signLog("UserProfile : " + userProfile);
                if(Member.getInstance().getKakaoId() != null | Member.getInstance().getKakaoId() == "")
                    Log.getInstance().signLog("카카오톡 아이디 : " + Member.getInstance().getKakaoId());

                // -> 거짓이라면 약관 화면 -> 참이라면 타임라인으로..
                if(!StorageHelper.getInstance().getBoolean(LoginActivity.this, "LOGIN")){
                    Intent intent = new Intent(LoginActivity.this, TermsActivity.class);
                    startActivity(intent);
                }else{
                    // 메인화면으로 이동
                    Intent intent = new Intent(LoginActivity.this, MainTLActivity.class);
                    startActivity(intent);
                    actFinish();
                }
            }

            @Override
            public void onNotSignedUp() {
            }
        });
    }
}
