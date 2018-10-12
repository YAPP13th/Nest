package roommate.yapp.com.yapp13th_roommate.main;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.DetailInfo.DetailInfoActivity;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpSecondActivity;

public class BottomRecyclerViewAdapter extends RecyclerView.Adapter<BottomRecyclerViewAdapter.ViewHolder> {
//    private UserInfo[] mData;
    private List<UserInfo> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public BottomRecyclerViewAdapter(Context context, List<UserInfo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bottom_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BottomRecyclerViewAdapter.ViewHolder holder, final int position) {
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
                bundle.putSerializable("userInfo", mData.get(position));
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LikeButton btn_bottom_recycler_pick;

        ImageView iv_profile;
        TextView tv_name, tv_age, tv_address, tv_monthMoney, tv_selfIntroduction, tv_like, tv_dislike;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_monthMoney = itemView.findViewById(R.id.tv_monthMoney);
            tv_selfIntroduction = itemView.findViewById(R.id.tv_selfIntroduction);
            tv_like = itemView.findViewById(R.id.tv_like);
            tv_dislike = itemView.findViewById(R.id.tv_disike);

            btn_bottom_recycler_pick = itemView.findViewById(R.id.btn_bottom_recycler_pick);

            btn_bottom_recycler_pick.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    Toast.makeText(context, "찜!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    Toast.makeText(context, "찜@", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public List<UserInfo> getmData() {
        return mData;
    }

    public void setmData(List<UserInfo> mData) {
        this.mData = mData;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
