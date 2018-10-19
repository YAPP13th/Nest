package roommate.yapp.com.yapp13th_roommate.Recommend.ItemDecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BottomSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public BottomSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = 0;
        }
    }

}