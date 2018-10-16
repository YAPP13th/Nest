package roommate.yapp.com.yapp13th_roommate.Function;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import roommate.yapp.com.yapp13th_roommate.DataModel.UserInfo;
import roommate.yapp.com.yapp13th_roommate.Global.GlobalVariable;
import roommate.yapp.com.yapp13th_roommate.R;

public class RadioFunc {

    private Context mContext;
    private GlobalVariable global;
    private GradientDrawable drawable,drawable2;

    public RadioFunc(Context context){
        super();
        this.mContext = context;
        global = (GlobalVariable)mContext.getApplicationContext();

        drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.rounding);
        drawable2 = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.rounding2);
    }

    public void genderCheck(RadioButton rb, RadioButton[] rbGender){
        radioSelect(rb);
        if(rb.isChecked()){
            if(rb == rbGender[0]){
                global.temp.setGender("F");
            }else if(rb == rbGender[1]){
                global.temp.setGender("M");
            }

        }else{
            radioClear(rb);
        }
    }

    public void roomCheck(RadioButton rb, RadioButton[] rbRoom){
        radioSelect(rb);
        if(rb.isChecked()){
            if(rb == rbRoom[0]){
                global.temp.setRoom(true);
            }else if(rb == rbRoom[1]){
                global.temp.setRoom(false);
            }

        }else{
            radioClear(rb);
        }
    }

    public Boolean[] patternCheck(RadioGroup rgPattern, RadioButton rb, RadioButton[] rbPattern, Boolean[] clickCheck){
        if(rb.isChecked()){
            if(rb == rbPattern[0]){
                if(clickCheck[0]){
                    global.temp.setPattern("");
                    clickCheck[0] = false;

                    rgPattern.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setPattern("아침형");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;

                    rgPattern.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPattern[1]){
                if(clickCheck[1]){
                    global.temp.setPattern("");
                    clickCheck[1] = false;

                    rgPattern.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setPattern("저녁형");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;

                    rgPattern.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPattern[2]){
                if(clickCheck[2]){
                    global.temp.setPattern("");
                    clickCheck[2] = false;

                    rgPattern.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setPattern("불규칙");
                    clickCheck[0] = false;
                    clickCheck[1] = false;
                    clickCheck[2] = true;

                    rgPattern.clearCheck();
                    radioSelect(rb);
                }
            }
        }else{
            radioClear(rb);
        }

        return clickCheck;
    }

    public Boolean[] drinkCheck(RadioGroup rgDrink,RadioButton rb, RadioButton[] rbDrink, Boolean[] clickCheck){
        if(rb.isChecked()){
            if(rb == rbDrink[0]){
                if(clickCheck[0]){
                    global.temp.setDrink("");
                    clickCheck[0] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setDrink("금주");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbDrink[1]){
                if(clickCheck[1]){
                    global.temp.setDrink("");
                    clickCheck[1] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setDrink("매일");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbDrink[2]){
                if(clickCheck[2]){
                    global.temp.setDrink("0");
                    clickCheck[2] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setDrink("주 1-2회");
                    clickCheck[0] = false;
                    clickCheck[1] = false;
                    clickCheck[2] = true;
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbDrink[3]){
                if(clickCheck[3]){
                    global.temp.setDrink("");
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setDrink("월 1-2회");
                    clickCheck[0] = false;
                    clickCheck[1] = false;
                    clickCheck[2] = false;
                    clickCheck[3] = true;

                    rgDrink.clearCheck();
                    radioSelect(rb);
                }
            }
        }else{
            radioClear(rb);
        }

        return clickCheck;
    }

    public Boolean[] smokingCheck(RadioGroup rgSmoking, RadioButton rb, RadioButton[] rbSmoking, Boolean[] clickCheck){
        if(rb.isChecked()){
            if(rb == rbSmoking[0]){
                if(clickCheck[0]){
                    global.temp.setSmoking("");
                    clickCheck[0] = false;

                    rgSmoking.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setSmoking("흡연");
                    clickCheck[0] = true;
                    clickCheck[1] = false;

                    rgSmoking.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbSmoking[1]){
                if(clickCheck[1]){
                    global.temp.setSmoking("");
                    clickCheck[1] = false;

                    rgSmoking.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setSmoking("비흡연");
                    clickCheck[0] = false;
                    clickCheck[1] = true;

                    rgSmoking.clearCheck();
                    radioSelect(rb);
                }
            }
        }else{
            radioClear(rb);
        }

        return clickCheck;
    }

    public Boolean[] allowFriendCheck(RadioGroup rgAllowFriend,RadioButton rb, RadioButton[] rbAllowFriend, Boolean[] clickCheck){

        if(rb.isChecked()){
            if(rb == rbAllowFriend[0]){
                if(clickCheck[0]){
                    global.temp.setAllow_friend("");
                    clickCheck[0] = false;

                    rgAllowFriend.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setAllow_friend("허용");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;

                    rgAllowFriend.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbAllowFriend[1]){
                if(clickCheck[1]){
                    global.temp.setAllow_friend("");
                    clickCheck[1] = false;

                    rgAllowFriend.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setAllow_friend("금지");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;

                    rgAllowFriend.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbAllowFriend[2]){
                if(clickCheck[2]){
                    global.temp.setAllow_friend("");
                    clickCheck[2] = false;

                    rgAllowFriend.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setAllow_friend("합의하 허용");
                    clickCheck[0] = false;
                    clickCheck[1] = false;
                    clickCheck[2] = true;

                    rgAllowFriend.clearCheck();
                    radioSelect(rb);
                }
            }
        }else{
            radioClear(rb);
        }

        return clickCheck;
    }

    public Boolean[] petCheck(RadioGroup rgPet, RadioButton rb, RadioButton[] rbPet, Boolean[] clickCheck){
        if(rb.isChecked()){
            if(rb == rbPet[0]){
                if(clickCheck[0]){
                    global.temp.setPet("");
                    clickCheck[0] = false;

                    rgPet.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setPet("허용");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;

                    rgPet.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPet[1]){
                if(clickCheck[1]){
                    global.temp.setPet("");
                    clickCheck[1] = false;

                    rgPet.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setPet("금지");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;

                    rgPet.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPet[2]){
                if(clickCheck[2]){
                    global.temp.setPet("");
                    clickCheck[2] = false;

                    rgPet.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setPet("합의하 허용");
                    clickCheck[0] = false;
                    clickCheck[1] = false;
                    clickCheck[2] = true;

                    rgPet.clearCheck();
                    radioSelect(rb);
                }
            }
        }else{
            radioClear(rb);
        }

        return clickCheck;
    }

    public Boolean[] roomCheck(RadioGroup rgRoom, RadioButton rb, RadioButton[] rbRoom, Boolean[] clickCheck){
        if(rb.isChecked()){
            if(rb == rbRoom[0]){
                if(clickCheck[0]){
                    global.temp.setRoom(null);
                    clickCheck[0] = false;

                    rgRoom.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setRoom(true);
                    clickCheck[0] = true;
                    clickCheck[1] = false;

                    rgRoom.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbRoom[1]){
                if(clickCheck[1]){
                    global.temp.setRoom(null);
                    clickCheck[1] = false;

                    rgRoom.clearCheck();
                    radioClear(rb);
                }else{
                    global.temp.setRoom(false);
                    clickCheck[0] = false;
                    clickCheck[1] = true;

                    rgRoom.clearCheck();
                    radioSelect(rb);
                }
            }
        }else{
            radioClear(rb);
        }

        return clickCheck;
    }

    public void modifyGenderInit(RadioButton[] rbGender){
        if(global.myInfo.getGender().equals("F")){
            global.temp.setGender("F");
            radioSelect(rbGender[0]);
            rbGender[0].setChecked(true);
        }else{
            global.temp.setGender("M");
            radioSelect(rbGender[1]);
            rbGender[1].setChecked(true);
        }
    }

    public void modifyRoomInit(RadioButton[] rbRoom){
        if(global.myInfo.getRoom()){
            global.temp.setRoom(true);
            radioSelect(rbRoom[0]);
            rbRoom[0].setChecked(true);
        }else{
            global.temp.setRoom(false);
            radioSelect(rbRoom[1]);
            rbRoom[1].setChecked(true);
        }
    }

    public void modifyPatternInit(RadioButton[] rbPattern){
        if(TextUtils.isEmpty(global.myInfo.getPattern())){
            global.temp.setPattern("");
        }else if(global.myInfo.getPattern().equals("아침형")){
            global.temp.setPattern("아침형");
            radioSelect(rbPattern[0]);
            rbPattern[0].setChecked(true);
        }else if(global.myInfo.getPattern().equals("저녁형")){
            global.temp.setPattern("저녁형");
            radioSelect(rbPattern[1]);
            rbPattern[1].setChecked(true);
        }else if(global.myInfo.getPattern().equals("불규칙")){
            global.temp.setPattern("불규칙");
            radioSelect(rbPattern[2]);
            rbPattern[2].setChecked(true);
        }
    }

    public void modifyDrinkInit(RadioButton[] rbDrink){
        if(TextUtils.isEmpty(global.myInfo.getDrink())){
            global.temp.setDrink("");
        }else if(global.myInfo.getDrink().equals("금주")){
            global.temp.setDrink("금주");
            radioSelect(rbDrink[0]);
            rbDrink[0].setChecked(true);
        }else if(global.myInfo.getDrink().equals("매일")){
            global.temp.setDrink("매일");
            radioSelect(rbDrink[1]);
            rbDrink[1].setChecked(true);
        }else if(global.myInfo.getDrink().equals("주 1-2회")){
            global.temp.setDrink("주 1-2회");
            radioSelect(rbDrink[2]);
            rbDrink[2].setChecked(true);
        }else if(global.myInfo.getDrink().equals("월 1-2회")){
            global.temp.setDrink("월 1-2회");
            radioSelect(rbDrink[3]);
            rbDrink[3].setChecked(true);
        }
    }

    public void modifySmokingInit(RadioButton[] rbSmoking){
        if(TextUtils.isEmpty(global.myInfo.getSmoking())){
            global.temp.setSmoking("");
        }else if(global.myInfo.getSmoking().equals("흡연")){
            global.temp.setSmoking("흡연");
            radioSelect(rbSmoking[0]);
            rbSmoking[0].setChecked(true);
        }else if(global.myInfo.getSmoking().equals("비흡연")){
            global.temp.setSmoking("비흡연");
            radioSelect(rbSmoking[1]);
            rbSmoking[1].setChecked(true);
        }
    }

    public void modifyAllowFriendInit(RadioButton[] rbAllowFriend){
        if(TextUtils.isEmpty(global.myInfo.getAllow_friend())){
            global.temp.setAllow_friend("");
        }else if(global.myInfo.getAllow_friend().equals("허용")){
            global.temp.setAllow_friend("허용");
            radioSelect(rbAllowFriend[0]);
            rbAllowFriend[0].setChecked(true);
        }else if(global.myInfo.getAllow_friend().equals("금지")){
            global.temp.setAllow_friend("금지");
            radioSelect(rbAllowFriend[1]);
            rbAllowFriend[1].setChecked(true);
        }else if(global.myInfo.getAllow_friend().equals("합의하 허용")){
            global.temp.setAllow_friend("합의하 허용");
            radioSelect(rbAllowFriend[2]);
            rbAllowFriend[2].setChecked(true);
        }
    }

    public void modifyPetInit(RadioButton[] rbPet){
        if(TextUtils.isEmpty(global.myInfo.getPet())){
            global.temp.setPet("");
        }else if(global.myInfo.getPet().equals("허용")){
            global.temp.setPet("허용");
            radioSelect(rbPet[0]);
            rbPet[0].setChecked(true);
        }else if(global.myInfo.getPet().equals("금지")){
            global.temp.setPet("금지");
            radioSelect(rbPet[1]);
            rbPet[1].setChecked(true);
        }else if(global.myInfo.getPet().equals("합의하 허용")){
            global.temp.setPet("합의하 허용");
            radioSelect(rbPet[2]);
            rbPet[2].setChecked(true);
        }
    }

    public void radioSelect(RadioButton rb){
        rb.setBackground(drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rb.setClipToOutline(true);
        }
    }

    public void radioClear(RadioButton rb){
        rb.setBackground(drawable2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rb.setClipToOutline(true);
        }
    }

}
