package com.example.sanketnawle.whatshappening;


import android.util.Log;

public class SelectYourEvent {

    public String getFriendPhone() {
        return fphone;
    }

    public void setFriendPhone(String fphone) {
        this.fphone = fphone;
    }


    public String fphone,userNumber,ename, details, location, time,date,eid;


    public void setUserNumber(String userNo)
    {
        userNumber = userNo;
        Log.e("user no in SU:setUserno", userNumber);
    }


    public String getUserNumber()
    {
        Log.e("User no in SU:getUserNo",userNumber);
        return userNumber;

    }

    public String getEventName() {
        return ename;
    }

    public void setEventName(String ename) {
        this.ename = ename;
    }

    public String getEventDetails() {
        return details;
    }

    public void setEventDetails(String details) {
        this.details = details;
    }
    public String getEventLocation() {
        return location;
    }

    public void setEventLocation(String location) {
        this.location = location;
    }

    public String getEventTime() {
        return time;
    }

    public void setEventTime(String time) {
        this.time = time;
    }

    public String getEventDate() {
        return date ;
    }

    public void setEventDate(String date) {
        this.date = date;
    }

    public String getEventId() {
        return eid ;
    }

    public void setEventId(String eid) {
        this.eid = eid;
    }





}
