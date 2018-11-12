package roommate.yapp.com.yapp13th_roommate.LikeList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.DetailInfo.DetailInfoActivity;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder>{
    private List<UserInfo> mData;
    private LayoutInflater mInflater;
    private Context context;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private GlobalVariable global;
  
    public LikeAdapter(Context context, List<UserInfo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_like_recyclerview, parent, false);
        global = GlobalVariable.getGlobalApplicationContext();
        global.delCheckList=new ArrayList<String>();
        return new LikeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mData != null){

            if(!(mData.get(position).getProfile_image() == null || mData.get(position).getProfile_image().isEmpty())){
                byte[] image = Base64.decode(mData.get(position).getProfile_image(), Base64.DEFAULT);
                Bitmap decodeByte = BitmapFactory.decodeByteArray(image, 0, image.length);

                holder.iv_profile.setImageBitmap(decodeByte);
            }

            holder.tv_name.setText(mData.get(position).getName());
            holder.tv_age.setText(mData.get(position).getYear());
            holder.tv_address.setText(mData.get(position).getLocation());
            holder.tv_monthMoney.setText(mData.get(position).getMonthly());
            holder.tv_selfIntroduction.setText(mData.get(position).getIntroduce());
            holder.tv_like.setText(mData.get(position).getLike());
            holder.tv_dislike.setText(mData.get(position).getDisLike());
            holder.cb_like.setChecked(false);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), DetailInfoActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("like", position);
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });
            holder.cb_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(b) //체크하면 id가 스트링으로 리스트에 저장
                        global.delCheckList.add(mData.get(position).getId());
                    else //체크 풀면 id있는지 찾고 있으면 없앰
                        if(global.delCheckList.contains(mData.get(position).getId()))
                            global.delCheckList.remove(mData.get(position).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mData !=null) {
            return mData.size();
        }else {
            return 0;
        }

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        LikeButton btn_bottom_recycler_pick;
        ImageView iv_profile;
        TextView tv_name, tv_age, tv_address, tv_monthMoney, tv_selfIntroduction, tv_like, tv_dislike;
        CheckBox cb_like;


        public ViewHolder(View view) {
            super(view);
            iv_profile = itemView.findViewById(R.id.iv_profile1);
            tv_name = itemView.findViewById(R.id.tv_name1);
            tv_age = itemView.findViewById(R.id.tv_top_recycler_birth_year1);
            tv_address = itemView.findViewById(R.id.tv_address1);
            tv_monthMoney = itemView.findViewById(R.id.tv_top_recycler_monthly_money1);
            tv_selfIntroduction = itemView.findViewById(R.id.tv_selfIntroduction1);
            tv_like = itemView.findViewById(R.id.tv_top_recycler_like1);
            tv_dislike = itemView.findViewById(R.id.tv_top_recycler_dislike1);
            cb_like = itemView.findViewById(R.id.cb_like);
        }

    }




}
