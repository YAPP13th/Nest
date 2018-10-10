//package roommate.yapp.com.yapp13th_roommate.main;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.like.LikeButton;
//import com.like.OnLikeListener;
//
//import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
//import roommate.yapp.com.yapp13th_roommate.DetailInfo.DetailInfoActivity;
//import roommate.yapp.com.yapp13th_roommate.R;
//import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpSecondActivity;
//
//public class BottomRecyclerViewAdapter extends RecyclerView.Adapter<BottomRecyclerViewAdapter.ViewHolder> {
////    private UserInfo[] mData;
//    private UserInfo[] mData;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//    private Context context;
//
//    public BottomRecyclerViewAdapter(Context context, UserInfo[] data) {
//        this.mInflater = LayoutInflater.from(context);
//        this.mData = data;
//        this.context = context;
//    }
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.item_bottom_recyclerview, parent, false);
//        return new ViewHolder(view);
//    }
//
//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public void onBindViewHolder(BottomRecyclerViewAdapter.ViewHolder holder, final int position) {
//
//        holder.tv_name.setText(mData[position].getName());
//        holder.tv_age.setText(mData[position].getYear());
//        holder.tv_address.setText(mData[position].getLocation());
//        holder.tv_monthMoney.setText(mData[position].getMonthly());
//        holder.tv_selfIntroduction.setText(mData[position].getIntroduce());
//        holder.tv_like.setText(mData[position].getLike());
//        holder.tv_dislike.setText(mData[position].getDisLike());
//
////        final UserInfo test = new UserInfo();
////        test = mData[position];
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(v.getContext(), DetailInfoActivity.class);
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userInfo", mData[position]);
//                intent.putExtras(bundle);
//
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.length;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        LikeButton btn_bottom_recycler_pick;
//        TextView tv_name, tv_age, tv_address, tv_monthMoney, tv_selfIntroduction, tv_like, tv_dislike;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_age = itemView.findViewById(R.id.tv_age);
//            tv_address = itemView.findViewById(R.id.tv_address);
//            tv_monthMoney = itemView.findViewById(R.id.tv_monthMoney);
//            tv_selfIntroduction = itemView.findViewById(R.id.tv_selfIntroduction);
//            tv_like = itemView.findViewById(R.id.tv_like);
//            tv_dislike = itemView.findViewById(R.id.tv_disike);
//
//            btn_bottom_recycler_pick = itemView.findViewById(R.id.btn_bottom_recycler_pick);
//
//            btn_bottom_recycler_pick.setOnLikeListener(new OnLikeListener() {
//                @Override
//                public void liked(LikeButton likeButton) {
//                    Toast.makeText(context, "찜!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void unLiked(LikeButton likeButton) {
//                    Toast.makeText(context, "찜@", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
//    public UserInfo[] getmData() {
//        return mData;
//    }
//
//    public void setmData(UserInfo[] mData) {
//        this.mData = mData;
//    }
//
//    public void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//}
