package com.mp.mobileproject.Calendar;

public class Listitem_calendar {
    private String name;
    private int type;

    public Listitem_calendar(String name, int type){
        this.name = name;
        this.type = type;
    }
    public void SetName(String name){
        this.name = name;
    }
    public String GetName(){
        return this.name;
    }
    public void SetType(int type){this.type = type;}
    public int GetType(){return this.type;}
}
