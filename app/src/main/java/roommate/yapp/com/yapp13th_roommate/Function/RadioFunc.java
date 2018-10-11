package roommate.yapp.com.yapp13th_roommate.Function;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
                global.myInfo.setGender("F");
            }else if(rb == rbGender[1]){
                global.myInfo.setGender("M");
            }

        }else{
            radioClear(rb);
        }
    }

    public void roomCheck(RadioButton rb, RadioButton[] rbRoom){
        radioSelect(rb);
        if(rb.isChecked()){
            if(rb == rbRoom[0]){
                global.myInfo.setRoom(true);
            }else if(rb == rbRoom[1]){
                global.myInfo.setRoom(false);
            }

        }else{
            radioClear(rb);
        }
    }

    public Boolean[] patternCheck(RadioGroup rgPattern, RadioButton rb, RadioButton[] rbPattern, Boolean[] clickCheck){
        if(rb.isChecked()){
            if(rb == rbPattern[0]){
                if(clickCheck[0]){
                    global.myInfo.setPattern("");
                    clickCheck[0] = false;

                    rgPattern.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setPattern("아침형");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;

                    rgPattern.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPattern[1]){
                if(clickCheck[1]){
                    global.myInfo.setPattern("");
                    clickCheck[1] = false;

                    rgPattern.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setPattern("저녁형");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;

                    rgPattern.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPattern[2]){
                if(clickCheck[2]){
                    global.myInfo.setPattern("");
                    clickCheck[2] = false;

                    rgPattern.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setPattern("불규칙");
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
                    global.myInfo.setDrink("");
                    clickCheck[0] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setDrink("금주");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbDrink[1]){
                if(clickCheck[1]){
                    global.myInfo.setDrink("");
                    clickCheck[1] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setDrink("매일");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbDrink[2]){
                if(clickCheck[2]){
                    global.myInfo.setDrink("0");
                    clickCheck[2] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setDrink("주 1~2회");
                    clickCheck[0] = false;
                    clickCheck[1] = false;
                    clickCheck[2] = true;
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbDrink[3]){
                if(clickCheck[3]){
                    global.myInfo.setDrink("");
                    clickCheck[3] = false;

                    rgDrink.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setDrink("월 1~2회");
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
                    global.myInfo.setSmoking("");
                    clickCheck[0] = false;

                    rgSmoking.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setSmoking("흡연");
                    clickCheck[0] = true;
                    clickCheck[0] = false;

                    rgSmoking.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbSmoking[1]){
                if(clickCheck[1]){
                    global.myInfo.setSmoking("");
                    clickCheck[1] = false;

                    rgSmoking.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setSmoking("비흡연");
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
                    global.myInfo.setAllow_friend("");
                    clickCheck[0] = false;

                    rgAllowFriend.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setAllow_friend("허용");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;

                    rgAllowFriend.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbAllowFriend[1]){
                if(clickCheck[1]){
                    global.myInfo.setAllow_friend("");
                    clickCheck[1] = false;

                    rgAllowFriend.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setAllow_friend("금지");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;

                    rgAllowFriend.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbAllowFriend[2]){
                if(clickCheck[2]){
                    global.myInfo.setAllow_friend("");
                    clickCheck[2] = false;

                    rgAllowFriend.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setAllow_friend("합의하 허용");
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
                    global.myInfo.setPet("");
                    clickCheck[0] = false;

                    rgPet.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setPet("허용");
                    clickCheck[0] = true;
                    clickCheck[1] = false;
                    clickCheck[2] = false;

                    rgPet.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPet[1]){
                if(clickCheck[1]){
                    global.myInfo.setPet("");
                    clickCheck[1] = false;

                    rgPet.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setPet("금지");
                    clickCheck[0] = false;
                    clickCheck[1] = true;
                    clickCheck[2] = false;

                    rgPet.clearCheck();
                    radioSelect(rb);
                }
            }else if(rb == rbPet[2]){
                if(clickCheck[2]){
                    global.myInfo.setPet("");
                    clickCheck[2] = false;

                    rgPet.clearCheck();
                    radioClear(rb);
                }else{
                    global.myInfo.setPet("합의하 허용");
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
