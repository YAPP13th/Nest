package roommate.yapp.com.yapp13th_roommate.SignUp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import roommate.yapp.com.yapp13th_roommate.Datamodel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.R;

public class SignUp_First_Activity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private EditText name;
    private ImageView imageView;
    private RadioButton rbf, rbm, rbroomo,rbroomx;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rg1,rg2;
    private GradientDrawable drawable,drawable2;
    private Spinner spinner;
    private TextView juso;

    private UserInfo userInfo;
    //유저 정보 DTO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join1);

        userInfo = new UserInfo();

        name = (EditText)findViewById(R.id.join_etname);

        //스피너에 연도 값 넣기,,
//        String[] years={"1999","1998","1997","1996","1995","1994","1993",
//                "1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982","1981","1980","1979"};

        Resources res = getResources();
        String[] years = res.getStringArray(R.array.year);

        //Strings.xml에 넣기부터 시작
        //변수명이랑 연동 방법 등등 내 맛으로 바꿔야겠어
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

                userInfo.setMonthly(prog);
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
        rg2=findViewById(R.id.join_rgroom);
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

                userInfo.setName(name.getText().toString());
                userInfo.setYear(Integer.parseInt(spinner.getSelectedItem().toString()));

                if((userInfo.getName() == null || userInfo.getName().equals("")) || (userInfo.getGender() == null || userInfo.getGender().equals("")) || (userInfo.getRoom() == null || userInfo.getRoom().equals(""))){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_First_Activity.this);
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
                    Intent intent = new Intent(getApplicationContext(),SignUp_Second_Activity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userInfo", userInfo);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }


            }
        });

        //주소검색(구 단위로 자르는거 해야함)
        juso=findViewById(R.id.join_tvjuso);
        juso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp_First_Activity.this, WebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
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

    //주소 검색 했을때 돌아오는 결과
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){

            case SEARCH_ADDRESS_ACTIVITY:

                if(resultCode == RESULT_OK){

                    String address = data.getExtras().getString("data");
                    if (address != null)
                        juso.setText(address);

                }
                break;

        }
    }
}
