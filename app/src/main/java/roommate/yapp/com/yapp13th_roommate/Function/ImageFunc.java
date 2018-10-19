package roommate.yapp.com.yapp13th_roommate.Function;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;

public class ImageFunc extends AppCompatActivity{
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private final int MULTI_CROP = 1113;
    private final int ROOM_SELECT = 1114;

    private Uri photoUri;
    private String currentPhotoPath;
    private String mImageCaptureName;

    private GlobalVariable global;

    private Context mContext;

    public ImageFunc(Context context){
        super();
        this.mContext = context;
        global = (GlobalVariable)mContext.getApplicationContext();
    }

    public void selectPhoto(){
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
                    ((Activity)mContext).startActivityForResult(intent, CAMERA_CODE);
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

    public void getPictureForPhoto() {
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
        global.setMyProfile(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }
    //위에는 사진 촬영 관련 함수

    //프로필 함수
    public void selectGallery(){
        try {
            //변경 할 이미지뷰
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_CODE);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                ((Activity)mContext).startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //갤러리에서 사진을 가져오는 기능
    //selectGallery를 통해 갤러리 권환 확인 후 intent로 갤러리로 이동
    //갤러리에서 이미지를 선택하면 onActivityResult에서 결과를 받음
    //갤러리 선택 후 함수단에서 onActivityResult를 두면 컨텍스트는 원래 부분으로 넘어가서 여기에서 처리가 안됨
    //그래서 원래 컨텍스트에서 onActivityResult를 처리 한 후 이미지 처리 함수를 쭉 사용

    //집 사진 선택 함수
    public void selectRoomGallery(){
        try {
            //변경 할 이미지뷰
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, ROOM_SELECT);
            } else {
                Intent intent = new Intent(mContext, AlbumSelectActivity.class);
                //set limit on number of images that can be selected, default is 10
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
                ((Activity)mContext).startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //사용자 다중 이미지 선택 후 이미지 잘라내는 작업 하는 함수
    public void multiCrop (ArrayList<Image> images, int index, Context context){
        Intent intent = CropImage.activity(getImageContentUri(context, new File(images.get(index).path)))
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(5,4)
                .getIntent(context);
        ((Activity)context).startActivityForResult(intent, MULTI_CROP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case GALLERY_CODE:
                //api 버전에 따라 권환을 따로 더 명시해서 확인해야 하는 경우가 있음
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((Activity)mContext).startActivityForResult(galleryIntent, GALLERY_CODE);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
            case Constants.REQUEST_CODE:
                //api 버전에 따라 권환을 따로 더 명시해서 확인해야 하는 경우가 있음
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(mContext, AlbumSelectActivity.class);
                    //set limit on number of images that can be selected, default is 10
                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
                    ((Activity)mContext).startActivityForResult(intent, Constants.REQUEST_CODE);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    public Bitmap sendPicture(Uri imgUri) {
        //원래 컨텍스트에서 onActivityResult를 통해 가져온 갤러리 이미지 uri를 처리하는 부분
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

        return rotate(bitmap, exifDegree);
    }

    private String getRealPathFromURI(Uri contentUri) {
        //가져온 uri를 이용하여 real path를 반환하는 함수
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    public static Uri getImageContentUri(Context context, File imageFile){
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
    //file uri 경로를 가진 이미지를 content:// 형태로 변경하는 함수

    private int exifOrientationToDegrees(int exifOrientation) {
        //이미지의 회전을 확인하고 회전된 값을 리턴
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
        //회전된 값을 이용하여 그만큼 이미지를 회전
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    public String saveConvertBitmap(Bitmap bitmap){
        //bitmap 이미지를 base64로 인코딩하는 함수
        if(bitmap != null){
//            BitmapFactory.Options options = new BitmapFactory.Options();
//
//            int inSampleSize = 1;
//            if (bitmap.getHeight() > 200 || bitmap.getWidth() > 200) {
//                //FAILED BINDER TRANSACTION 를 해결하기 위해 reSize를 위한 sampleSize 구하기
//                //안드로이드에서 intent를 통해 넘길 수 있는 크기는 40kb까지이다.
//                //큰 이미지는 필요하지 않으므로 크기를 100x100 이하로 만들어주기 위한 sampleSize 구하기
//
//                final int halfHeight = bitmap.getHeight() / 2;
//                final int halfWidth = bitmap.getWidth() / 2;
//
//                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//                // height and width larger than the requested height and width.
//                while ((halfHeight / inSampleSize) >= 100
//                        && (halfWidth / inSampleSize) >= 100) {
//                    inSampleSize *= 2;
//                }
//            }
//            options.inSampleSize = inSampleSize;
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] bitmapdata = baos.toByteArray();
//            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
//            bitmap = BitmapFactory.decodeStream(bs, null, options);
//            //비트맵을 inputStream으로 만든 뒤 옵션을 이용해서 reSize 후 bitmap 에 저장
//
//            ByteArrayOutputStream reSizeBaos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, reSizeBaos);
//            //reSize 된 bitmap 을 base64로 인코딩 후 리턴


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        }else{
            return null;
        }

    }

    public Bitmap decodebase64ToBitmap(String base64){
        //bitmap 이미지를 base64로 디코딩하는 함수
        if(!TextUtils.isEmpty(base64)){
            byte[] image = Base64.decode(base64, Base64.DEFAULT);
            Bitmap decodeByte = BitmapFactory.decodeByteArray(image, 0, image.length);

            return decodeByte;
        }else{
            return null;
        }

    }

    public Bitmap incisionToTopRecyclerImage(Bitmap bitmap){
        Bitmap result = Bitmap.createBitmap(bitmap,
                0,
                (int)bitmap.getHeight() * 1 / 10,
                bitmap.getWidth(),
                (int)bitmap.getHeight() * 9 / 10);
        //top recycler view의 이미지는 5:4
        //원본의 프로필 사진 이미지는 1:1 이므로
        //상, 하 10%씩 잘라내는 작업 -> 이미지 늘어나는게 싫어서

        if(bitmap != result){
            bitmap.recycle();
        }

        return result;
    }

}
