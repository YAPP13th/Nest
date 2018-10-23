package roommate.yapp.com.yapp13th_roommate.SignUp;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Function.RadioFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.ViewPager.PagerAdapter;
import roommate.yapp.com.yapp13th_roommate.ViewPager.RoomImageModifyPagerAdapter;
import roommate.yapp.com.yapp13th_roommate.ViewPager.RoomImagePagerAdapter;

public class SignUpFirstActivity extends AppCompatActivity {

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private final int MULTI_CROP = 1113;
    private final int ROOM_SELECT = 1114;

    private EditText etName, etOpenChat;
    private ImageView ivMyProfile, pagerIndex1, pagerIndex2, pagerIndex3;
    private RadioButton[] rbGender, rbRoom;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rg1,rg2;
    private Spinner spinner;
    private CheckBox chTerms;

    private GlobalVariable global;
    private ImageFunc imageFunc;
    private RadioFunc radioFunc;

    private TextView tvTitle;

    private ViewPager viewPager;
    private RoomImageModifyPagerAdapter roomImagePagerAdapter;

    private int multiCropIndex, multiSelectNum;
    private ArrayList<Image> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_first);

        global = (GlobalVariable)getApplicationContext();
        imageFunc = new ImageFunc(this);
        radioFunc = new RadioFunc(this);

//        global.everyInfo = new ArrayList<>();
//        global.filterInfo = new ArrayList<>();
//        global.myInfo = new UserInfo();
//        global.temp = new UserInfo();
//        global.myRoom = new Bitmap[3];
//        global.tempRoom = new Bitmap[3];
//
//        global.setExist(false);
//        global.setMyId("4");
//        global.myInfo.setId("4");

        tvTitle = (TextView)findViewById(R.id.tvNewLine);
        String str = "당신은\n어떤사람인가요?";
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ffc231")), 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitle.setText(ssb);

        etName = (EditText)findViewById(R.id.join_etname);
        etOpenChat = (EditText)findViewById(R.id.join_etchatURL);

        Resources res = getResources();
        String[] years = res.getStringArray(R.array.year);

        spinner = findViewById(R.id.join_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

        ivMyProfile = findViewById(R.id.join_image);
        ivMyProfile.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivMyProfile.setClipToOutline(true);//원형으로 자르는게 롤리팝이상버전만 가능
        }

        ivMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFunc.selectGallery();
            }
        });

        pagerIndex1 = findViewById(R.id.viewPagerIndex1);
        pagerIndex2 = findViewById(R.id.viewPagerIndex2);
        pagerIndex3 = findViewById(R.id.viewPagerIndex3);

        global.temp.setMonthly("0");
        //사용자가 시크바를 움직이지 않을 수도 있으므로 0으로 미리 초기화

        final TextView tvprog = findViewById(R.id.join_tvprog);
        seekBar=findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prog = i*10;
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
        //라디오 이벤트
        rg1 = findViewById(R.id.join_rggender);
        rg2 = findViewById(R.id.join_rgroom);

        rbGender = new RadioButton[2];
        rbRoom = new RadioButton[2];
        rbGender[0] = findViewById(R.id.join_rbf);
        rbGender[1] = findViewById(R.id.join_rbm);
        rbRoom[0] = findViewById(R.id.join_rbroomo);
        rbRoom[1] = findViewById(R.id.join_rbroomx);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.genderCheck(rbGender[0], rbGender);
                radioFunc.genderCheck(rbGender[1], rbGender);
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.roomCheck(rbRoom[0], rbRoom);
                radioFunc.roomCheck(rbRoom[1], rbRoom);
            }
        });

        //다음페이지
        TextView tvnext = findViewById(R.id.join_btnnext);
        tvnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                global.temp.setName(etName.getText().toString());
                global.temp.setYear(spinner.getSelectedItem().toString());
                global.temp.setOpenChatURL(etOpenChat.getText().toString());
                //여기에서 유효한 오픈 채팅 url 인지 확인하는 작업이 필요함

                if((global.temp.getName() == null || global.temp.getName().equals("")) || (global.temp.getGender() == null || global.temp.getGender().equals(""))
                        || (global.temp.getRoom() == null || global.temp.getRoom().equals("")) || (global.temp.getOpenChatURL() == null || global.temp.getOpenChatURL().equals(""))){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpFirstActivity.this);
                    builder.setTitle("모두 입력해 주세요");
                    builder.setMessage("모두 입력해 주세요");
                    builder.setPositiveButton("넹",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }else{
                    if(chTerms.isChecked()){
                        Intent intent = new Intent(SignUpFirstActivity.this,SignUpSecondActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpFirstActivity.this);
                        builder.setTitle("약관 동의");
                        builder.setMessage("약관에 동의해 주세요");
                        builder.setPositiveButton("넹",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                    }

                }


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

        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.myprofileedit_house_photo_icon);
        roomImagePagerAdapter = new RoomImageModifyPagerAdapter(SignUpFirstActivity.this, bitmaps);
        viewPager.setAdapter(roomImagePagerAdapter);
        //뷰 페이저 빈 화면 만들기

        chTerms = (CheckBox)findViewById(R.id.chTerms);

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
                    global.setTempProfile(imageFunc.sendPicture(imageFunc.getImageContentUri(SignUpFirstActivity.this, new File(resultUri.getPath()))));
                    ivMyProfile.setImageBitmap(global.getTempProfile());
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

                    global.tempRoom[multiCropIndex++] = imageFunc.sendPicture(imageFunc.getImageContentUri(SignUpFirstActivity.this, new File(multiResultUri.getPath())));

                    if(multiCropIndex == multiSelectNum){
                        roomImagePagerAdapter = new RoomImageModifyPagerAdapter(SignUpFirstActivity.this, global.tempRoom);
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

}