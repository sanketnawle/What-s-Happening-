package com.example.sanketnawle.whatshappening;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Contacts extends Activity {

    // ArrayList
    ArrayList<R_SelectUser> RSelectUsers;
    List<R_SelectUser> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones, email;
    public List<String> mainList;
    // Pop up
    ContentResolver resolver;
    SearchView search;
    R_SelectUserAdapter adapter;


    public String userNumber;
    String phone_no;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("Entered Contacts:","OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");
        Log.e("User no from onCreate", userNumber);

        RSelectUsers = new ArrayList<R_SelectUser>();
        resolver = this.getContentResolver();
        listView = (ListView) findViewById(R.id.contacts_list);

        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();

        search = (SearchView) findViewById(R.id.searchView);

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);
                return false;
            }
        });
    }







    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("Entered Contacts","Pre Execute");

        }


        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone
            Log.e("Entered Contacts","Do In Background");
            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(Contacts.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }
                int i=0;
                while (phones.moveToNext()) {
                    mainList = new ArrayList<String>();
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {

                        mainList.add(phoneNumber);
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {
                            Log.e("No Image Thumb", "--------------");
                        }
                     //  Log.e("Added",mainList.toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    R_SelectUser RSelectUser = new R_SelectUser();
                    RSelectUser.setThumb(bit_thumb);
                    RSelectUser.setName(name);
                    RSelectUser.setPhone(phoneNumber);
                    RSelectUser.setEmail(id);
                  //  RSelectUser.getAddContact();
                    RSelectUser.setAddContact("+");
                    RSelectUser.setUserNumber(userNumber);
                    RSelectUsers.add(RSelectUser);
                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }
            //phones.close();
            return null;
        }






        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("Entered Contacts", "Post Execute");
            adapter = new R_SelectUserAdapter(RSelectUsers, Contacts.this);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    R_SelectUser data = RSelectUsers.get(i);

                }
            });

            listView.setFastScrollEnabled(true);
        }
    }

/*
    public void called(final String phone_no)
    {
        View view = getLayoutInflater().inflate(R.layout.activity_contacts, null);
        this.phone_no = phone_no;
        goToCalled(view);
    }

    public void goToCalled(View view)
    {
        Intent send = new Intent(this, ContactRequest.class);
        send.putExtra("phone_no", phone_no);
        Log.e("phone no",phone_no);
        startActivity(send);

    }

    */



    @Override
    protected void onStop() {
        super.onStop();
        phones.close();
    }


}

