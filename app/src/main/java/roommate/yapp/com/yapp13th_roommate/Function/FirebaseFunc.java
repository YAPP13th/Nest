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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.Kakao.KaKaoLoginActivity;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;
import roommate.yapp.com.yapp13th_roommate.ViewPager.ViewPagerMain;

public class FirebaseFunc extends AppCompatActivity{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private GlobalVariable global;

    private Context mContext;

    public FirebaseFunc(Context context){
        super();
        this.mContext = context;
        global = (GlobalVariable)mContext.getApplicationContext();
    }

    public void FirebaseLoginInit(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_info_test");
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
                            global.myInfo = temp ;
                            temp.setKey(snapshot.getKey());
                            global.setExist(true);
                        }
                    }
                }

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserInfo temp = snapshot.getValue(UserInfo.class);
                    if(global.myInfo.getGender().equals(temp.getGender()) && !temp.getId().equals(global.getMyId())){
                        temp.setKey(snapshot.getKey());
                        global.everyInfo.add(temp);
                        global.filterInfo.add(temp);
                    }
                }

                Login(loginProgres);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private void Login(ProgressDialog loginProgres){
        global.setViewPagerPosition(0);
        if(global.getExist()){
            loginProgres.dismiss();
            Intent intent = new Intent(mContext, ViewPagerMain.class);
            mContext.startActivity(intent);
            finish();
        }else{
            loginProgres.dismiss();
            Intent intent = new Intent(mContext, SignUpFirstActivity.class);
            mContext.startActivity(intent);
            finish();
        }
    }

    public void FirebaseSignUp(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_info_test");

        databaseReference.push().setValue(global.myInfo);
    }

    public void MyInfoUpdate(){
        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put(global.myInfo.getKey(), global.myInfo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_info_test");
        databaseReference.updateChildren(taskMap);

        global.setViewPagerPosition(2);

        Intent intent = new Intent(mContext, ViewPagerMain.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        finish();
    }

}
