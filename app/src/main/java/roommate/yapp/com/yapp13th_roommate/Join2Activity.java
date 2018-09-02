package roommate.yapp.com.yapp13th_roommate;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Join2Activity extends AppCompatActivity {
    GradientDrawable drawable,drawable2;
    RadioButton rb11, rb12,rb13, rb21,rb22,rb23,rb24,rb31,rb32,rb41,rb42,rb43,rb51,rb52,rb53;
    RadioGroup rg1,rg2,rg3,rg4,rg5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

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
    }
    public void checkclick(RadioButton rb){
        if(rb.isChecked()){
            rb.setBackground(drawable);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rb.setClipToOutline(true);
            }
        }else{
            rb.setBackground(drawable2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rb.setClipToOutline(true);
            }
        }
    }
}
