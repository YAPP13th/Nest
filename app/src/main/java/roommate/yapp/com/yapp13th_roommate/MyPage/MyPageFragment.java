package roommate.yapp.com.yapp13th_roommate.MyPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import roommate.yapp.com.yapp13th_roommate.R;

public class MyPageFragment extends Fragment {

    private ImageView ivRoom, ivUser;
    private ProgressBar pbLogin;
    private TextView tvName, tvBirth, tvLocation, tvInstarID, tvMonthly, tvPattern, tvDrink, tvSmoking, tvAllowFriend, tvPet, tvLike, tvDisLike, tvChatURL, tvIntroduceContent;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mypage, null);

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

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("user_info_test");

        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        
        mReference.addChildEventListener(mChild);

        return view;
    }
}
