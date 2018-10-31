package roommate.yapp.com.yapp13th_roommate.Recommend.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.DetailInfo.DetailInfoActivity;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.LikeList.LikeAdapter;
import roommate.yapp.com.yapp13th_roommate.R;

public class TopRecyclerViewAdapter extends RecyclerView.Adapter<TopRecyclerViewAdapter.ViewHolder> {
    private List<UserInfo> mData;
    private LayoutInflater mInflater;
    private TopRecyclerViewAdapter.ItemClickListener mClickListener;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private GlobalVariable global;
    private ImageFunc imageFunc;

    public TopRecyclerViewAdapter(Context context, List<UserInfo> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_top_recyclerview, parent, false);
        global = GlobalVariable.getGlobalApplicationContext();
        imageFunc = new ImageFunc(context);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        String animal = mData[position];
//        holder.myTextView.setText(animal);

        if(mData != null) {
            mData.get(position).setLikeFrom(global.myInfo.getId());
            final UserInfo dataModel = mData.get(position);
            final TopRecyclerViewAdapter.ViewHolder fHolder = holder;

            if (!(mData.get(position).getProfile_image() == null || mData.get(position).getProfile_image().isEmpty())) {
                byte[] image = Base64.decode(mData.get(position).getProfile_image(), Base64.DEFAULT);
                Bitmap decodeByte = BitmapFactory.decodeByteArray(image, 0, image.length);
                decodeByte = imageFunc.incisionToTopRecyclerImage(decodeByte);
                //상, 하 10% 씩 잘라내는 함수

                holder.iv_profile.setImageBitmap(decodeByte);
            }

            holder.tv_name.setText(mData.get(position).getName());
            holder.tv_age.setText(mData.get(position).getYear());
            holder.tv_address.setText(mData.get(position).getLocation());
            holder.tv_monthMoney.setText(mData.get(position).getMonthly());
            holder.tv_selfIntroduction.setText(mData.get(position).getIntroduce());
            holder.tv_like.setText(mData.get(position).getLike());
            holder.tv_dislike.setText(mData.get(position).getDisLike());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), DetailInfoActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("bottom", position);
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });

            if(!(global.likeInfo == null)){
                for (int i = 0; i < global.likeInfo.size(); i++) {
                    if (global.likeInfo.get(i).getId().equals(mData.get(position).getId())) {
                        holder.btn_top_recycler_pick.setLiked(true);
                    }
                }
            }


            holder.btn_top_recycler_pick.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    fHolder.btn_top_recycler_pick.setEnabled(false);
                    //파베 처리하는 동안 버튼 클릭 금지
                    try {

                        global.likeInfo.add(dataModel);

                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference("like");

                        databaseReference.push().setValue(dataModel, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError,
                                                   DatabaseReference databaseReference) {
                                String uniqueKey = databaseReference.getKey();
                                global.likeInfo.get(global.likeInfo.size() - 1).setKey(uniqueKey);

                                Map<String, Object> taskMap = new HashMap<String, Object>();
                                taskMap.put("key", uniqueKey);
                                //기존 데이터에 키 값을 추가하기 위한 해쉬맵 생성

                                databaseReference.updateChildren(taskMap);
                                //데이터 베이스에 데이터 등록 후 키값을 받아와 myInfo에 반영 및 데이터베이스에 업데이트

                                global.likeAdapter = new LikeAdapter(context, global.likeInfo);
                                global.likeRecyclerView.setAdapter(global.likeAdapter);

                                global.bottomAdapter = new BottomRecyclerViewAdapter(context, global.filterInfo);
                                global.bottomRecyclerView.setAdapter(global.bottomAdapter);

                                fHolder.btn_top_recycler_pick.setEnabled(true);
                            }
                        });
                    }catch (NullPointerException e){
                        fHolder.btn_top_recycler_pick.setEnabled(true);
                        e.printStackTrace();
                    }

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    //comment Like Button FALSE
                    fHolder.btn_top_recycler_pick.setEnabled(false);
                    //파베 처리하는 동안 버튼 클릭 금지
                    try{
                        for(int i = 0; i < global.likeInfo.size(); i++){
                            if(global.likeInfo.get(i).getId().equals(mData.get(position).getId())){
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                databaseReference = firebaseDatabase.getReference("like");

                                databaseReference.child(global.likeInfo.get(i).getKey()).removeValue();
                                global.likeInfo.remove(i);

                                global.likeAdapter = new LikeAdapter(context, global.likeInfo);
                                global.likeRecyclerView.setAdapter(global.likeAdapter);

                                global.bottomAdapter = new BottomRecyclerViewAdapter(context, global.filterInfo);
                                global.bottomRecyclerView.setAdapter(global.bottomAdapter);

                                fHolder.btn_top_recycler_pick.setEnabled(true);
                                break;
                            }
                        }
                    }catch (NullPointerException e){
                        fHolder.btn_top_recycler_pick.setEnabled(true);
                        e.printStackTrace();
                    }

                }
            });

        }
    }

    @Override
    public int getItemCount() {

//        return mData.size();
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_profile;
        TextView tv_name, tv_age, tv_address, tv_monthMoney, tv_selfIntroduction, tv_like, tv_dislike;
        private LikeButton btn_top_recycler_pick;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_top_profile);
            tv_name = itemView.findViewById(R.id.tv_top_name);
            tv_age = itemView.findViewById(R.id.tv_top_recycler_birth_year);
            tv_address = itemView.findViewById(R.id.tv_top_address);
            tv_monthMoney = itemView.findViewById(R.id.tv_top_recycler_monthly_money);
            tv_selfIntroduction = itemView.findViewById(R.id.tv_top_selfIntroduction);
            tv_like = itemView.findViewById(R.id.tv_top_recycler_like);
            tv_dislike = itemView.findViewById(R.id.tv_top_recycler_dislike);

            btn_top_recycler_pick = itemView.findViewById(R.id.btn_top_recycler_pick);




            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

//    public String getItem(int id) {
//        return mData[id];
//    }

    public void setClickListener(TopRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}