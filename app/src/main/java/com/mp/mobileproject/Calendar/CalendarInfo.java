package com.mp.mobileproject.Calendar;


import org.threeten.bp.LocalDate;

import java.io.Serializable;

public class CalendarInfo implements Serializable {
    private String name;
    private String content;
    private String type;
    private String member;
    private int count;
    private LocalDate localDate;

    public CalendarInfo(String name, String content, String type, LocalDate localDate, int count, String Member){
        this.name = name;
        this.content = content;
        this.type = type;
        this.localDate = localDate;
        this.content = content;
        this.member = Member;
        this.count = count;
    }
    public CalendarInfo(){

    }
    public void Cal_AllSetting(String name, String content, String type, LocalDate localDate, int count, String Member){
        this.name = name;
        this.content = content;
        this.type = type;
        this.localDate = localDate;
        this.content = content;
        this.member = Member;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
