package roommate.yapp.com.yapp13th_roommate.SignUp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.FirebaseFunc;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Function.RadioFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class SignUpSecondActivity extends AppCompatActivity {

    private GlobalVariable global;
    private FirebaseFunc firebaseFunc;
    private ImageFunc imageFunc;
    private RadioFunc radioFunc;

    private RadioButton[] rbPattern, rbDrink, rbSmoking, rbAllowFriend, rbPet;
    private RadioGroup rgPattern, rgDrink, rgSmoking, rgAllowFriend, rgPet;
    private EditText tvInstar, tvLike, tvDisLike, tvIntroduce;
    private TextView btnStart;
    private ImageView ivBack;

    private Boolean patternCheck[], drinkCheck[],smokingCheck[], friendCheck[], petCheck[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_second);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getApplicationContext().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
//        tvLike = (EditText)findViewById(R.id.join_etlike);
//        tvLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        // TODO: 10/11/2018 잠깐 추가 할부분 지울거임
        global = (GlobalVariable)getApplicationContext();
        global.everyInfo = new ArrayList<>();
        global.filterInfo = new ArrayList<>();
        global.myInfo = new UserInfo();
        global.temp = new UserInfo();
        global.myRoom = new Bitmap[3];
        global.tempRoom = new Bitmap[3];

        global.setExist(false);
        global.setMyId("4");
        global.myInfo.setId("4");




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

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
                Map<String, String> roomsInfo = new HashMap<>();
                Bitmap[] rooms;
                rooms = global.getTempRoom();

                global.setMyProfile(global.getTempProfile());
                global.temp.setProfile_image(imageFunc.saveConvertBitmap(global.getMyProfile()));

                for(int i = 0; i < rooms.length; i++){
                    roomsInfo.put("room" + i, imageFunc.saveConvertBitmap(rooms[i]));
                }
                global.temp.setRoom_image(roomsInfo);

                global.temp.setId(global.myInfo.getId());
                global.temp.setInstarID(tvInstar.getText().toString());
                global.temp.setLike(tvLike.getText().toString());
                global.temp.setDisLike(tvDisLike.getText().toString());
                global.temp.setIntroduce(tvIntroduce.getText().toString());
                global.temp.setNow_date(new Date(System.currentTimeMillis()));

                global.myInfo = global.temp;

                firebaseFunc.FirebaseSignUp();
            }
        });

    }

}
