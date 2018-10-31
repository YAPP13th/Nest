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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;
import roommate.yapp.com.yapp13th_roommate.ViewPager.RoomImageModifyPagerAdapter;
import roommate.yapp.com.yapp13th_roommate.ViewPager.RoomImagePagerAdapter;

public class ModifyMyInfoActivity extends AppCompatActivity{

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private final int MULTI_CROP = 1113;
    private final int ROOM_SELECT = 1114;

    private GlobalVariable global;
    private FirebaseFunc firebaseFunc;
    private ImageFunc imageFunc;
    private RadioFunc radioFunc;

    private EditText etName, etOpenChat, etInstar, etLike, etDisLike, etIntroduce;
    private ImageView ivJoin, pagerIndex1, pagerIndex2, pagerIndex3;
    private RadioButton[] rbGender, rbRoom, rbPattern, rbDrink, rbSmoking, rbAllowFriend, rbPet;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rgGender, rgRoom, rgPattern, rgDrink, rgSmoking, rgAllowFriend, rgPet;
    private Spinner spinner;

    private TextView modify;

    private Boolean[] patternCheck, drinkCheck, smokingCheck, friendCheck, petCheck;

    private ViewPager viewPager;
    private RoomImageModifyPagerAdapter roomImagePagerAdapter;

    private ArrayList<Image> images;
    private int multiCropIndex, multiSelectNum;

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
        global.temp.setId(global.myInfo.getId());
        global.temp.setKey(global.myInfo.getKey());
        global.temp.setProfile_image(global.myInfo.getProfile_image());
        global.setTempProfile(imageFunc.decodebase64ToBitmap(global.temp.getProfile_image()));

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
                imageFunc.selectGallery();
            }
        });

        pagerIndex1 = (ImageView)findViewById(R.id.viewPagerIndex1);
        pagerIndex2 = (ImageView)findViewById(R.id.viewPagerIndex2);
        pagerIndex3 = (ImageView)findViewById(R.id.viewPagerIndex3);

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
                Map<String, String> roomsInfo = new HashMap<>();
                Bitmap[] rooms;
                rooms = global.getTempRoom();

                global.setMyProfile(global.getTempProfile());
                global.temp.setProfile_image(imageFunc.saveConvertBitmap(global.getMyProfile()));

                for(int i = 0; i < rooms.length; i++){
                    roomsInfo.put("room" + i, imageFunc.saveConvertBitmap(rooms[i]));
                }
                global.temp.setRoom_image(roomsInfo);

                global.temp.setName(etName.getText().toString());
                global.temp.setYear(spinner.getSelectedItem().toString());
                global.temp.setOpenChatURL(etOpenChat.getText().toString());
                global.temp.setInstarID(etInstar.getText().toString());
                global.temp.setLike(etLike.getText().toString());
                global.temp.setDisLike(etDisLike.getText().toString());
                global.temp.setIntroduce(etIntroduce.getText().toString());
                global.temp.setNow_date(new Date(System.currentTimeMillis()));

                global.myInfo = global.temp;

                firebaseFunc.MyInfoUpdate();
            }
        });

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewTreeObserver vto = viewPager.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                viewPager.getLayoutParams().height = (int)(viewPager.getWidth() * 0.8);
                viewPager.requestLayout();
            }
        });
        //match_parent를 onCreate에서 이용하면 아직 뷰가 그려지기 전이라서 0으로 호출이 된다
        //뷰가 그려진 이후를 지켜보기 위해 트리옵저버를 이용하여 viewPager를 확인 및 그려지면 가로 : 세로 = 5 : 4 비율을 만들기 위해
        //레이아웃을 다시 그려준다

        dataInit();
        //내 정보를 불러와 레이아웃 세팅 함수

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //갤러리 선택 or 카메라로 촬영 선택에 따른 onActivityResult
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .start(this);
                    //갤러리를 열고 거기서 받아온 이미지 uri를 crop 하기 위한 액티비티로 이동
                    break;

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    //file:~~~ 식의 경로로 리턴이 됨
                    //imageFunc.getImageContentUri 이건 file 형태의 url 이미지를 content://~~ 형태의 이미지로 변경하는 함수
                    global.setTempProfile(imageFunc.sendPicture(imageFunc.getImageContentUri(ModifyMyInfoActivity.this, new File(resultUri.getPath()))));
                    ivJoin.setImageBitmap(global.getTempProfile());
                    break;

                case Constants.REQUEST_CODE:
                    //이미지 다중 선택 처리
                    //The array list has the image paths of the selected images
                    images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                    global.tempRoom = new Bitmap[images.size()];

                    multiSelectNum = images.size();
                    multiCropIndex = 0;

                    imageFunc.multiCrop(images, multiCropIndex, this);
                    //선택한 이미지 수를 받아와서 그 수만큼 페이저 뷰에 이미지 세팅하기 위한 함수
                    break;

                case MULTI_CROP:
                    //이미지 잘라내는 것을 처리 한 후 사용자가 선택한 이미지의 수 모두 처리 할 경우 페이저 뷰를 생성
                    CropImage.ActivityResult multiResult = CropImage.getActivityResult(data);
                    Uri multiResultUri = multiResult.getUri();

                    global.tempRoom[multiCropIndex++] = imageFunc.sendPicture(imageFunc.getImageContentUri(ModifyMyInfoActivity.this, new File(multiResultUri.getPath())));

                    if(multiCropIndex == multiSelectNum){
                        roomImagePagerAdapter = new RoomImageModifyPagerAdapter(ModifyMyInfoActivity.this, global.tempRoom);
                        viewPager.setAdapter(roomImagePagerAdapter);

                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                //페이저 뷰에서 지금 선택 된 인덱스를 표시해주는것 처리
                                if(multiSelectNum == 3){
                                    if(position == 0){
                                        pagerIndex1.setImageResource(R.drawable.oval_copy);
                                        pagerIndex2.setImageResource(R.drawable.oval);
                                        pagerIndex3.setImageResource(R.drawable.oval);
                                        //●○○ 형태의 이미지
                                    }else if(position == 1){
                                        pagerIndex1.setImageResource(R.drawable.oval);
                                        pagerIndex2.setImageResource(R.drawable.oval_copy);
                                        pagerIndex3.setImageResource(R.drawable.oval);
                                        //○●○ 형태의 이미지
                                    }else if(position == 2){
                                        pagerIndex1.setImageResource(R.drawable.oval);
                                        pagerIndex2.setImageResource(R.drawable.oval);
                                        pagerIndex3.setImageResource(R.drawable.oval_copy);
                                        //○○● 형태의 이미지
                                    }
                                }else if(multiSelectNum == 2){
                                    if(position == 0){
                                        pagerIndex1.setImageResource(R.color.transparent);
                                        pagerIndex2.setImageResource(R.drawable.oval_copy);
                                        pagerIndex3.setImageResource(R.drawable.oval);
                                        //●○ 형태의 이미지
                                    }else if(position == 1){
                                        pagerIndex1.setImageResource(R.color.transparent);
                                        pagerIndex2.setImageResource(R.drawable.oval);
                                        pagerIndex3.setImageResource(R.drawable.oval_copy);
                                        //○● 형태의 이미지
                                    }
                                }
                            }

                            @Override
                            public void onPageSelected(int position) {

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });

                        global.myRoom = new Bitmap[global.tempRoom.length];
                        global.setMyRoom(global.tempRoom);
                        //뷰 페이저가 생성되면, 사용자가 선택 및 잘라내는 작업 완료된 비트맵 저장
                    }else{
                        imageFunc.multiCrop(images, multiCropIndex, this);
                        //모든 이미지가 처리가 안 됬을 경우 다음 이미지 처리
                    }
                    break;

                case CAMERA_CODE:
                    imageFunc.getPictureForPhoto();
                    //갤러리 선택 말고 촬영 모드
                    //추가 안할듯? 갤러리도 겁나 복잡햇음
                    break;
                default:
                    break;
            }

        }
    }

    private void dataInit() {
        ivJoin.setImageBitmap(imageFunc.decodebase64ToBitmap(global.myInfo.getProfile_image()));
        //base64로 인코딩 된 내 프로필을 가져와서 bitmap으로 디코딩 후 이미지뷰에 셋팅

        etName.setText(global.myInfo.getName());
        etOpenChat.setText(global.myInfo.getOpenChatURL());
        etInstar.setText(global.myInfo.getInstarID());
        etLike.setText(global.myInfo.getLike());
        etDisLike.setText(global.myInfo.getDisLike());
        etIntroduce.setText(global.myInfo.getIntroduce());
        //사용자가 입력하는 부분 초기화

        radioFunc.modifyGenderInit(rbGender);
        radioFunc.modifyRoomInit(rbRoom);
        radioFunc.modifyPatternInit(rbPattern);
        radioFunc.modifyDrinkInit(rbDrink);
        radioFunc.modifySmokingInit(rbSmoking);
        radioFunc.modifyAllowFriendInit(rbAllowFriend);
        radioFunc.modifyPetInit(rbPet);
        //라디오 버튼 초기화

        spinner.setSelection(2014 - Integer.parseInt(global.myInfo.getYear()));

        TextView tvprog = findViewById(R.id.join_tvprog);
        seekBar.setProgress(Integer.parseInt(global.myInfo.getMonthly()) / 10);

        if(Integer.parseInt(global.myInfo.getMonthly()) == 0)
            tvprog.setText(prog + "만원");
        else
            tvprog.setText("0~" + prog + "만원");
        //시크바 초기화

        if(global.myInfo.getRoom_image() == null || global.myInfo.getRoom_image().isEmpty()){
            int bitmapLength = 1;
            Bitmap[] bitmaps = new Bitmap[bitmapLength];
            bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.myprofileedit_house_photo_icon);
            //추후에 빈 화면일 때 이미지생기면 여기에 처리
            makeRoom(bitmapLength, bitmaps);
        }else{
            int bitmapLength = global.myInfo.getRoom_image().size();
            Bitmap[] bitmaps = new Bitmap[bitmapLength];

            for(int i = 0; i < bitmaps.length; i++){
                bitmaps[i] = imageFunc.decodebase64ToBitmap(global.myInfo.getRoom_image().get("room" + i));
            }
            //뷰 페이저에 들어갈 방 사진 bitmap 만들기
            makeRoom(bitmapLength, bitmaps);
        }

    }

    public void makeRoom(int length, Bitmap[] bitmaps){
        final int bitmapLength = length;
        roomImagePagerAdapter = new RoomImageModifyPagerAdapter(ModifyMyInfoActivity.this, bitmaps);
        viewPager.setAdapter(roomImagePagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //페이저 뷰에서 지금 선택 된 인덱스를 표시해주는것 처리
                if(bitmapLength == 3){
                    if(position == 0){
                        pagerIndex1.setImageResource(R.drawable.oval_copy);
                        pagerIndex2.setImageResource(R.drawable.oval);
                        pagerIndex3.setImageResource(R.drawable.oval);
                        //●○○ 형태의 이미지
                    }else if(position == 1){
                        pagerIndex1.setImageResource(R.drawable.oval);
                        pagerIndex2.setImageResource(R.drawable.oval_copy);
                        pagerIndex3.setImageResource(R.drawable.oval);
                        //○●○ 형태의 이미지
                    }else if(position == 2){
                        pagerIndex1.setImageResource(R.drawable.oval);
                        pagerIndex2.setImageResource(R.drawable.oval);
                        pagerIndex3.setImageResource(R.drawable.oval_copy);
                        //○○● 형태의 이미지
                    }
                }else if(bitmapLength == 2){
                    if(position == 0){
                        pagerIndex1.setImageResource(R.color.transparent);
                        pagerIndex2.setImageResource(R.drawable.oval_copy);
                        pagerIndex3.setImageResource(R.drawable.oval);
                        //●○ 형태의 이미지
                    }else if(position == 1){
                        pagerIndex1.setImageResource(R.color.transparent);
                        pagerIndex2.setImageResource(R.drawable.oval);
                        pagerIndex3.setImageResource(R.drawable.oval_copy);
                        //○● 형태의 이미지
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
