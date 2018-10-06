package roommate.yapp.com.yapp13th_roommate.SignUp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.R;

public class SignUpFirstActivity extends AppCompatActivity {

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private Uri photoUri;
    private String currentPhotoPath;
    private String mImageCaptureName;

    private EditText name, openChat;
    private ImageView imageView;
    private RadioButton rbf, rbm, rbroomo,rbroomx;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rg1,rg2;
    private GradientDrawable drawable,drawable2;
    private Spinner spinner;

    private UserInfo userInfo;
    //유저 정보 DTO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_first);

        userInfo = new UserInfo();

        name = (EditText)findViewById(R.id.join_etname);
        openChat = (EditText)findViewById(R.id.join_etchatURL);

        Resources res = getResources();
        String[] years = res.getStringArray(R.array.year);

        spinner=findViewById(R.id.join_spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

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
        //라디오 이벤트
        rg1 = findViewById(R.id.join_rggender);
        rg2 = findViewById(R.id.join_rgroom);
        rbf = findViewById(R.id.join_rbf);
        rbm = findViewById(R.id.join_rbm);
        rbroomo = findViewById(R.id.join_rbroomo);
        rbroomx = findViewById(R.id.join_rbroomx);

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

        //다음페이지
        TextView tvnext = findViewById(R.id.join_btnnext);
        tvnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userInfo.setName(name.getText().toString());
                userInfo.setYear(spinner.getSelectedItem().toString());
                userInfo.setOpenChatURL(openChat.getText().toString());

                if((userInfo.getName() == null || userInfo.getName().equals("")) || (userInfo.getGender() == null || userInfo.getGender().equals(""))
                        || (userInfo.getRoom() == null || userInfo.getRoom().equals("")) || (userInfo.getOpenChatURL() == null || userInfo.getOpenChatURL().equals(""))){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpFirstActivity.this);
                    builder.setTitle("모두 입력해 주세요");
                    builder.setMessage("모두 입력해 주세요");
                    builder.setPositiveButton("넹",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }else{
                    Intent intent = new Intent(getApplicationContext(),SignUpSecondActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userInfo", userInfo);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }


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

        bitmap = bitmap.getWidth() > bitmap.getHeight()
                ? Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0, bitmap.getHeight(), bitmap.getHeight())
                : Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2, bitmap.getWidth(), bitmap.getWidth());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        userInfo.setProfile_image(Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP));

        imageView.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    private void selectGallery(){
        try {
            if (ActivityCompat.checkSelfPermission(SignUpFirstActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SignUpFirstActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_CODE);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case GALLERY_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY_CODE);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
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
