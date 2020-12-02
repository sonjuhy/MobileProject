package com.mp.mobileproject.Calendar;

public class Listitem_calendar {
    private String name;
    private int type;
    private int count;

    public Listitem_calendar(String name, int type, int count){
        this.name = name;
        this.type = type;
        this.count = count;
    }
    public void SetName(String name){
        this.name = name;
    }
    public String GetName(){
        return this.name;
    }
    public void SetType(int type){this.type = type;}
    public int GetType(){return this.type;}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
