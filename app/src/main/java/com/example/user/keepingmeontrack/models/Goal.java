package com.example.user.keepingmeontrack.models;

/**
 * Created by User on 14.02.2018.
 */

public class Goal {
    private String id;
    private String uid;
    private String Name;
    private String totalMoney;
    private String dailyAllowance;
    private String startDate;
    private String endDate;
    private String reminding;
    private int type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Goal(String name, String startDate, String endDate, String reminding, int type) {
        Name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminding = reminding;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Goal() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getDailyAllowance() {
        return dailyAllowance;
    }

    public void setDailyAllowance(String dailyAllowance) {
        this.dailyAllowance = dailyAllowance;
    }

    public Goal(String id, String uid, String name, String totalMoney, String dailyAllowance, String startDate, String endDate, String reminding, int type) {
        this.uid = uid;
        Name = name;
        this.totalMoney = totalMoney;
        this.dailyAllowance = dailyAllowance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminding = reminding;
        this.type = type;
        this.id = id;

    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReminding() {
        return reminding;
    }

    public void setReminding(String reminding) {
        this.reminding = reminding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}






