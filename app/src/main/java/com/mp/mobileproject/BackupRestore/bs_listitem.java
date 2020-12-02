package com.mp.mobileproject.BackupRestore;

public class bs_listitem {
    private String name;
    private String size;
    private String PrePath;
    private long size_long;
    private boolean type;// true is folder false is file

    public bs_listitem(String name, String size, String Path, boolean type){
        this.name = name;
        this.size = size;
        this.PrePath = Path;
        this.type = type;
    }
    public bs_listitem(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrePath() {
        return PrePath;
    }

    public void setPrePath(String prePath) {
        PrePath = prePath;
    }

    public long getSize_long() {
        return size_long;
    }

    public void setSize_long(long size_long) {
        this.size_long = size_long;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
