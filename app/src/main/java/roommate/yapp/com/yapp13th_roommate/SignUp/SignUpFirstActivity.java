package roommate.yapp.com.yapp13th_roommate.SignUp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Function.RadioFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class SignUpFirstActivity extends AppCompatActivity {

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;

    private EditText etName, etOpenChat;
    private ImageView ivMyProfile;
    private RadioButton[] rbGender, rbRoom;
    private SeekBar seekBar;
    private int prog;
    private RadioGroup rg1,rg2;
    private Spinner spinner;

    private GlobalVariable global;
    private ImageFunc imageFunc;
    private RadioFunc radioFunc;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_first);

        global = (GlobalVariable)getApplicationContext();
        imageFunc = new ImageFunc(this);
        radioFunc = new RadioFunc(this);

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
                imageFunc.selectGallery(ivMyProfile);
            }
        });

        //싴바 테스트

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

                global.myInfo.setMonthly(Integer.toString(prog));
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

                global.myInfo.setName(etName.getText().toString());
                global.myInfo.setYear(spinner.getSelectedItem().toString());
                global.myInfo.setOpenChatURL(etOpenChat.getText().toString());

                if((global.myInfo.getName() == null || global.myInfo.getName().equals("")) || (global.myInfo.getGender() == null || global.myInfo.getGender().equals(""))
                        || (global.myInfo.getRoom() == null || global.myInfo.getRoom().equals("")) || (global.myInfo.getOpenChatURL() == null || global.myInfo.getOpenChatURL().equals(""))){
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
                    Intent intent = new Intent(SignUpFirstActivity.this,SignUpSecondActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //갤러리 선택 or 카메라로 촬영 선택에 따른 onActivityResult
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    imageFunc.sendPicture(data.getData());
                    break;
                case CAMERA_CODE:
                    imageFunc.getPictureForPhoto();
                    break;

                default:
                    break;
            }

        }
    }

}