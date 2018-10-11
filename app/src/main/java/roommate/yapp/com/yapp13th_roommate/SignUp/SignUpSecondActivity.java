package roommate.yapp.com.yapp13th_roommate.SignUp;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Date;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.FirebaseFunc;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Function.RadioFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.ViewPager.ViewPagerMain;

public class SignUpSecondActivity extends AppCompatActivity {

    private GlobalVariable global;
    private FirebaseFunc firebaseFunc;
    private ImageFunc imageFunc;
    private RadioFunc radioFunc;

    private RadioButton[] rbPattern, rbDrink, rbSmoking, rbAllowFriend, rbPet;
    private RadioGroup rgPattern, rgDrink, rgSmoking, rgAllowFriend, rgPet;
    private EditText tvInstar, tvLike, tvDisLike, tvIntroduce;
    private TextView btnStart;

    private Boolean patternCheck[], drinkCheck[],smokingCheck[], friendCheck[], petCheck[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_second);

        global = (GlobalVariable)getApplicationContext();
        firebaseFunc = new FirebaseFunc(this);
        imageFunc = new ImageFunc(this);
        radioFunc = new RadioFunc(this);

        rbPattern = new RadioButton[3];
        rbDrink = new RadioButton[4];
        rbSmoking = new RadioButton[2];
        rbAllowFriend = new RadioButton[3];
        rbPet = new RadioButton[3];

        patternCheck = new Boolean[3];
        drinkCheck = new Boolean[4];
        smokingCheck = new Boolean[2];
        friendCheck = new Boolean[3];
        petCheck = new Boolean[3];

        Arrays.fill(patternCheck, false);
        Arrays.fill(drinkCheck, false);
        Arrays.fill(smokingCheck, false);
        Arrays.fill(friendCheck, false);
        Arrays.fill(petCheck, false);

        tvInstar = (EditText)findViewById(R.id.join_etinstar) ;
        tvLike = (EditText)findViewById(R.id.join_etlike);
        tvDisLike = (EditText)findViewById(R.id.join_etdislike);
        tvIntroduce = (EditText)findViewById(R.id.join_etme);

        btnStart = (TextView)findViewById(R.id.join_start);

        //라디오 이벤트
        rgPattern = findViewById(R.id.join_rglifestyle);
        rgDrink = findViewById(R.id.join_rgdrink);
        rgSmoking = findViewById(R.id.join_rgcig);
        rgAllowFriend = findViewById(R.id.join_rgfriend);
        rgPet = findViewById(R.id.join_rgpet);

        rbPattern[0] = findViewById(R.id.join_rbday);
        rbPattern[1] = findViewById(R.id.join_rbnight);
        rbPattern[2] = findViewById(R.id.join_rbnostyle);
        rbDrink[0] = findViewById(R.id.join_rbdrink1);
        rbDrink[1] = findViewById(R.id.join_rbdrink2);
        rbDrink[2] = findViewById(R.id.join_rbdrink3);
        rbDrink[3] = findViewById(R.id.join_rbdrink4);
        rbSmoking[0] = findViewById(R.id.join_rbcig1);
        rbSmoking[1] = findViewById(R.id.join_rbcig2);
        rbAllowFriend[0] = findViewById(R.id.join_rbfri1);
        rbAllowFriend[1] = findViewById(R.id.join_rbfri2);
        rbAllowFriend[2] = findViewById(R.id.join_rbfri3);
        rbPet[0] = findViewById(R.id.join_rbpet1);
        rbPet[1] = findViewById(R.id.join_rbpet2);
        rbPet[2] = findViewById(R.id.join_rbpet3);

        rgPattern.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                patternCheck = radioFunc.patternCheck(rgPattern, rbPattern[0], rbPattern, patternCheck);
                patternCheck = radioFunc.patternCheck(rgPattern, rbPattern[1], rbPattern, patternCheck);
                patternCheck = radioFunc.patternCheck(rgPattern, rbPattern[2], rbPattern, patternCheck);
            }
        });
        rgDrink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                drinkCheck = radioFunc.drinkCheck(rgDrink, rbDrink[0], rbDrink, drinkCheck);
                drinkCheck = radioFunc.drinkCheck(rgDrink, rbDrink[1], rbDrink, drinkCheck);
                drinkCheck = radioFunc.drinkCheck(rgDrink, rbDrink[2], rbDrink, drinkCheck);
                drinkCheck = radioFunc.drinkCheck(rgDrink, rbDrink[3], rbDrink, drinkCheck);
            }
        });
        rgSmoking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                smokingCheck = radioFunc.smokingCheck(rgSmoking, rbSmoking[0], rbSmoking, smokingCheck);
                smokingCheck = radioFunc.smokingCheck(rgSmoking, rbSmoking[1], rbSmoking, smokingCheck);
            }
        });
        rgAllowFriend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                friendCheck = radioFunc.allowFriendCheck(rgAllowFriend, rbAllowFriend[0], rbAllowFriend, friendCheck);
                friendCheck = radioFunc.allowFriendCheck(rgAllowFriend, rbAllowFriend[1], rbAllowFriend, friendCheck);
                friendCheck = radioFunc.allowFriendCheck(rgAllowFriend, rbAllowFriend[2], rbAllowFriend, friendCheck);
            }
        });
        rgPet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                petCheck = radioFunc.petCheck(rgPet, rbPet[0], rbPet, petCheck);
                petCheck = radioFunc.petCheck(rgPet, rbPet[1], rbPet, petCheck);
                petCheck = radioFunc.petCheck(rgPet, rbPet[2], rbPet, petCheck);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.setMyProfile(global.getTempProfile());
                global.myInfo.setProfile_image(imageFunc.saveConvertBitmap(global.getMyProfile()));

                global.myInfo.setInstarID(tvInstar.getText().toString());
                global.myInfo.setLike(tvLike.getText().toString());
                global.myInfo.setDisLike(tvDisLike.getText().toString());
                global.myInfo.setIntroduce(tvIntroduce.getText().toString());
                global.myInfo.setNow_date(new Date(System.currentTimeMillis()));

                firebaseFunc.FirebaseSignUp();

                Intent signUpDone = new Intent(SignUpSecondActivity.this, ViewPagerMain.class);
                startActivity(signUpDone);
                finish();
            }
        });

    }

}
