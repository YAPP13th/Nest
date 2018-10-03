package roommate.yapp.com.yapp13th_roommate.Datamodel;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserInfo implements Serializable{

    private String id;          //고유 아이디(카톡 등으로 받은 아이디 값)
    private Date now_date;          //최근 접속한 날짜로 초기화
    private String name;        //이름
    private String gender;      //성별 남자 M / 여자 F
    private int year;           //출생년도 - 년도만 4자리 숫자
    private int monthly;        //예상 월세
    private Boolean room;       //방 유무 - 있음 true 없음 false
    private String location;    //희망 지역
    private int pattern;        //생활 패턴 - 1 아침형 / 2 저녁형 / 3 불규칙
    private int drink;          //음주 빈도 - 1 금주 / 2 매일 / 3 주 1~2회 / 4 월 1~2회
    private int smoking;        //흡연 - 1 흡연 / 2 비흡연
    private int allow_friend;   //친구 출입 - 1 허용 / 2 금지 / 3 합의하 허용
    private int pet;            //애완동물 1 - 허용 / 2 금지 / 3 합의하 허용
    private String like;
    private String disLike;
    private String introduce;
    private String sns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getNow_date() {
        return now_date;
    }

    public void setNow_date(Date now_date) {
        this.now_date = now_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonthly() {
        return monthly;
    }

    public void setMonthly(int monthly) {
        this.monthly = monthly;
    }

    public Boolean getRoom() {
        return room;
    }

    public void setRoom(Boolean room) {
        this.room = room;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public int getDrink() {
        return drink;
    }

    public void setDrink(int drink) {
        this.drink = drink;
    }

    public int getSmoking() {
        return smoking;
    }

    public void setSmoking(int smoking) {
        this.smoking = smoking;
    }

    public int getAllow_friend() {
        return allow_friend;
    }

    public void setAllow_friend(int allow_friend) {
        this.allow_friend = allow_friend;
    }

    public int getPet() {
        return pet;
    }

    public void setPet(int pet) {
        this.pet = pet;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDisLike() {
        return disLike;
    }

    public void setDisLike(String disLike) {
        this.disLike = disLike;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }
}
