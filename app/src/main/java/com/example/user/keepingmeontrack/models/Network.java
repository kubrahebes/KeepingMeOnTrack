package com.example.user.keepingmeontrack.models;

/**
 * Created by Mahmoud on 2/23/2018.
 */

public class Network {
   private String goalName;
   private String goalDesc;
   private String userName;
   private int like;
    private int dislike;
    private String id;
    private String userId;  //This is to get the user ID for networking part.

    public Network(String goalName, String goalDesc, String userName, int like, int dislike, String id,  String userId) {
        this.goalName = goalName;
        this.goalDesc = goalDesc;
        this.userName = userName;
        this.like = like;
        this.dislike = dislike;
        this.id = id;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Network(String goalName, String goalDesc, String userName, int like, int dislike) {
        this.goalName = goalName;
        this.goalDesc = goalDesc;
        this.userName = userName;
        this.like = like;
        this.dislike = dislike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public Network() {
    }

    public Network(String goalName, String goalDesc, String userName) {
        this.goalName = goalName;
        this.goalDesc = goalDesc;
        this.userName = userName;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalDesc() {
        return goalDesc;
    }

    public void setGoalDesc(String goalDesc) {
        this.goalDesc = goalDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String goalDesc) {
        this.userName = goalDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
