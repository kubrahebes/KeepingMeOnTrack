package com.example.user.keepingmeontrack.models;

/**
 * Created by User on 09.02.2018.
 */

public class Users {
    private String userName;
    private String userPassWord;
    private String userEmail;

    public Users(String userName, String userPassWord, String userEmail) {
        this.userName = userName;
        this.userPassWord = userPassWord;
        this.userEmail = userEmail;
    }

    public Users() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
