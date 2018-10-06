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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.R;

public class SignUpFirstActivity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private EditText name, openChat;
    private ImageView imageView;
    private RadioButton rbf, rbm, rbroomo,rbroomx;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rg1,rg2;
    private GradientDrawable drawable,drawable2;
    private Spinner spinner;
    private TextView location;

    private UserInfo userInfo;
    //유저 정보 DTO

    private ImageView iv1, iv2,iv3,iv4,iv5,iv6,iv7;
    //소제콕 텍스트 앞에 붙는 이미지(위에서부터 순서대로 번호)
    private ImageView ivBack, ivRoomPic, ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_first);

        userInfo = new UserInfo();

        location=findViewById(R.id.join_location);
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

        //이미지뷰 세팅
        iv1=findViewById(R.id.ivName);
        iv2=findViewById(R.id.ivGender);
        iv3=findViewById(R.id.ivBirth);
        iv4=findViewById(R.id.ivMonthly);
        iv5=findViewById(R.id.ivChatURL);
        iv6=findViewById(R.id.ivRoom);
        iv7=findViewById(R.id.ivLocation);
        ivBack=findViewById(R.id.ivBack);
        ivRoomPic=findViewById(R.id.join_ivroompotho);

        //이미지 삽입(후에 기기 화면 크기따라서 해상도 조절해야함) 급하니까 xhdpi로 다 넣어놓음
        iv1.setImageResource(R.drawable.myprofileedit_first_name_icon);
        iv2.setImageResource(R.drawable.myprofileedit_first_sex_icon);
        iv3.setImageResource(R.drawable.myprofileedit_birth_icon);
        iv4.setImageResource(R.drawable.myprofileedit_money_icon);
        iv5.setImageResource(R.drawable.myprofileedit_chat_icon);
        iv6.setImageResource(R.drawable.myprofileedit_house_icon);
        iv7.setImageResource(R.drawable.myprofileedit_location_icon);


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
        //위치검색 했을때
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpFirstActivity.this, WebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }

    //webview액티비티에 인텐트 결과(주소)받아서 텍스트바꾸기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){

            case SEARCH_ADDRESS_ACTIVITY:

                if(resultCode == RESULT_OK){

                    String d = data.getExtras().getString("data");
                    if (d != null)
                        location.setText(d);

                }
                break;

        }
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


}
