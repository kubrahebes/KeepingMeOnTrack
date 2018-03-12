package com.example.user.keepingmeontrack.models;

/**
 * Created by Mahmoud on 3/12/2018.
 */


//Model for check the isLiked in networking.

public class CheckItem {
    public String checker;
    public String networkId;


    //Empty constructor. Firebase needs enpty constructor to use.
    public CheckItem() {
    }

    public CheckItem(String checker, String networkId) {
        this.checker = checker;
        this.networkId = networkId;
    }
}
