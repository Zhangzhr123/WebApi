package com.authine.cloudpivot.mybatis.entity;

import java.util.ArrayList;

public class MeetingApplyInfo {
    private String ID ;
    private  String Name;
    private  String Address;
    private ArrayList<Double> TimeLine;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public ArrayList<Double> getTimeLine() {
        return TimeLine;
    }

    public void setTimeLine(ArrayList<Double> timeLine) {
        TimeLine = timeLine;
    }

    @Override
    public String toString() {
        return "MeetingApplyInfo{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", TimeLine=" + TimeLine +
                '}';
    }
}
