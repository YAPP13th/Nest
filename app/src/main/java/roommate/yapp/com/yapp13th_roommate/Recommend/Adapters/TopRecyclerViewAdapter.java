package roommate.yapp.com.yapp13th_roommate.Recommend.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import roommate.yapp.com.yapp13th_roommate.R;

public class TopRecyclerViewAdapter extends RecyclerView.Adapter<TopRecyclerViewAdapter.ViewHolder> {
    private String[] mData;
    private LayoutInflater mInflater;
    private BottomRecyclerViewAdapter.ItemClickListener mClickListener;
    private Context context;
    private LikeButton btn_top_recycler_pick;

    public TopRecyclerViewAdapter(Context context, String[] mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_top_recyclerview, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData[position];
//        holder.myTextView.setText(animal);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View itemView) {
            super(itemView);
            btn_top_recycler_pick = itemView.findViewById(R.id.btn_top_recycler_pick);

            btn_top_recycler_pick.setOnLikeListener(new OnLikeListener() {
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

    public String getItem(int id) {
        return mData[id];
    }

    public void setClickListener(BottomRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}