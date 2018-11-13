package roommate.yapp.com.yapp13th_roommate.LikeList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.LikeList.DeleteList.ListviewAdapter;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.BottomRecyclerViewAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.TopRecyclerViewAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.ItemDecorations.BottomSpacesItemDecoration;

public class LikeFragment extends Fragment {
    private GlobalVariable global;
    private Context context;
    private ImageView btnDel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView topList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        global = GlobalVariable.getGlobalApplicationContext();

        View view = inflater.inflate(R.layout.fragment_picked, container, false);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space_for_top_items);
        LinearLayoutManager mLayoutManager;
        global.likeRecyclerView = (RecyclerView) view.findViewById(R.id.pick_recyclerview);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        global.likeRecyclerView.setLayoutManager(mLayoutManager);
        global.likeRecyclerView.addItemDecoration(new BottomSpacesItemDecoration(spacingInPixels));
        global.likeAdapter = new LikeAdapter(context, global.likeInfo);
        global.likeRecyclerView.setAdapter(global.likeAdapter);

        topList=view.findViewById(R.id.toplist);
        ListviewAdapter listviewAdapter= new ListviewAdapter(context);
        topList.setAdapter(listviewAdapter);

        return view;
    }
}

