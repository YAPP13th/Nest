package roommate.yapp.com.yapp13th_roommate.DetailInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class DetailInfoActivity extends AppCompatActivity {

    private GlobalVariable global;
    private ImageFunc imageFunc;

    private UserInfo userInfo;

    private ImageView ivProfile;
    private TextView tvName, tvBirth, tvLocation, tvInstarID, tvMonthly, tvPattern, tvDrink, tvSmoking, tvAllowFriend, tvPet, tvLike, tvDisLike, tvChatURL, tvIntroduceContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        global = (GlobalVariable)getApplicationContext();
        imageFunc = new ImageFunc(this);

        Intent intent = getIntent();
        userInfo = (UserInfo) intent.getSerializableExtra("userInfo");

        ivProfile = (ImageView)findViewById(R.id.ivUser);

        tvName = (TextView)findViewById(R.id.tvName);
        tvBirth = (TextView)findViewById(R.id.tvBirth);
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        tvInstarID = (TextView)findViewById(R.id.tvInstarID);
        tvMonthly = (TextView)findViewById(R.id.tvMonthly);
        tvPattern = (TextView)findViewById(R.id.tvPattern);
        tvDrink = (TextView)findViewById(R.id.tvDrink);
        tvSmoking = (TextView)findViewById(R.id.tvSmoking);
        tvAllowFriend = (TextView)findViewById(R.id.tvAllowFriend);
        tvPet = (TextView)findViewById(R.id.tvPet);
        tvLike = (TextView)findViewById(R.id.tvLike);
        tvDisLike = (TextView)findViewById(R.id.tvDisLike);
        tvChatURL= (TextView)findViewById(R.id.tvChatURL);
        tvIntroduceContent = (TextView)findViewById(R.id.tvIntroduceContent);

        ivProfile.setImageBitmap(imageFunc.decodebase64ToBitmap(userInfo.getProfile_image()));;
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


}
