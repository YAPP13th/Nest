package roommate.yapp.com.yapp13th_roommate.Global;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Kakao.KakaoSDKAdapter;

public class GlobalVariable extends Application {

    public static volatile GlobalVariable instance = null;
    public static volatile Activity currentActivity = null;

    public UserInfo myInfo;
    public UserInfo[] everyInfo;
    public UserInfo[] filterInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalVariable.currentActivity = currentActivity;
    }

    public static GlobalVariable getGlobalApplicationContext(){
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.Global.GlobalVariable");
        return instance;
    }

    public UserInfo getMyInfo() {
        return myInfo;
    }

    public void setMyInfo(UserInfo myInfo) {
        this.myInfo = myInfo;
    }

    public UserInfo[] getEveryInfo() {
        return everyInfo;
    }

    public void setEveryInfo(UserInfo[] everyInfo) {
        this.everyInfo = everyInfo;
    }

    public UserInfo[] getFilterInfo() {
        return filterInfo;
    }

    public void setFilterInfo(UserInfo[] filterInfo) {
        this.filterInfo = filterInfo;
    }
}
