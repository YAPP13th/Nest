package roommate.yapp.com.yapp13th_roommate.Kakao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.FirebaseFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;
import roommate.yapp.com.yapp13th_roommate.ViewPager.ViewPagerMain;

public class KaKaoLoginActivity extends Activity {

    private SessionCallback callback; // 콜백 선언
    private static final String TAG = "KaKaoLoginActivity";

    private GlobalVariable global;
    private FirebaseFunc firebaseFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

        global = (GlobalVariable)getApplicationContext();
        firebaseFunc = new FirebaseFunc(KaKaoLoginActivity.this);

        callback = new SessionCallback();                  // 이 두개의 함수 중요함
        Session.getCurrentSession().addCallback(callback);
//        requestMe();

    }

    private void getHashKey(){
        try {                                                        // 패키지이름을 입력해줍니다.
            PackageInfo info = getPackageManager().getPackageInfo("roommate.yapp.com.yapp13th_roommate", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG,"key_hash="+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.activity_kakao_login); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }

    protected void requestMe() {
//        UserManagement.requestLogout(new LogoutResponseCallback() {
//            @Override
//            public void onCompleteLogout() {
//
//            }
//        });
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                global.everyInfo = new ArrayList<>();
                global.filterInfo = new ArrayList<>();
                global.myInfo = new UserInfo();
                global.temp = new UserInfo();
                global.myRoom = new Bitmap[3];
                global.tempRoom = new Bitmap[3];

                String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴

                global.setExist(false);
                global.setMyId(kakaoID);
                global.myInfo.setId(kakaoID);
                redirectKeywordActivity();
            }


        });
    }

    private void redirectKeywordActivity() {
        //카카오로 로그인 성공 시 파베에 내 정보가 있다면 메인으로, 없다면 회원가입 페이지로

        firebaseFunc.FirebaseLoginInit();

    }

    protected void redirectLoginActivity() {
        //로그인 오류 시 다시 로그인을 위해 로그인 페이지 다시 불러옴
        final Intent intent = new Intent(this, roommate.yapp.com.yapp13th_roommate.Kakao.KaKaoLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }



}