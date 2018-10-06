package roommate.yapp.com.yapp13th_roommate.MyPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.ModifyMyInfo.ModifyMyInfoActivity;
import roommate.yapp.com.yapp13th_roommate.R;
import roommate.yapp.com.yapp13th_roommate.SignUp.SignUpSecondActivity;

public class MyPageFragment extends Fragment {

    private ImageView ivRoom, ivUser;
    private ProgressBar pbLogin;
    private TextView tvName, tvBirth, tvLocation, tvInstarID, tvMonthly, tvPattern, tvDrink, tvSmoking, tvAllowFriend, tvPet, tvLike, tvDisLike, tvChatURL, tvIntroduceContent;
    private Button btnModify;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mypage, null);

        userInfo = new UserInfo();

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

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("user_info_test");

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Log.d("MainActivity", "Single ValueEventListener : " + snapshot.getValue());

                    userInfo = snapshot.getValue(UserInfo.class);
                    if(userInfo.getId().equals("933048308"))
                        break;
                }

                Log.i("test", userInfo.getId());
                byte[] image = Base64.decode(userInfo.getProfile_image(), Base64.DEFAULT);
                Bitmap decodeByte = BitmapFactory.decodeByteArray(image, 0, image.length);
                ivUser.setImageBitmap(decodeByte);

                tvName.setText(userInfo.getName());
                tvBirth.setText(userInfo.getYear() + " 년생");
                tvLocation.setText(userInfo.getLocation());
                tvInstarID.setText(userInfo.getInstarID());
                tvMonthly.setText(userInfo.getMonthly() + " 만원");
                tvPattern.setText(userInfo.getPattern());
                tvDrink.setText(userInfo.getDrink());
                tvSmoking.setText(userInfo.getSmoking());
                tvAllowFriend.setText(userInfo.getAllow_friend());
                tvPet.setText(userInfo.getPet());
                tvLike.setText(userInfo.getLike());
                tvDisLike.setText(userInfo.getDisLike());
                tvChatURL.setText(userInfo.getOpenChatURL());
                tvIntroduceContent.setText(userInfo.getIntroduce());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ModifyMyInfoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", userInfo);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        return view;
    }
}
