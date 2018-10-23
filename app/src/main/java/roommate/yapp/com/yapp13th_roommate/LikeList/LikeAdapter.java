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
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;

import java.util.List;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.DetailInfo.DetailInfoActivity;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.Recommend.Adapters.BottomRecyclerViewAdapter;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder>{
    private List<UserInfo> mData;
    private LayoutInflater mInflater;

    private Context context;
    public LikeAdapter(Context context, List<UserInfo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_like_recyclerview, parent, false);
        return new LikeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mData != null){
            Log.e("global 3 :->" , String.valueOf(mData));
            byte[] image = Base64.decode(mData.get(position).getProfile_image(), Base64.DEFAULT);
            Bitmap decodeByte = BitmapFactory.decodeByteArray(image, 0, image.length);


            holder.iv_profile.setImageBitmap(decodeByte);
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
                    bundle.putInt("position1", position);
                    intent.putExtras(bundle);

                    context.startActivity(intent);
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
//        return mData.size();




    }
    class ViewHolder extends RecyclerView.ViewHolder {
        LikeButton btn_bottom_recycler_pick;
        ImageView iv_profile;
        TextView tv_name, tv_age, tv_address, tv_monthMoney, tv_selfIntroduction, tv_like, tv_dislike;


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
            btn_bottom_recycler_pick = itemView.findViewById(R.id.btn_bottom_recycler_pick1);


        }

    }




}
