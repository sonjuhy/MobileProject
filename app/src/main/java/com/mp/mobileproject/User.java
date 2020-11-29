package com.mp.mobileproject;

import com.mp.mobileproject.Calendar.CalendarInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String ID;
    private String PW;

    public User(String name, String ID, String PW){
        this.name = name;
        this.ID = ID;
        this.PW = PW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

}
