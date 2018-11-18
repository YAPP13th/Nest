package roommate.yapp.com.yapp13th_roommate.Recommend;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.darsh.multipleimageselect.helpers.Constants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.FirebaseFunc;
import roommate.yapp.com.yapp13th_roommate.Function.RadioFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.ModifyMyInfo.ModifyMyInfoActivity;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.BottomRecyclerViewAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.ItemDecorations.BottomSpacesItemDecoration;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpFirstActivity;
import roommate.yapp.com.yapp13th_roommate.SignUp.WebViewActivity;
import roommate.yapp.com.yapp13th_roommate.ViewPager.RoomImageModifyPagerAdapter;

public class FABFragment extends AAH_FabulousFragment {

    private RadioFunc radioFunc;
    private GlobalVariable global;
    private String findAddress;//일단 주소 받아온것 여기저장,,
    private static final Object RecommendFragment = new RecommendFragment();

    private RelativeLayout layout_filter;
    private LinearLayout layout_filter_static;
    private TextView tv_money_in_filter;
    private SeekBar seekBar_in_filter;
    private RadioGroup rg_house_in_filter, rg_pattern_in_filter, rg_drink_in_filter,
            rg_smoking_in_filter, rg_friend_in_filter, rg_pet_in_filter;
    private RadioButton[] rb_house_in_filter, rb_pattern_in_filter, rb_drink_in_filter,
            rb_smoking_in_filter, rb_friend_in_filter, rb_pet_in_filter;
    private Boolean[] roomCheck ,patternCheck, drinkCheck, smokingCheck, friendCheck, petCheck;
    private Button btn_cancel, btn_filter;
    private Spinner spinner_in_filter;
    private TextView join_location;
    private String[] years;
    private final int SEARCH_ADDRESS_ACTIVITY = 1115;
    private BottomRecyclerViewAdapter bottom_adapter;

    public static FABFragment newInstance() {
        FABFragment f = new FABFragment();
        return f;
//        return new FABFragment();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        global = (GlobalVariable)getActivity().getApplication();
        radioFunc = new RadioFunc(getActivity());

        global.temp = new UserInfo();
        global.temp.setMonthly("0");
        global.temp.setPattern("");
        global.temp.setDrink("");
        global.temp.setSmoking("");
        global.temp.setAllow_friend("");
        global.temp.setPet("");

        roomCheck = new Boolean[2];
        patternCheck = new Boolean[3];
        drinkCheck = new Boolean[4];
        smokingCheck = new Boolean[2];
        friendCheck = new Boolean[3];
        petCheck = new Boolean[3];

        Arrays.fill(roomCheck, false);
        Arrays.fill(patternCheck, false);
        Arrays.fill(drinkCheck, false);
        Arrays.fill(smokingCheck, false);
        Arrays.fill(friendCheck, false);
        Arrays.fill(petCheck, false);

        View view = View.inflate(getContext(), R.layout.fragment_filter, null);
        join_location=view.findViewById(R.id.join_location);
        layout_filter = view.findViewById(R.id.layout_filter);
        layout_filter_static = view.findViewById(R.id.layout_filter_static);
        tv_money_in_filter = view.findViewById(R.id.tv_money_in_filter);
        seekBar_in_filter = view.findViewById(R.id.seekBar_in_filter);
        rg_house_in_filter = view.findViewById(R.id.rg_house_in_filter);
        rg_pattern_in_filter = view.findViewById(R.id.rg_pattern_in_filter);
        rg_drink_in_filter = view.findViewById(R.id.rg_drink_in_filter);
        rg_smoking_in_filter = view.findViewById(R.id.rg_smoking_in_filter);
        rg_friend_in_filter = view.findViewById(R.id.rg_friend_in_filter);
        rg_pet_in_filter = view.findViewById(R.id.rg_pet_in_filter);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_filter = view.findViewById(R.id.btn_filter);
        spinner_in_filter = view.findViewById(R.id.spinner_in_filter);

        // radioButton
        rb_house_in_filter = new RadioButton[2];
        rb_house_in_filter[0] = view.findViewById(R.id.rb_house_o_in_filter);
        rb_house_in_filter[1] = view.findViewById(R.id.rb_house_x_in_filter);
        rb_pattern_in_filter = new RadioButton[3];
        rb_pattern_in_filter[0] = view.findViewById(R.id.rb_morning_pattern_in_filter);
        rb_pattern_in_filter[1] = view.findViewById(R.id.rb_evening_pattern_in_filter);
        rb_pattern_in_filter[2] = view.findViewById(R.id.rb_random_pattern_in_filter);
        rb_drink_in_filter = new RadioButton[4];
        rb_drink_in_filter[0] = view.findViewById(R.id.rb_no_drink_in_filter);
        rb_drink_in_filter[1] = view.findViewById(R.id.rb_everyday_drink_in_filter);
        rb_drink_in_filter[2] = view.findViewById(R.id.rb_week_drink_in_filter);
        rb_drink_in_filter[3] = view.findViewById(R.id.rb_month_drink_in_filter);
        rb_smoking_in_filter = new RadioButton[2];
        rb_smoking_in_filter[0] = view.findViewById(R.id.rb_o_smoking_in_filter);
        rb_smoking_in_filter[1] = view.findViewById(R.id.rb_x_smoking_in_filter);
        rb_friend_in_filter = new RadioButton[3];
        rb_friend_in_filter[0] = view.findViewById(R.id.rb_o_friend_in_filter);
        rb_friend_in_filter[1] = view.findViewById(R.id.rb_x_friend_in_filter);
        rb_friend_in_filter[2] = view.findViewById(R.id.rb_maybe_friend_in_filter);
        rb_pet_in_filter = new RadioButton[3];
        rb_pet_in_filter[0] = view.findViewById(R.id.rb_o_pet_in_filter);
        rb_pet_in_filter[1] = view.findViewById(R.id.rb_x_pet_in_filter);
        rb_pet_in_filter[2] = view.findViewById(R.id.rb_maybe_pet_in_filter);

        rg_house_in_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.roomCheck(rg_house_in_filter, rb_house_in_filter[0], rb_house_in_filter, roomCheck);
                radioFunc.roomCheck(rg_house_in_filter, rb_house_in_filter[1], rb_house_in_filter, roomCheck);
            }
        });

        rg_pattern_in_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.patternCheck(rg_pattern_in_filter, rb_pattern_in_filter[0], rb_pattern_in_filter, patternCheck);
                radioFunc.patternCheck(rg_pattern_in_filter, rb_pattern_in_filter[1], rb_pattern_in_filter, patternCheck);
                radioFunc.patternCheck(rg_pattern_in_filter, rb_pattern_in_filter[2], rb_pattern_in_filter, patternCheck);
            }
        });

        rg_drink_in_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.drinkCheck(rg_drink_in_filter, rb_drink_in_filter[0], rb_drink_in_filter, drinkCheck);
                radioFunc.drinkCheck(rg_drink_in_filter, rb_drink_in_filter[1], rb_drink_in_filter, drinkCheck);
                radioFunc.drinkCheck(rg_drink_in_filter, rb_drink_in_filter[2], rb_drink_in_filter, drinkCheck);
                radioFunc.drinkCheck(rg_drink_in_filter, rb_drink_in_filter[3], rb_drink_in_filter, drinkCheck);
            }
        });
        rg_smoking_in_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.smokingCheck(rg_smoking_in_filter, rb_smoking_in_filter[0], rb_smoking_in_filter, smokingCheck);
                radioFunc.smokingCheck(rg_smoking_in_filter, rb_smoking_in_filter[1], rb_smoking_in_filter, smokingCheck);
            }
        });
        rg_friend_in_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.allowFriendCheck(rg_friend_in_filter, rb_friend_in_filter[0], rb_friend_in_filter, friendCheck);
                radioFunc.allowFriendCheck(rg_friend_in_filter, rb_friend_in_filter[1], rb_friend_in_filter, friendCheck);
                radioFunc.allowFriendCheck(rg_friend_in_filter, rb_friend_in_filter[2], rb_friend_in_filter, friendCheck);
            }
        });
        rg_pet_in_filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioFunc.petCheck(rg_pet_in_filter, rb_pet_in_filter[0], rb_pet_in_filter, petCheck);
                radioFunc.petCheck(rg_pet_in_filter, rb_pet_in_filter[1], rb_pet_in_filter, petCheck);
                radioFunc.petCheck(rg_pet_in_filter, rb_pet_in_filter[2], rb_pet_in_filter, petCheck);
            }
        });


        // spinner
        years = getResources().getStringArray(R.array.year);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_in_filter.setAdapter(arrayAdapter);
        spinner_in_filter.setSelection(0);

        // seekBar
        seekBar_in_filter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int prog = i*10;

                if (prog == 0) {
                    tv_money_in_filter.setText(prog + "만원");
                } else {
                    tv_money_in_filter.setText("0~" + prog + "만원");
                }

                global.temp.setMonthly(Integer.toString(prog));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //웹뷰 인텐트
        join_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);



            }
        });

        // filtering button
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // parameter로 넘기고 싶은 object를 넘기면 됩니다.
                closeFilter("closed");
                // RecommendFragment 내 onResult에서 parameter로 받아서 파베로 가면 됩니다.

//                2014 - Integer.parseInt(global.myInfo.getYear())
                global.filterInfo = new ArrayList<>();
                global.temp.setYear(spinner_in_filter.getSelectedItem().toString());

                int i = 0;

                for(i = 0;i < global.everyInfo.size();i++){
                    if(global.everyInfo.get(i).getYear().equals(global.temp.getYear()) && Integer.parseInt(global.everyInfo.get(i).getMonthly()) <= Integer.parseInt(global.temp.getMonthly())
                            && ((global.temp.getRoom() == null) || (global.temp.getRoom() == global.everyInfo.get(i).getRoom()))
                            && ((global.temp.getPattern().equals("")) || (global.temp.getPattern().equals(global.everyInfo.get(i).getPattern())))
                            && ((global.temp.getDrink().equals("")) || (global.temp.getDrink().equals(global.everyInfo.get(i).getDrink())))
                            && ((global.temp.getSmoking().equals("")) || (global.temp.getSmoking().equals(global.everyInfo.get(i).getSmoking())))
                            && ((global.temp.getAllow_friend().equals("")) || (global.temp.getAllow_friend().equals(global.everyInfo.get(i).getAllow_friend())))
                            && ((global.temp.getPet().equals("")) || (global.temp.getPet().equals(global.everyInfo.get(i).getPet()))) ){
                        //필터 조건
                        //클릭 안 된 조건은 상관없이 모두 찾는다
                        //클릭 된 조건은 조건과 일치하는 사람만 찾는다
                        //나이는 일단 딱 같은 나이만
                        //월세는 선택 한 월세보다 적은 사람만 ex) 0~50 이면 50만 이하 모두

                        global.filterInfo.add(global.everyInfo.get(i));

                    }

                    bottom_adapter = new BottomRecyclerViewAdapter(getActivity(), global.filterInfo);
                    global.bottomRecyclerView.setAdapter(bottom_adapter);
                    global.bottomRecyclerView.setNestedScrollingEnabled(false);

                    //적용 된 필터를 새로운 recycleView adapter 를 만든 다음 원래 프래그 먼트에 적용

                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFilter("cancel");

                global.filterInfo = new ArrayList<>();
                global.filterInfo = global.everyInfo;

                bottom_adapter = new BottomRecyclerViewAdapter(getActivity(), global.filterInfo);
                global.bottomRecyclerView.setAdapter(bottom_adapter);
                global.bottomRecyclerView.setNestedScrollingEnabled(false);

                //필터 적용 해제 후, 새로운 recycleView adapter 를 만든 다음 원래 프래그 먼트에 적용
            }
        });

        setCallbacks((Callbacks) RecommendFragment);
        setViewgroupStatic(layout_filter_static);
        setViewMain(layout_filter);
        setMainContentView(view);
        super.setupDialog(dialog, style);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            String d = data.getExtras().getString("data");
            if (d != null) {
                String[] values = d.split(" ");
                findAddress = values[0] + " " + values[1];
                join_location.setText(findAddress);// 구 까지 자른거,,,
                global.temp.setLocation(findAddress);
            }


    }
}
