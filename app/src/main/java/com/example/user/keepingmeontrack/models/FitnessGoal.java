package com.example.user.keepingmeontrack.models;

/**
 * Created by User on 05.03.2018.
 */

public class FitnessGoal {
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer5;
    private String key;
    private String Uid;

    public FitnessGoal(String answer1, String answer2, String answer3, String answer5, String key, String uid) {
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer5 = answer5;
        this.key = key;
        Uid = uid;
    }

    public FitnessGoal() {
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}