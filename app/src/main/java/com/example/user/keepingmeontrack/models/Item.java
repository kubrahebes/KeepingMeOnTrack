package com.example.user.keepingmeontrack.models;

/**
 * Created by Mahmoud on 2/23/2018.
 */

public class Item {
    String goalName;
    String goalDesc;
    String userName;

    public Item(String goalName, String goalDesc, String userName) {
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
}
