package roommate.yapp.com.yapp13th_roommate.DetailInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import roommate.yapp.com.yapp13th_roommate.Function.ImageFunc;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class DetailInfoActivity extends AppCompatActivity {

    private GlobalVariable global;
    private ImageFunc imageFunc;

    private int position;

    private ImageView ivProfile;
    private TextView tvName, tvBirth, tvLocation, tvInstarID, tvMonthly, tvPattern, tvDrink, tvSmoking, tvAllowFriend, tvPet, tvLike, tvDisLike, tvChatURL, tvIntroduceContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        global = (GlobalVariable)getApplicationContext();
        imageFunc = new ImageFunc(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

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

        ivProfile.setImageBitmap(imageFunc.decodebase64ToBitmap(global.filterInfo.get(position).getProfile_image()));;
        tvName.setText(global.filterInfo.get(position).getName());
        tvBirth.setText(global.filterInfo.get(position).getYear() + " 년생");
        tvLocation.setText(global.filterInfo.get(position).getLocation());
        tvInstarID.setText(global.filterInfo.get(position).getInstarID());
        tvMonthly.setText(global.filterInfo.get(position).getMonthly() + " 만원");
        tvPattern.setText(global.filterInfo.get(position).getPattern());
        tvDrink.setText(global.filterInfo.get(position).getDrink());
        tvSmoking.setText(global.filterInfo.get(position).getSmoking());
        tvAllowFriend.setText(global.filterInfo.get(position).getAllow_friend());
        tvPet.setText(global.filterInfo.get(position).getPet());
        tvLike.setText(global.filterInfo.get(position).getLike());
        tvDisLike.setText(global.filterInfo.get(position).getDisLike());
        tvChatURL.setText(global.filterInfo.get(position).getOpenChatURL());
        tvIntroduceContent.setText(global.filterInfo.get(position).getIntroduce());

    }


}
