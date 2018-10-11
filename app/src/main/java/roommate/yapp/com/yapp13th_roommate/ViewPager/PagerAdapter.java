package roommate.yapp.com.yapp13th_roommate.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import roommate.yapp.com.yapp13th_roommate.LikeList.LikeFragment;

import roommate.yapp.com.yapp13th_roommate.MyPage.MyPageFragment;
import roommate.yapp.com.yapp13th_roommate.Recommend.RecommendFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecommendFragment tab1 = new RecommendFragment();
                return tab1;
            case 1:
                LikeFragment tab2 = new LikeFragment();
                return tab2;
            case 2:
                MyPageFragment tab3 = new MyPageFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
