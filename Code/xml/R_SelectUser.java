package com.example.sanketnawle.whatshappening;

import android.graphics.Bitmap;
import android.util.Log;


public class R_SelectUser {
    String name;

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    Bitmap thumb;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;

    public String getAddContact() {
        return addContact;
    }

     public void setAddContact(String addContact) {
        this.addContact = addContact;
    }


    String addContact = "+";
    Boolean called= false;
    String contact;
    public String userNumber;




    public void setUserNumber(String userNo)
    {
        userNumber = userNo;
        Log.e("user no in SU:setUserno",userNumber);
    }


    public String getUserNumber()
    {
        Log.e("User no in SU:getUserNo",userNumber);
        return userNumber;

    }

 /*   public void callContact(String contact1)
    {
        called = true;
        contact = contact1;

        Contacts c = new Contacts();
        Log.e("Phone no in R_SelectUser", contact);
        c.called(contact);

    }

    */

  /*  public Boolean getStatus()
    {
        return called;
    }

    public String getContact()
    {
        return contact;

    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
