package roommate.yapp.com.yapp13th_roommate.MyPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.DetailInfo.DetailInfoActivity;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.ModifyMyInfo.ModifyMyInfoActivity;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.ViewPager.RoomImagePagerAdapter;

public class MyPageFragment extends Fragment {

    private GlobalVariable global;
    private ImageFunc imageFunc;

    private ImageView ivUser, pagerIndex1, pagerIndex2, pagerIndex3;
    private TextView tvName, tvBirth, tvLocation, tvInstarID, tvMonthly, tvPattern, tvDrink, tvSmoking, tvAllowFriend, tvPet, tvLike, tvDisLike, tvChatURL, tvIntroduceContent;
    private Button btnModify;

    private ViewPager viewPager;
    private RoomImagePagerAdapter roomImagePagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mypage, null);

        global = (GlobalVariable)getActivity().getApplication();
        imageFunc = new ImageFunc(getActivity());

        ivUser = (ImageView)view.findViewById(R.id.ivUser);
        pagerIndex1 = (ImageView)view.findViewById(R.id.viewPagerIndex1);
        pagerIndex2 = (ImageView)view.findViewById(R.id.viewPagerIndex2);
        pagerIndex3 = (ImageView)view.findViewById(R.id.viewPagerIndex3);

        tvName = (TextView)view.findViewById(R.id.tvName);
        tvBirth = (TextView)view.findViewById(R.id.tvBirth);
        tvLocation = (TextView)view.findViewById(R.id.tvLocation);
        tvInstarID = (TextView)view.findViewById(R.id.tvInstarID);
        tvMonthly = (TextView)view.findViewById(R.id.tvMonthly);
        tvPattern = (TextView)view.findViewById(R.id.tvPattern);
        tvDrink = (TextView)view.findViewById(R.id.tvDrink);
        tvSmoking = (TextView)view.findViewById(R.id.tvSmoking);
        tvAllowFriend = (TextView)view.findViewById(R.id.tvAllowFriend);
        tvPet = (TextView)view.findViewById(R.id.tvPet);
        tvLike = (TextView)view.findViewById(R.id.tvLike);
        tvDisLike = (TextView)view.findViewById(R.id.tvDisLike);
        tvChatURL= (TextView)view.findViewById(R.id.tvChatURL);
        tvIntroduceContent = (TextView)view.findViewById(R.id.tvIntroduceContent);

        btnModify = (Button)view.findViewById(R.id.btnModify);

        ivUser.setImageBitmap(imageFunc.decodebase64ToBitmap(global.myInfo.getProfile_image()));

        tvName.setText(global.myInfo.getName());
        tvBirth.setText(global.myInfo.getYear() + " 년생");
        tvLocation.setText(global.myInfo.getLocation());
        tvInstarID.setText(global.myInfo.getInstarID());
        tvMonthly.setText(global.myInfo.getMonthly() + " 만원");
        tvPattern.setText(global.myInfo.getPattern());
        tvDrink.setText(global.myInfo.getDrink());
        tvSmoking.setText(global.myInfo.getSmoking());
        tvAllowFriend.setText(global.myInfo.getAllow_friend());
        tvPet.setText(global.myInfo.getPet());
        tvLike.setText(global.myInfo.getLike());
        tvDisLike.setText(global.myInfo.getDisLike());
        tvChatURL.setText(global.myInfo.getOpenChatURL());
        tvIntroduceContent.setText(global.myInfo.getIntroduce());

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ModifyMyInfoActivity.class);

                startActivity(intent);
            }
        });

        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        ViewTreeObserver vto = viewPager.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                viewPager.getLayoutParams().height = (int)(viewPager.getWidth() * 0.8);
                viewPager.requestLayout();
            }
        });
        //match_parent를 onCreate에서 이용하면 아직 뷰가 그려지기 전이라서 0으로 호출이 된다
        //뷰가 그려진 이후를 지켜보기 위해 트리옵저버를 이용하여 viewPager를 확인 및 그려지면 가로 : 세로 = 5 : 4 비율을 만들기 위해
        //레이아웃을 다시 그려준다

        if(global.myInfo.getRoom_image() == null || global.myInfo.getRoom_image().isEmpty()){
            int bitmapLength = 1;
            Bitmap[] bitmaps = new Bitmap[bitmapLength];
            bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.myprofileedit_house_photo_icon);
            //추후에 빈 화면일 때 이미지생기면 여기에 처리
            makeRoom(bitmapLength, bitmaps);
        }else{
            int bitmapLength = global.myInfo.getRoom_image().size();
            Bitmap[] bitmaps = new Bitmap[bitmapLength];

            for(int i = 0; i < bitmaps.length; i++){
                bitmaps[i] = imageFunc.decodebase64ToBitmap(global.myInfo.getRoom_image().get("room" + i));
            }
            //뷰 페이저에 들어갈 방 사진 bitmap 만들기
            makeRoom(bitmapLength, bitmaps);
        }

        return view;
    }

    public void makeRoom(int length, Bitmap[] bitmaps){
        final int bitmapLength = length;
        roomImagePagerAdapter = new RoomImagePagerAdapter(getActivity(), bitmaps);
        viewPager.setAdapter(roomImagePagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //페이저 뷰에서 지금 선택 된 인덱스를 표시해주는것 처리
                if(bitmapLength == 3){
                    if(position == 0){
                        pagerIndex1.setImageResource(R.drawable.oval_copy);
                        pagerIndex2.setImageResource(R.drawable.oval);
                        pagerIndex3.setImageResource(R.drawable.oval);
                        //●○○ 형태의 이미지
                    }else if(position == 1){
                        pagerIndex1.setImageResource(R.drawable.oval);
                        pagerIndex2.setImageResource(R.drawable.oval_copy);
                        pagerIndex3.setImageResource(R.drawable.oval);
                        //○●○ 형태의 이미지
                    }else if(position == 2){
                        pagerIndex1.setImageResource(R.drawable.oval);
                        pagerIndex2.setImageResource(R.drawable.oval);
                        pagerIndex3.setImageResource(R.drawable.oval_copy);
                        //○○● 형태의 이미지
                    }
                }else if(bitmapLength == 2){
                    if(position == 0){
                        pagerIndex1.setImageResource(R.color.transparent);
                        pagerIndex2.setImageResource(R.drawable.oval_copy);
                        pagerIndex3.setImageResource(R.drawable.oval);
                        //●○ 형태의 이미지
                    }else if(position == 1){
                        pagerIndex1.setImageResource(R.color.transparent);
                        pagerIndex2.setImageResource(R.drawable.oval);
                        pagerIndex3.setImageResource(R.drawable.oval_copy);
                        //○● 형태의 이미지
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
