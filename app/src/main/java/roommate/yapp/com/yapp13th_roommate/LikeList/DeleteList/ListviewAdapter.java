package roommate.yapp.com.yapp13th_roommate.LikeList.DeleteList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.LikeList.LikeAdapter;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.BottomRecyclerViewAdapter;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.TopRecyclerViewAdapter;

public class ListviewAdapter extends BaseAdapter {
    private GlobalVariable global;
    private List<String> delList;
    LayoutInflater inflater=null;
    ImageView btnDel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Context context;

    public ListviewAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        global = GlobalVariable.getGlobalApplicationContext();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            final Context context = viewGroup.getContext();
            view=inflater.inflate(R.layout.listview_item, viewGroup,false);
            btnDel=view.findViewById(R.id.btnDeletePick2);
        }
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(global.delCheckList == null)){
                    for(int i=0;i<global.likeInfo.size();i++) {
                        for (int j = 0; j < global.delCheckList.size(); j++) {
                            if (global.likeInfo.get(i).getId().equals(global.delCheckList.get(j))){
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                databaseReference = firebaseDatabase.getReference("like");
                                databaseReference.child(global.myInfo.getId()).child(global.delCheckList.get(j)).removeValue();
                                global.likeInfo.remove(i);//likeinfo 같은 아이디값 지우기
                                global.delCheckList.remove(j);
                                j--; //삭제목록 1지우고 1줄이기

                                global.bottomAdapter = new BottomRecyclerViewAdapter(context, global.filterInfo);
                                global.bottomRecyclerView.setAdapter(global.bottomAdapter);

                                global.topAdapter = new TopRecyclerViewAdapter(context, global.randomTopUser);
                                global.topRecyclerView.setAdapter(global.topAdapter);

                                global.likeAdapter = new LikeAdapter(context, global.likeInfo);
                                global.likeRecyclerView.setAdapter(global.likeAdapter);
                            }
                        }
                    }
                }
            }
        });

        return view;
    }
}
