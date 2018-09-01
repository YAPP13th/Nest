package roommate.yapp.com.yapp13th_roommate;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Join1Activity extends AppCompatActivity {
    ImageView imageView;
    RadioButton rbf, rbm, rbroomo,rbroomx;
    SeekBar seekBar;
    int prog;
    RadioGroup rg1,rg2;
    GradientDrawable drawable,drawable2;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join1);

        //스피너에 연도 값 넣기,,
        String[] years={"1999","1998","1997","1996","1995","1994","1993",
                "1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982","1981","1980","1979"};
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

        //싴바 테스트

        final TextView tvprog=findViewById(R.id.join_tvprog);
        seekBar=findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prog=i*10;
                if(prog==0)
                    tvprog.setText(prog+"만원");
                else
                    tvprog.setText("0~"+prog+"만원");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //라디오 이벤트
        rg1=findViewById(R.id.join_rggender);
        rbf=findViewById(R.id.join_rbf);
        rbm=findViewById(R.id.join_rbm);
        rbroomo=findViewById(R.id.join_rbroomo);
        rbroomx=findViewById(R.id.join_rbroomx);
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
        rg2=findViewById(R.id.join_rgroom);
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
        TextView tvnext=findViewById(R.id.join_btnnext);
        tvnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Join2Activity.class);
                startActivity(intent);
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
