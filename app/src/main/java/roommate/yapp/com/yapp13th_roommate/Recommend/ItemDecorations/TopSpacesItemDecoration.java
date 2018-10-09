package roommate.yapp.com.yapp13th_roommate.Recommend.ItemDecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class TopSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public TopSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 0;
        }
    }
}
