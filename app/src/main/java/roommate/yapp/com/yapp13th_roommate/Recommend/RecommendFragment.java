package roommate.yapp.com.yapp13th_roommate.Recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.ModifyMyInfo.ModifyMyInfoActivity;
import roommate.yapp.com.yapp13th_roommate.MyPage.MyPageFragment;
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

    private List<UserInfo> sameLocationUser;

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

        sameLocationUser = new ArrayList<>();
        global.randomTopUser = new ArrayList<>();
        ArrayList<Integer>ranNumber = new ArrayList<Integer>();
        int count = 0;

        for(int i = 0; i < global.everyInfo.size(); i++){
            if(global.myInfo.getLocation().equals(global.everyInfo.get(i).getLocation())){
                sameLocationUser.add(global.everyInfo.get(i));
                ranNumber.add(count++);
            }
            //나와 같은 위치를 원하는 모든 사용자만 따로 추려낸다
            //랜덤 값을 만들기 위한 배열을 만든다
        }

        Collections.shuffle(ranNumber);
        //shuffle을 이용하여 랜덤 인덱스를 섞는다
        if(sameLocationUser.size() < 3){
            for(int i = 0; i < sameLocationUser.size(); i++){
                global.randomTopUser.add(sameLocationUser.get(i));
            }
        }else{
            global.randomTopUser.add(sameLocationUser.get(ranNumber.get(0)));
            global.randomTopUser.add(sameLocationUser.get(ranNumber.get(1)));
            global.randomTopUser.add(sameLocationUser.get(ranNumber.get(2)));
        }
        //랜덤 인덱스를 이용하여 탑 뷰에 뿌려줄 데이터를 만든다


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

        //현재 데이터가 나를 제외하고 3개 미만만 받아오면 탑 리사이클러뷰가 return 3을 하기 때문에 개수가 3개보다 안되어서 팅김
        global.topRecyclerView = rootView.findViewById(R.id.topRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (context, LinearLayoutManager.HORIZONTAL, false);
        global.topRecyclerView.setLayoutManager(layoutManager);
        global.topRecyclerView.addItemDecoration(new TopSpacesItemDecoration(spacingInPixels));
        global.topAdapter = new TopRecyclerViewAdapter(context, global.randomTopUser);
        global.topRecyclerView.setAdapter(global.topAdapter);


        global.bottomRecyclerView = rootView.findViewById(R.id.recyclerView);
        global.bottomRecyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        global.bottomRecyclerView.setNestedScrollingEnabled(false);
        global.bottomRecyclerView.addItemDecoration(new BottomSpacesItemDecoration(spacingInPixels));
        global.bottomAdapter = new BottomRecyclerViewAdapter(context, global.filterInfo);
        global.bottomRecyclerView.setAdapter(global.bottomAdapter);





        // todo 여기부분에서 메인 추천화면에서 myPage로 넘어가는 이벤트 처리 추가


//        Button bt = (Button) rootView.findViewById(R.id.main_myinfo);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(getActivity(), MyPageFragment.class);
////                startActivity(intent);
//                Fragment fragment = new MyPageFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.home_frg, fragment);
//                fragmentTransaction.commit();
//            }
//        });



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