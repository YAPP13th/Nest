package roommate.yapp.com.yapp13th_roommate.ViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class ViewPagerMain extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tabbar_home_click));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tabbar_like));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tabbar_myprofile));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0){
                    tabLayout.getTabAt(0).setIcon(R.drawable.tabbar_home_click);
                    tabLayout.getTabAt(1).setIcon(R.drawable.tabbar_like);
//                    tabLayout.getTabAt(2).setIcon(R.drawable.tabbar_myprofile);
                }else if(tab.getPosition() == 1){
                    tabLayout.getTabAt(0).setIcon(R.drawable.tabbar_home);
                    tabLayout.getTabAt(1).setIcon(R.drawable.tabbar_like_click);
//                    tabLayout.getTabAt(2).setIcon(R.drawable.tabbar_myprofile);
                }else if(tab.getPosition() == 2){
                    tabLayout.getTabAt(0).setIcon(R.drawable.tabbar_home);
                    tabLayout.getTabAt(1).setIcon(R.drawable.tabbar_like);
//                    tabLayout.getTabAt(2).setIcon(R.drawable.tabbar_myprofile_click);
                }
            }

            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}