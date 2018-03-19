package com.example.user.keepingmeontrack.models;

/**
 * Created by User on 15.03.2018.
 */

public class CaloriCalculate {
    private String name;
    private int calori;
    private String  tur;

    public CaloriCalculate(String name, int calori, String tur) {
        this.name = name;
        this.calori = calori;
        this.tur = tur;
    }

    public CaloriCalculate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalori() {
        return calori;
    }

    public void setCalori(int calori) {
        this.calori = calori;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }
}
