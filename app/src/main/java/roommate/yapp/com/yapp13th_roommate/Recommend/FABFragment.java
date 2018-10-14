package roommate.yapp.com.yapp13th_roommate.Recommend;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;


import roommate.yapp.com.yapp13th_roommate.R;

public class FABFragment extends AAH_FabulousFragment {

    private static final Object RecommendFragment = new RecommendFragment();
    private RelativeLayout layout_filter;
    private LinearLayout layout_filter_static;
    private Button btn_filter;

    public static FABFragment newInstance() {
        return new FABFragment();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View view = View.inflate(getContext(), R.layout.fragment_filter, null);
        layout_filter = view.findViewById(R.id.layout_filter);
        layout_filter_static = view.findViewById(R.id.layout_filter_static);
        btn_filter = view.findViewById(R.id.btn_filter);

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter("closed");
            }
        });

        setViewMain(layout_filter);
        setMainContentView(view);
        setCallbacks((Callbacks) RecommendFragment);
        setViewgroupStatic(layout_filter_static);
        super.setupDialog(dialog, style);
    }
}
