package roommate.yapp.com.yapp13th_roommate.DataModel;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserInfo implements Serializable{

    private String key;             //DB에 저장되는 키값
    private String profile_image;   //프로필 사진 이미지 base64
    private String[] room_image;    //방 사진 이미지 base64 최대 3개
    private String id;              //고유 아이디(카톡 등으로 받은 아이디 값)
    private Date now_date;          //최근 접속한 날짜로 초기화
    private String name;            //이름
    private String gender;          //성별 남자 M / 여자 F
    private String year;            //출생년도 - 년도만 4자리 숫자
    private String monthly;         //예상 월세
    private String openChatURL;     //오픈채팅 URL
    private Boolean room;           //방 유무 - 있음 true 없음 false
    private String location;        //희망 지역
    private String instarID;        //인스타 아이디
    private String pattern;         //생활 패턴 - 아침형 / 저녁형 / 불규칙
    private String drink;           //음주 빈도 - 금주 / 매일 / 주 1~2회 / 월 1~2회
    private String smoking;         //흡연 - 흡연 / 비흡연
    private String allow_friend;    //친구 출입 - 허용 / 금지 / 합의하 허용
    private String pet;             //애완동물 허용 / 금지 / 합의하 허용
    private String like;            //좋아하는 것
    private String disLike;         //싫어하는 것
    private String introduce;       //자기소개

    public UserInfo() {}

    public UserInfo(String key, String profile_image, String[] room_image, String id, Date now_date, String name, String gender, String year, String monthly, String openChatURL, Boolean room, String location, String instarID, String pattern, String drink, String smoking, String allow_friend, String pet, String like, String disLike, String introduce) {
        this.key = key;
        this.profile_image = profile_image;
        this.room_image = room_image;
        this.id = id;
        this.now_date = now_date;
        this.name = name;
        this.gender = gender;
        this.year = year;
        this.monthly = monthly;
        this.openChatURL = openChatURL;
        this.room = room;
        this.location = location;
        this.instarID = instarID;
        this.pattern = pattern;
        this.drink = drink;
        this.smoking = smoking;
        this.allow_friend = allow_friend;
        this.pet = pet;
        this.like = like;
        this.disLike = disLike;
        this.introduce = introduce;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String[] getRoom_image() {
        return room_image;
    }

    public void setRoom_image(String[] room_image) {
        this.room_image = room_image;
    }

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

    public String getOpenChatURL() {
        return openChatURL;
    }

    public void setOpenChatURL(String openChatURL) {
        this.openChatURL = openChatURL;
    }

    public String getInstarID() {
        return instarID;
    }

    public void setInstarID(String instarID) {
        this.instarID = instarID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getAllow_friend() {
        return allow_friend;
    }

    public void setAllow_friend(String allow_friend) {
        this.allow_friend = allow_friend;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }
}
