package roommate.yapp.com.yapp13th_roommate.ModifyMyInfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.FirebaseFunc;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Function.RadioFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;

public class ModifyMyInfoActivity extends AppCompatActivity{

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;

    private GlobalVariable global;
    private FirebaseFunc firebaseFunc;
    private ImageFunc imageFunc;
    private RadioFunc radioFunc;

    private EditText etName, etOpenChat, etInstar, etLike, etDisLike, etIntroduce;
    private ImageView ivJoin;
    private RadioButton[] rbGender, rbRoom, rbPattern, rbDrink, rbSmoking, rbAllowFriend, rbPet;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rgGender, rgRoom, rgPattern, rgDrink, rgSmoking, rgAllowFriend, rgPet;
    private Spinner spinner;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView modify;

    private Boolean[] patternCheck, drinkCheck, smokingCheck, friendCheck, petCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        global = (GlobalVariable)getApplicationContext();
        firebaseFunc = new FirebaseFunc(this);
        imageFunc = new ImageFunc(this);
        radioFunc = new RadioFunc(this);

        rbGender = new RadioButton[2];
        rbRoom = new RadioButton[2];
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

        global.temp = new UserInfo();

        Resources res = getResources();
        String[] years = res.getStringArray(R.array.year);

        spinner=findViewById(R.id.join_spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        
        etName = (EditText)findViewById(R.id.join_etname);
        etOpenChat = (EditText)findViewById(R.id.join_etchatURL);
        etInstar = (EditText)findViewById(R.id.join_etinstar) ;
        etLike = (EditText)findViewById(R.id.join_etlike);
        etDisLike = (EditText)findViewById(R.id.join_etdislike);
        etIntroduce = (EditText)findViewById(R.id.join_etme);

        modify = (TextView) findViewById(R.id.join_modify);

        //라디오 이벤트
        rgGender = findViewById(R.id.join_rggender);
        rgRoom = findViewById(R.id.join_rgroom);
        rgPattern = findViewById(R.id.join_rglifestyle);
        rgDrink = findViewById(R.id.join_rgdrink);
        rgSmoking = findViewById(R.id.join_rgcig);
        rgAllowFriend = findViewById(R.id.join_rgfriend);
        rgPet = findViewById(R.id.join_rgpet);

        rbGender[0] = findViewById(R.id.join_rbf);
        rbGender[1] = findViewById(R.id.join_rbm);
        rbRoom[0] = findViewById(R.id.join_rbroomo);
        rbRoom[1] = findViewById(R.id.join_rbroomx);
        rbPattern[0] = findViewById(R.id.join_rbday);
        rbPattern[1] = findViewById(R.id.join_rbnight);
        rbPattern[2] = findViewById(R.id.join_rbnostyle);
        rbDrink[0] = findViewById(R.id.join_rbdrink1);
        rbDrink[1] = findViewById(R.id.join_rbdrink2);
        rbDrink[2] = findViewById(R.id.join_rbdrink3);
        rbDrink[3] = findViewById(R.id.join_rbdrink4);
        rbSmoking[0] = findViewById(R.id.join_rbcig1);
        rbSmoking[1] = findViewById(R.id.join_rbcig2);
        rbAllowFriend[0] =findViewById(R.id.join_rbfri1);
        rbAllowFriend[1] = findViewById(R.id.join_rbfri2);
        rbAllowFriend[2] =findViewById(R.id.join_rbfri3);
        rbPet[0] = findViewById(R.id.join_rbpet1);
        rbPet[1] = findViewById(R.id.join_rbpet2);
        rbPet[2] = findViewById(R.id.join_rbpet3);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.genderCheck(rbGender[0], rbGender);
                radioFunc.genderCheck(rbGender[1], rbGender);
            }
        });

        rgRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.roomCheck(rbRoom[0], rbRoom);
                radioFunc.roomCheck(rbRoom[1], rbRoom);
            }
        });

        rgPattern.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.patternCheck(rgPattern, rbPattern[0], rbPattern, patternCheck);
                radioFunc.patternCheck(rgPattern, rbPattern[1], rbPattern, patternCheck);
                radioFunc.patternCheck(rgPattern, rbPattern[2], rbPattern, patternCheck);
            }
        });

        rgDrink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.drinkCheck(rgDrink, rbDrink[0], rbDrink, drinkCheck);
                radioFunc.drinkCheck(rgDrink, rbDrink[1], rbDrink, drinkCheck);
                radioFunc.drinkCheck(rgDrink, rbDrink[2], rbDrink, drinkCheck);
                radioFunc.drinkCheck(rgDrink, rbDrink[3], rbDrink, drinkCheck);
            }
        });
        rgSmoking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.smokingCheck(rgSmoking, rbSmoking[0], rbSmoking, smokingCheck);
                radioFunc.smokingCheck(rgSmoking, rbSmoking[1], rbSmoking, smokingCheck);
            }
        });
        rgAllowFriend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.allowFriendCheck(rgAllowFriend, rbAllowFriend[0], rbAllowFriend, friendCheck);
                radioFunc.allowFriendCheck(rgAllowFriend, rbAllowFriend[1], rbAllowFriend, friendCheck);
                radioFunc.allowFriendCheck(rgAllowFriend, rbAllowFriend[2], rbAllowFriend, friendCheck);
            }
        });
        rgPet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.petCheck(rgPet, rbPet[0], rbPet, petCheck);
                radioFunc.petCheck(rgPet, rbPet[1], rbPet, petCheck);
                radioFunc.petCheck(rgPet, rbPet[2], rbPet, petCheck);
            }
        });

        ivJoin=findViewById(R.id.join_image);
        ivJoin.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivJoin.setClipToOutline(true);//원형으로 자르는게 롤리팝이상버전만 가능
        }

        ivJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFunc.selectGallery(ivJoin);
            }
        });

        //싴바 테스트

        final TextView tvprog = findViewById(R.id.join_tvprog);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prog = i * 10;
                if(prog == 0)
                    tvprog.setText(prog + "만원");
                else
                    tvprog.setText("0~" + prog + "만원");

                global.temp.setMonthly(Integer.toString(prog));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.temp.setName(etName.getText().toString());
                global.temp.setYear(spinner.getSelectedItem().toString());
                global.temp.setOpenChatURL(etOpenChat.getText().toString());
                global.temp.setInstarID(etInstar.getText().toString());
                global.temp.setLike(etLike.getText().toString());
                global.temp.setDisLike(etDisLike.getText().toString());
                global.temp.setIntroduce(etIntroduce.getText().toString());
                global.temp.setNow_date(new Date(System.currentTimeMillis()));

                global.setMyProfile(global.getTempProfile());
//                global.myInfo = global.temp;
//                databaseReference.push().setValue(userInfo);
            }
        });

        dataInit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //갤러리 선택 or 카메라로 촬영 선택에 따른 onActivityResult
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    imageFunc.sendPicture(data.getData());
                    break;
                case CAMERA_CODE:
                    imageFunc.getPictureForPhoto();
                    break;

                default:
                    break;
            }

        }
    }

    private void dataInit() {
        ivJoin.setImageBitmap(imageFunc.decodebase64ToBitmap(global.myInfo.getProfile_image()));

        etName.setText(global.myInfo.getName());
        etOpenChat.setText(global.myInfo.getOpenChatURL());
        etInstar.setText(global.myInfo.getInstarID());
        etLike.setText(global.myInfo.getLike());
        etDisLike.setText(global.myInfo.getDisLike());
        etIntroduce.setText(global.myInfo.getIntroduce());
//        tvLocation.setText(global.myInfo.getLocation());
//        tvMonthly.setText(global.myInfo.getMonthly() + " 만원");
//        tvPattern.setText(global.myInfo.getPattern());
//        tvDrink.setText(global.myInfo.getDrink());
//        tvSmoking.setText(global.myInfo.getSmoking());
//        tvAllowFriend.setText(global.myInfo.getAllow_friend());
//        tvPet.setText(global.myInfo.getPet());
        //라디오버튼이랑 seekbar 처리를 해야됨
    }

}
