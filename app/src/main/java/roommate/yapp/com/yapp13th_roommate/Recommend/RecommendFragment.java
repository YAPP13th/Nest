package roommate.yapp.com.yapp13th_roommate.Recommend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;

import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.BottomRecyclerViewAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.TopRecyclerViewAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.ItemDecorations.BottomSpacesItemDecoration;
import roommate.yapp.com.yapp13th_roommate.Recommend.ItemDecorations.TopSpacesItemDecoration;

public class RecommendFragment extends Fragment
        implements BottomRecyclerViewAdapter.ItemClickListener, AAH_FabulousFragment.Callbacks {

    private Context context;
    private FloatingActionButton btn_fab;

    private GlobalVariable global;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global = (GlobalVariable)getActivity().getApplication();

        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btn_fab = rootView.findViewById(R.id.btn_fab);

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FABFragment fabFragment = FABFragment.newInstance();
                fabFragment.setParentFab(btn_fab);
                fabFragment.show(getActivity().getSupportFragmentManager(), fabFragment.getTag());
            }
        });

        int numberOfColumns = 1;
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space_for_top_items);

        global.topRecyclerView = rootView.findViewById(R.id.topRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (context, LinearLayoutManager.HORIZONTAL, false);
        global.topRecyclerView.setLayoutManager(layoutManager);
        global.topRecyclerView.addItemDecoration(new TopSpacesItemDecoration(spacingInPixels));
        global.topAdapter = new TopRecyclerViewAdapter(context, global.everyInfo);
        global.topRecyclerView.setAdapter(global.topAdapter);


        global.bottomRecyclerView = rootView.findViewById(R.id.recyclerView);
        global.bottomRecyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        global.bottomRecyclerView.setNestedScrollingEnabled(false);
        global.bottomRecyclerView.addItemDecoration(new BottomSpacesItemDecoration(spacingInPixels));
        global.bottomAdapter = new BottomRecyclerViewAdapter(context, global.filterInfo);
        global.bottomRecyclerView.setAdapter(global.bottomAdapter);


        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResult(Object result) {
        Log.d("CALLBACK", "onResult: " + result.toString());
        if (result.toString().equalsIgnoreCase("swiped_down")) {

        } else {
        }
    }
}