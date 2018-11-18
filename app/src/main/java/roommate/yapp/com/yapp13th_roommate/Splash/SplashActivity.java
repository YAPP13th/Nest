package roommate.yapp.com.yapp13th_roommate.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import roommate.yapp.com.yapp13th_roommate.Kakao.KaKaoLoginActivity;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, KaKaoLoginActivity.class));
        finish();
    }
}
