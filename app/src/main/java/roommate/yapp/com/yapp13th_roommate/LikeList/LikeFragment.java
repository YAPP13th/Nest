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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class LikeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LikeAdapter mAdapter;
    private GlobalVariable global;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<UserInfo> likeData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        mAdapter = new LikeAdapter(this.getActivity(), global.filterInfo);
//        mAdapter = new LikeAdapter(getContext(), global.filterInfo);
        // TODO: 2018. 10. 17. 여기부분에 파이어베이스 값 가져와서 값을 넘겨주면 될거같다.

//        firebase_like_get();
//        Log.e("global 2 :->" , String.valueOf(global.likeInfo));
//        mAdapter = new LikeAdapter(context,global.likeInfo);
//        Log.e("global 2 :->" , String.valueOf(global.likeInfo));
//        mRecyclerView.setAdapter(mAdapter);
        // TODO: 2018. 10. 18.
        global = GlobalVariable.getGlobalApplicationContext();

        Log.e("global 2 :->" , String.valueOf(global.likeInfo));
        View view = inflater.inflate(R.layout.fragment_picked, container, false);


        LinearLayoutManager mLayoutManager;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pick_recyclerview);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new LikeAdapter(context,likeData);
        likeData = new ArrayList<UserInfo>();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("like");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        UserInfo temp = snapshot.getValue(UserInfo.class);
                        temp.setKey(snapshot.getKey());
//                        likeData = new ArrayList<UserInfo>();
//                        temp.setKey(snapshot.getKey());
//                        global.likeInfo.add(temp);
                        likeData.add(temp);

                        Log.e("global 2 :->" , String.valueOf(likeData));
//                        likeData.add(temp);




                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }

//                        if(temp.getId().equals(global.getMyId())){
//                            global.myInfo = temp ;
//                            temp.setKey(snapshot.getKey());
//                            global.setExist(true);
//                        }


                }
                Log.e("global 2 :->" , String.valueOf(global.likeInfo));
                mRecyclerView.setAdapter(mAdapter);

//                likeData = global.likeInfo;
//                likeData = (UserInfo) global.likeInfo;
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    UserInfo temp = snapshot.getValue(UserInfo.class);
//
//                        temp.setKey(snapshot.getKey());
//                        global.everyInfo.add(temp);
//                        global.filterInfo.add(temp);
//
//                }

//                Login(loginProgres);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });






        // Inflate the layout for this fragment

        return view;
    }
<<<<<<< HEAD
//
//    public void firebase_like_get(){
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("like");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        try {
//                            UserInfo temp = snapshot.getValue(UserInfo.class);
//                            temp.setKey(snapshot.getKey());
//                            global.likeInfo.add(temp);
//
//                            Log.e("global 2 :->" , String.valueOf(global.likeInfo));
//                            likeData.add(temp);
//
//                        }catch(NullPointerException e){
//                           e.printStackTrace();
//                        }
//
////                        if(temp.getId().equals(global.getMyId())){
////                            global.myInfo = temp ;
////                            temp.setKey(snapshot.getKey());
////                            global.setExist(true);
////                        }
//
//
//                }
////                likeData = global.likeInfo;
////                likeData = (UserInfo) global.likeInfo;
////
////                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
////                    UserInfo temp = snapshot.getValue(UserInfo.class);
////
////                        temp.setKey(snapshot.getKey());
////                        global.everyInfo.add(temp);
////                        global.filterInfo.add(temp);
////
////                }
//
////                Login(loginProgres);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });
//
//
//    }
}
=======
}
>>>>>>> e94d4598334349dad653b4a2de26ff71b66bfea0
