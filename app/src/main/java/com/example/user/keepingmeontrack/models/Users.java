package com.example.user.keepingmeontrack.models;

/**
 * Created by User on 09.02.2018.
 */

public class Users {
    private String userName;
    private String userEmail;
    private String uid;
    private String gender;
    private String age;
    private String weight;
    private String height;

    public Users(String userName, String userEmail, String uid, String gender, String age, String weight, String height) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.uid = uid;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public Users() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}