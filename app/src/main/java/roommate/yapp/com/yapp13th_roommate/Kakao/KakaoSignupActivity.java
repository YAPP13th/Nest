package roommate.yapp.com.yapp13th_roommate.Kakao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;


/**
 * Created by kgh on 2018. 8. 17..
 */

public class KakaoSignupActivity extends Activity {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    Context mcontext;
    static String kakaoNickname;

    private UserInfo userInfo;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userInfo = new UserInfo();

        requestMe();

        mcontext = getApplicationContext();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
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
                String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴


                setKakaoNickname(kakaoNickname);
                Logger.d("UserProfile : " + userProfile);
                //redirectMainActivity(); // 로그인 성공시 MainActivity로
                //redirectFragmentMain();

                userInfo.setId(userProfile.getUUID());

                redirectKeywordActivity();
                //redirectMainActivity();
            }


        });
    }

    public static String getKakaoNickname() {
        return kakaoNickname;
    }

    public static void setKakaoNickname(String kakaoNickname) {
        KakaoSignupActivity.kakaoNickname = kakaoNickname;
    }

//    private void redirectMainActivity() {
//        startActivity(new Intent(this, CulturalEventSearch.class));
//        finish();
//    }

    private void redirectKeywordActivity() {
        startActivity(new Intent(this, SignUpFirstActivity.class));
        finish();
    }

    private void redirectFragmentMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, roommate.yapp.com.yapp13th_roommate.Kakao.KaKaoLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        Bundle bundle = new Bundle();
        bundle.putSerializable("userInfo", userInfo);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

//    protected void onClickLogout() {
//        UserManagement.requestLogout(new LogoutResponseCallback() {
//            @Override
//            public void onCompleteLogout() {
//                Log.d("onClick LogOut -> : ", "Test");
//                startActivity(new Intent(, KaKaoLoginActivity.class));
//            }
//        });
//    }

}
