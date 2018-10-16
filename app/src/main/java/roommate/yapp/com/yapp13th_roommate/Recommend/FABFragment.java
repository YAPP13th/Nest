package roommate.yapp.com.yapp13th_roommate.Recommend;

import android.app.Dialog;
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


import roommate.yapp.com.yapp13th_roommate.Function.RadioFunc;
import roommate.yapp.com.yapp13th_roommate.R;

public class FABFragment extends AAH_FabulousFragment {

    private RadioFunc radioFunc;

    private static final Object RecommendFragment = new RecommendFragment();

    private RelativeLayout layout_filter;
    private LinearLayout layout_filter_static;
    private TextView tv_money_in_filter;
    private SeekBar seekBar_in_filter;
    private RadioGroup rg_house_in_filter, rg_pattern_in_filter, rg_drink_in_filter,
            rg_smoking_in_filter, rg_friend_in_filter, rg_pet_in_filter;
    private RadioButton[] rb_house_in_filter, rb_pattern_in_filter, rb_drink_in_filter,
            rb_smoking_in_filter, rb_friend_in_filter, rb_pet_in_filter;
    private Button btn_filter;
    private Spinner spinner_in_filter;

    private String[] years;

    public static FABFragment newInstance() {
        return new FABFragment();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        radioFunc = new RadioFunc(getContext());

        View view = View.inflate(getContext(), R.layout.fragment_filter, null);
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
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // filtering button
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // parameter로 넘기고 싶은 object를 넘기면 됩니다.
                closeFilter("closed");
                // RecommendFragment 내 onResult에서 parameter로 받아서 파베로 가면 됩니다.
            }
        });

        setViewMain(layout_filter);
        setMainContentView(view);
        setCallbacks((Callbacks) RecommendFragment);
        setViewgroupStatic(layout_filter_static);
        super.setupDialog(dialog, style);
    }
}
