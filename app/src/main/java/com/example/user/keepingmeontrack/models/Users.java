package com.example.user.keepingmeontrack.models;

/**
 * Created by User on 09.02.2018.
 */

public class Users {
    private String userName;
    private String userEmail;
    private String uid;

    public Users(String userName, String userEmail, String uid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.uid = uid;
    }

    public Users() {
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