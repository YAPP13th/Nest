package roommate.yapp.com.yapp13th_roommate.Global;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import com.kakao.auth.KakaoSDK;

import java.util.ArrayList;
import java.util.List;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Kakao.KakaoSDKAdapter;
import roommate.yapp.com.yapp13th_roommate.LikeList.LikeAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.BottomRecyclerViewAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.TopRecyclerViewAdapter;

public class GlobalVariable extends Application {

    public static volatile GlobalVariable instance = null;
    public static volatile Activity currentActivity = null;

    public UserInfo myInfo;
    public UserInfo temp;
    public List<UserInfo> everyInfo;
    public List<UserInfo> filterInfo;

    public List<UserInfo> likeInfo;
    public List<UserInfo> randomTopUser;
    public List<UserInfo> selected;

    public List<String> delCheckList;//찜에서 삭제할 목록


    private String myId;

    public Bitmap myProfile, myRoom[], tempProfile, tempRoom[];

    private Boolean isExist;

    private int viewPagerPosition;

    public RecyclerView likeRecyclerView, topRecyclerView, bottomRecyclerView;
    public LikeAdapter likeAdapter;
    public BottomRecyclerViewAdapter bottomAdapter;
    public TopRecyclerViewAdapter topAdapter;

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

    public List<UserInfo> getEveryInfo() {
        return everyInfo;
    }

    public void setEveryInfo(List<UserInfo> everyInfo) {
        this.everyInfo = everyInfo;
    }

    public List<UserInfo> getFilterInfo() {
        return filterInfo;
    }

    public void setFilterInfo(List<UserInfo> filterInfo) {
        this.filterInfo = filterInfo;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public Bitmap getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(Bitmap myProfile) {
        this.myProfile = myProfile;
    }

    public Bitmap[] getMyRoom() {
        return myRoom;
    }

    public void setMyRoom(Bitmap[] myRoom) {
        this.myRoom = myRoom;
    }

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }

    public Bitmap getTempProfile() {
        return tempProfile;
    }

    public void setTempProfile(Bitmap tempProfile) {
        this.tempProfile = tempProfile;
    }

    public Bitmap[] getTempRoom() {
        return tempRoom;
    }

    public void setTempRoom(Bitmap[] tempRoom) {
        this.tempRoom = tempRoom;
    }

    public UserInfo getTemp() {
        return temp;
    }

    public void setTemp(UserInfo temp) {
        this.temp = temp;
    }

    public int getViewPagerPosition() {
        return viewPagerPosition;
    }

    public void setViewPagerPosition(int viewPagerPosition) {
        this.viewPagerPosition = viewPagerPosition;
    }

    public List<UserInfo> getSelected() {
        return selected;
    }

    public void setSelected(List<UserInfo> selected) {
        this.selected = selected;
    }
}
