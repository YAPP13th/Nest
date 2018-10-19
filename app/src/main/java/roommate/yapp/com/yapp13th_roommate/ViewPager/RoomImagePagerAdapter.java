package roommate.yapp.com.yapp13th_roommate.ViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class RoomImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private ImageView iv;
    private LayoutInflater inflater;
    private GlobalVariable global;
    private ImageFunc imageFunc;

    private Bitmap[] bitmaps;

    public RoomImagePagerAdapter(Context mContext, Bitmap[] bitmaps) {
        this.mContext = mContext;
        this.bitmaps = new Bitmap[bitmaps.length];
        this.bitmaps = bitmaps;

        global = (GlobalVariable)mContext.getApplicationContext();
        imageFunc = new ImageFunc(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.room_slider, container, false);

        iv = (ImageView)v.findViewById(R.id.ivRoom);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageBitmap(bitmaps[position]);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.setViewPagerPosition(position);
                imageFunc.selectRoomGallery();
            }
        });

        container.addView(v);
        return v;
    }

    @Override
    public int getCount() {
        return bitmaps.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ConstraintLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
