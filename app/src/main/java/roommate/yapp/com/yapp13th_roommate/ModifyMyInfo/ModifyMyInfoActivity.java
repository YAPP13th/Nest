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
import java.util.Date;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;

public class ModifyMyInfoActivity extends AppCompatActivity{

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private Uri photoUri;
    private String currentPhotoPath;
    private String mImageCaptureName;

    private EditText name, openChat,instar, like, disLike, introduce;
    private ImageView imageView;
    private RadioButton rbf, rbm, rbroomo,rbroomx, rb11, rb12,rb13, rb21,rb22,rb23,rb24,rb31,rb32,rb41,rb42,rb43,rb51,rb52,rb53;;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7;
    private GradientDrawable drawable, drawable2, drawable3, drawable4;
    private Spinner spinner;

    private UserInfo userInfo;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView modify;

    private Boolean pattern1, pattern2, pattern3, drink1, drink2, drink3, drink4, smoking1, smoking2, friend1, friend2, friend3, pet1, pet2, pet3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        userInfo = new UserInfo();

        Intent intent = getIntent();

        userInfo = (UserInfo)intent.getSerializableExtra("userInfo");

        Log.i("test", userInfo.getId());
        Log.i("test", userInfo.getAllow_friend());
        Log.i("test", userInfo.getIntroduce());
        Log.i("test", userInfo.getLike());
        Log.i("test", userInfo.getName());
        Log.i("test", userInfo.getMonthly());
        Log.i("test", userInfo.getPattern());

        pattern1 = pattern2 = pattern3 = drink1 = drink2 = drink3 = drink4 = smoking1 = smoking2 = friend1 = friend2 = friend3 = pet1 = pet2 = pet3 = false;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_info_test");

        name = (EditText)findViewById(R.id.join_etname);
        openChat = (EditText)findViewById(R.id.join_etchatURL);

        Resources res = getResources();
        String[] years = res.getStringArray(R.array.year);

        spinner=findViewById(R.id.join_spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

        instar = (EditText)findViewById(R.id.join_etinstar) ;
        like = (EditText)findViewById(R.id.join_etlike);
        disLike = (EditText)findViewById(R.id.join_etdislike);
        introduce = (EditText)findViewById(R.id.join_etme);

        modify = (TextView) findViewById(R.id.join_modify);

        //라디오 이벤트
        rg1 = findViewById(R.id.join_rggender);
        rg2 = findViewById(R.id.join_rgroom);
        rg3=findViewById(R.id.join_rglifestyle);
        rg4=findViewById(R.id.join_rgdrink);
        rg5=findViewById(R.id.join_rgcig);
        rg6=findViewById(R.id.join_rgfriend);
        rg7=findViewById(R.id.join_rgpet);

        rbf = findViewById(R.id.join_rbf);
        rbm = findViewById(R.id.join_rbm);
        rbroomo = findViewById(R.id.join_rbroomo);
        rbroomx = findViewById(R.id.join_rbroomx);
        rb11=findViewById(R.id.join_rbday);
        rb12=findViewById(R.id.join_rbnight);
        rb13=findViewById(R.id.join_rbnostyle);
        rb21=findViewById(R.id.join_rbdrink1);
        rb22=findViewById(R.id.join_rbdrink2);
        rb23=findViewById(R.id.join_rbdrink3);
        rb24=findViewById(R.id.join_rbdrink4);
        rb31=findViewById(R.id.join_rbcig1);
        rb32=findViewById(R.id.join_rbcig2);
        rb41=findViewById(R.id.join_rbfri1);
        rb42=findViewById(R.id.join_rbfri2);
        rb43=findViewById(R.id.join_rbfri3);
        rb51=findViewById(R.id.join_rbpet1);
        rb52=findViewById(R.id.join_rbpet2);
        rb53=findViewById(R.id.join_rbpet3);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                //........체크안된거id 받아오는 방법이없어서 일일이,,

                checkclick(rbf);
                checkclick(rbm);
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rbroomo);
                checkclick(rbroomx);
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                //........체크안된거id 받아오는 방법이없어서 일일이,,
                checkclick(rb11);
                checkclick(rb12);
                checkclick(rb13);
            }
        });

        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rb21);
                checkclick(rb22);
                checkclick(rb23);
                checkclick(rb24);
            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rb31);
                checkclick(rb32);
            }
        });
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rb41);
                checkclick(rb42);
                checkclick(rb43);
            }
        });
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rb51);
                checkclick(rb52);
                checkclick(rb53);
            }
        });

        drawable=(GradientDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounding);
        drawable2=(GradientDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounding2);
        //라디오 자를 드로어블

        imageView=findViewById(R.id.join_image);
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);//원형으로 자르는게 롤리팝이상버전만 가능
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGallery();
            }
        });

        //싴바 테스트

        final TextView tvprog = findViewById(R.id.join_tvprog);
        seekBar=findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prog=i*10;
                if(prog==0)
                    tvprog.setText(prog+"만원");
                else
                    tvprog.setText("0~"+prog+"만원");

                userInfo.setMonthly(Integer.toString(prog));
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
                userInfo.setName(name.getText().toString());
                userInfo.setYear(spinner.getSelectedItem().toString());
                userInfo.setOpenChatURL(openChat.getText().toString());
                userInfo.setInstarID(instar.getText().toString());
                userInfo.setLike(like.getText().toString());
                userInfo.setDisLike(disLike.getText().toString());
                userInfo.setIntroduce(introduce.getText().toString());
                userInfo.setNow_date(new Date(System.currentTimeMillis()));

                databaseReference.push().setValue(userInfo);
            }
        });

    }

    public void checkclick(RadioButton rb){
        radioSelect(rb);
        if(rb.isChecked()){
            if(rb == rbf){
                userInfo.setGender("F");
            }else if(rb == rbm){
                userInfo.setGender("M");
            }else if(rb == rbroomo){
                userInfo.setRoom(true);
            }else if(rb == rbroomx){
                userInfo.setRoom(false);
            }else if(rb == rb11){
                if(pattern1){
                    userInfo.setPattern("");
                    pattern1 = false;

                    rg1.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setPattern("아침형");
                    pattern1 = true;
                    pattern2 = false;
                    pattern3 = false;

                    rg1.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb12){
                if(pattern2){
                    userInfo.setPattern("");
                    pattern2 = false;

                    rg1.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setPattern("저녁형");
                    pattern1 = false;
                    pattern2 = true;
                    pattern3 = false;

                    rg1.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb13){
                if(pattern3){
                    userInfo.setPattern("");
                    pattern3 = false;

                    rg1.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setPattern("불규칙");
                    pattern1 = false;
                    pattern2 = false;
                    pattern3 = true;

                    rg1.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb21){
                if(drink1){
                    userInfo.setDrink("");
                    drink1 = false;

                    rg2.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setDrink("금주");
                    drink1 = true;
                    drink2 = false;
                    drink3 = false;
                    drink4 = false;

                    rg2.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb22){
                if(drink2){
                    userInfo.setDrink("");
                    drink2 = false;

                    rg2.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setDrink("매일");
                    drink1 = false;
                    drink2 = true;
                    drink3 = false;
                    drink4 = false;

                    rg2.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb23){
                if(drink3){
                    userInfo.setDrink("0");
                    drink3 = false;

                    rg2.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setDrink("주 1~2회");
                    drink1 = false;
                    drink2 = false;
                    drink3 = true;
                    drink4 = false;

                    rg2.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb24){
                if(drink4){
                    userInfo.setDrink("");
                    drink4 = false;

                    rg2.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setDrink("월 1~2회");
                    drink1 = false;
                    drink2 = false;
                    drink3 = false;
                    drink4 = true;

                    rg2.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb31){
                if(smoking1){
                    userInfo.setSmoking("");
                    smoking1 = false;

                    rg3.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setSmoking("흡연");
                    smoking1 = true;
                    smoking2 = false;

                    rg3.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb32){
                if(smoking2){
                    userInfo.setSmoking("");
                    smoking2 = false;

                    rg3.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setSmoking("비흡연");
                    smoking1 = false;
                    smoking2 = true;

                    rg3.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb41){
                if(friend1){
                    userInfo.setAllow_friend("");
                    friend1 = false;

                    rg4.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setAllow_friend("허용");
                    friend1 = true;
                    friend2 = false;
                    friend3 = false;

                    rg4.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb42){
                if(friend2){
                    userInfo.setAllow_friend("");
                    friend2 = false;

                    rg4.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setAllow_friend("금지");
                    friend1 = false;
                    friend2 = true;
                    friend3 = false;

                    rg4.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb43){
                if(friend3){
                    userInfo.setAllow_friend("");
                    friend3 = false;

                    rg4.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setAllow_friend("합의하 허용");
                    friend1 = false;
                    friend2 = false;
                    friend3 = true;

                    rg4.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb51){
                if(pet1){
                    userInfo.setPet("");
                    pet1 = false;

                    rg5.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setPet("허용");
                    pet1 = true;
                    pet2 = false;
                    pet3 = false;

                    rg5.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb52){
                if(pet2){
                    userInfo.setPet("");
                    pet2 = false;

                    rg5.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setPet("금지");
                    pet1 = false;
                    pet2 = true;
                    pet3 = false;

                    rg5.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb53){
                if(pet3){
                    userInfo.setPet("");
                    pet3 = false;

                    rg5.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setPet("합의하 허용");
                    pet1 = false;
                    pet2 = false;
                    pet3 = true;

                    rg5.clearCheck();
                    radioSelect(rb);
                }
            }

        }else{
            radioClear(rb);
        }
    }

    public void radioSelect(RadioButton rb){
        rb.setBackground(drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rb.setClipToOutline(true);
        }
    }

    public void radioClear(RadioButton rb){
        rb.setBackground(drawable2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rb.setClipToOutline(true);
        }
    }

    private void selectPhoto(){
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException ex){

                }
                if(photoFile != null){
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, CAMERA_CODE);
                }
            }
        }

    }

    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/path/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/path/"

                + mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;

    }

    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        imageView.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    private void selectGallery(){
        try {
            if (ActivityCompat.checkSelfPermission(ModifyMyInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ModifyMyInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_CODE);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    sendPicture(data.getData());
                    break;
                case CAMERA_CODE:
                    getPictureForPhoto();
                    break;

                default:
                    break;
            }

        }
    }

    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환

        bitmap = bitmap.getWidth() > bitmap.getHeight()
                ? Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0, bitmap.getHeight(), bitmap.getHeight())
                : Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2, bitmap.getWidth(), bitmap.getWidth());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        userInfo.setProfile_image(Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP));

        imageView.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기

    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

}
