package roommate.yapp.com.yapp13th_roommate.Function;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.Kakao.KaKaoLoginActivity;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;
import roommate.yapp.com.yapp13th_roommate.ViewPager.ViewPagerMain;

public class FirebaseFunc extends AppCompatActivity{
    private GlobalVariable global;
    private Context mContext;

    public FirebaseFunc(Context context){
        super();
        this.mContext = context;
        global = (GlobalVariable)mContext.getApplicationContext();
    }

    public void FirebaseLoginInit(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("user_info");
        //firebase 및 firebase 테이블 연결

        final ProgressDialog loginProgres = ProgressDialog.show(mContext, "Wait please", "로그인중");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(dataSnapshot.getChildrenCount() == 0){
                        loginProgres.dismiss();
                        Intent intent = new Intent(mContext, SignUpFirstActivity.class);
                        mContext.startActivity(intent);
                        finish();
                    }else{
                        UserInfo temp = snapshot.getValue(UserInfo.class);
                        if(temp.getId().equals(global.getMyId())){
                            temp.setNow_date(new Date(System.currentTimeMillis()));
                            global.myInfo = temp;
                            global.setExist(true);

                            Map<String, Object> taskMap = new HashMap<String, Object>();
                            taskMap.put("now_date", new Date(System.currentTimeMillis()));
                            //기존 데이터에 키 값을 추가하기 위한 해쉬맵 생성

                            databaseReference.child(global.myInfo.getId()).updateChildren(taskMap);
                            break;
                        }
                    }
                }

                global.setViewPagerPosition(0);
                if(global.getExist()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        UserInfo temp = snapshot.getValue(UserInfo.class);
                        if(global.myInfo.getGender().equals(temp.getGender()) && !temp.getId().equals(global.getMyId())){
                            global.everyInfo.add(temp);
                        }
                    }

                    Collections.sort(global.everyInfo, new Comparator<UserInfo>() {
                        @Override
                        public int compare(UserInfo userInfo, UserInfo t1) {
                            return userInfo.getNow_date().compareTo(t1.getNow_date());
                        }
                    });
                    Collections.reverse(global.everyInfo);
                    global.filterInfo = global.everyInfo;

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference likeDatabaseReference = firebaseDatabase.getReference("like");
                    likeDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                                if(global.myInfo.getId().equals(snapshot.getKey())){
                                    for(int i = 0; i < global.everyInfo.size(); i++){

                                        for(DataSnapshot likeSnapshot : snapshot.getChildren()){
                                            if(global.everyInfo.get(i).getId().equals(likeSnapshot.getKey())){
                                                global.likeInfo.add(global.everyInfo.get(i));
                                            }
                                        }
                                    }
                                }
                            }

                            loginProgres.dismiss();
                            Intent intent = new Intent(mContext, ViewPagerMain.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });

                }else{
                    loginProgres.dismiss();
                    Intent intent = new Intent(mContext, SignUpFirstActivity.class);
                    mContext.startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    public void FirebaseSignUp(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user_info");

        databaseReference.child(global.myInfo.getId()).setValue(global.myInfo);

        final ProgressDialog loginProgres = ProgressDialog.show(mContext, "Wait please", "로그인중");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                global.setViewPagerPosition(0);

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserInfo temp = snapshot.getValue(UserInfo.class);
                    if(global.myInfo.getGender().equals(temp.getGender()) && !temp.getId().equals(global.getMyId())){
                        global.everyInfo.add(temp);
                    }
                }

                Collections.sort(global.everyInfo, new Comparator<UserInfo>() {
                    @Override
                    public int compare(UserInfo userInfo, UserInfo t1) {
                        return userInfo.getNow_date().compareTo(t1.getNow_date());
                    }
                });
                Collections.reverse(global.everyInfo);
                global.filterInfo = global.everyInfo;

                loginProgres.dismiss();
                Intent intent = new Intent(mContext, ViewPagerMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    public void MyInfoUpdate(){
        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put(global.myInfo.getId(), global.myInfo);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user_info");
        databaseReference.updateChildren(taskMap);

        global.setViewPagerPosition(2);
        Intent intent = new Intent(mContext, ViewPagerMain.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);

        mContext.startActivity(intent);
        finish();
    }

}