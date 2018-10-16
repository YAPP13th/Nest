package roommate.yapp.com.yapp13th_roommate.MyPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.ModifyMyInfo.ModifyMyInfoActivity;
import roommate.yapp.com.yapp13th_roommate.R;

public class MyPageFragment extends Fragment {

    private GlobalVariable global;
    private ImageFunc imageFunc;

    private ImageView ivRoom, ivUser;
    private ProgressBar pbLogin;
    private TextView tvName, tvBirth, tvLocation, tvInstarID, tvMonthly, tvPattern, tvDrink, tvSmoking, tvAllowFriend, tvPet, tvLike, tvDisLike, tvChatURL, tvIntroduceContent;
    private Button btnModify;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mypage, null);

        global = (GlobalVariable)getActivity().getApplication();
        imageFunc = new ImageFunc(getActivity());

        ivRoom = (ImageView)view.findViewById(R.id.ivRoom);
        ivUser = (ImageView)view.findViewById(R.id.ivUser);

        pbLogin = (ProgressBar)view.findViewById(R.id.pbLogin);

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

        return view;
    }
}
