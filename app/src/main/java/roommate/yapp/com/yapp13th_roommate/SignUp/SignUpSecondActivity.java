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

import java.util.Date;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.ViewPager.ViewPagerMain;

public class SignUpSecondActivity extends AppCompatActivity {

    private UserInfo userInfo;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private GradientDrawable drawable,drawable2;
    private RadioButton rb11, rb12,rb13, rb21,rb22,rb23,rb24,rb31,rb32,rb41,rb42,rb43,rb51,rb52,rb53;
    private RadioGroup rg1,rg2,rg3,rg4,rg5;
    private EditText instar, like, disLike, introduce;
    private TextView start;

    private Boolean pattern1, pattern2, pattern3, drink1, drink2, drink3, drink4, smoking1, smoking2, friend1, friend2, friend3, pet1, pet2, pet3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_second);

        userInfo = new UserInfo();

        pattern1 = pattern2 = pattern3 = drink1 = drink2 = drink3 = drink4 = smoking1 = smoking2 = friend1 = friend2 = friend3 = pet1 = pet2 = pet3 = false;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_info_test");
        //user_info 라는 파베의 테이블과 연동

        Intent intent = getIntent();

        userInfo = (UserInfo)intent.getSerializableExtra("userInfo");
        //회원가입 1페이지에서 받아온 데이터

        instar = (EditText)findViewById(R.id.join_etinstar) ;
        like = (EditText)findViewById(R.id.join_etlike);
        disLike = (EditText)findViewById(R.id.join_etdislike);
        introduce = (EditText)findViewById(R.id.join_etme);

        start = (TextView)findViewById(R.id.join_start);

        drawable=(GradientDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounding);
        drawable2=(GradientDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounding2);
        //라디오 이벤트
        rg1=findViewById(R.id.join_rglifestyle);
        rg2=findViewById(R.id.join_rgdrink);
        rg3=findViewById(R.id.join_rgcig);
        rg4=findViewById(R.id.join_rgfriend);
        rg5=findViewById(R.id.join_rgpet);
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
                checkclick(rb11);
                checkclick(rb12);
                checkclick(rb13);
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rb31);
                checkclick(rb32);
            }
        });
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rb41);
                checkclick(rb42);
                checkclick(rb43);
            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //int checkid=radioGroup.getCheckedRadioButtonId();
                //checkclick((RadioButton) findViewById(checkid));
                checkclick(rb51);
                checkclick(rb52);
                checkclick(rb53);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userInfo.setInstarID(instar.getText().toString());
//                userInfo.setLike(like.getText().toString());
//                userInfo.setDisLike(disLike.getText().toString());
//                userInfo.setIntroduce(introduce.getText().toString());
//                userInfo.setNow_date(new Date(System.currentTimeMillis()));
//
//                databaseReference.push().setValue(userInfo);
//
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                            Log.d("test", snapshot.getValue().toString());
//                            UserInfo test = new UserInfo();
////                            test = snapshot.getValue(UserInfo.class);
//                            //이런식으로 DTO랑 연동해서 데이터 가져올 수 있슴!
////                            Log.d("test2", test.getNow_date().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

                Intent signUpDone = new Intent(getApplicationContext(), ViewPagerMain.class);
                startActivity(signUpDone);
            }
        });

    }
    public void checkclick(RadioButton rb){
        if(rb.isChecked()){

            if(rb == rb11){
                if(pattern1){
                    userInfo.setPattern("");
                    pattern1 = false;

                    rg1.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setPattern("1");
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
                    userInfo.setPattern("2");
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
                    userInfo.setPattern("3");
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
                    userInfo.setDrink("1");
                    drink1 = true;
                    drink2 = false;
                    drink3 = false;
                    drink4 = false;

                    rg2.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb22){
                if(drink2){
                    userInfo.setDrink("0");
                    drink2 = false;

                    rg2.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setDrink("2");
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
                    userInfo.setDrink("3");
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
                    userInfo.setDrink("4");
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
                    userInfo.setSmoking("1");
                    smoking1 = true;
                    smoking2 = false;

                    rg3.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb32){
                if(smoking2){
                    userInfo.setSmoking("0");
                    smoking2 = false;

                    rg3.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setSmoking("2");
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
                    userInfo.setAllow_friend("1");
                    friend1 = true;
                    friend2 = false;
                    friend3 = false;

                    rg4.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rb42){
                if(friend2){
                    userInfo.setAllow_friend("0");
                    friend2 = false;

                    rg4.clearCheck();
                    radioClear(rb);
                }else{
                    userInfo.setAllow_friend("2");
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
                    userInfo.setAllow_friend("3");
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
                    userInfo.setPet("1");
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
                    userInfo.setPet("2");
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
                    userInfo.setPet("3");
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

}
