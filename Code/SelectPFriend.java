package com.example.sanketnawle.whatshappening;


import android.graphics.Bitmap;
import android.util.Log;

public class SelectPFriend {
    String phone,id;
    String name;
    Bitmap thumb;

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        Log.e("Phone no in setPhone", phone);
    }


    public String userNumber;

    public void setUserNumber(String userNumber)
    {

        this.userNumber= userNumber;
        Log.e("UserNo in setUserNo",userNumber);
    }

    public String getUserNumber()
    {
        Log.e("UserNo in getUserno",userNumber);
        return userNumber;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Log.e("Name no in setName",name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        Log.e("Id no in setId",id);
    }

}


