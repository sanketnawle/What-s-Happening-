package com.example.sanketnawle.whatshappening;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


class Config1 {
    public static final String DATA_URL = "http://whatshappening.16mb.com/getFriends.php?id=";
  //  public static final String DATA_URL2 = "http://whatshappening.16mb.com/removeData.php?id=";
   // public static final String DATA_URL3 = "http://whatshappening.16mb.com/addData.php?id=";
    public static final String KEY_UNAME = "user_name";
    public static final String KEY_UPHONE = "user_phone";
    public static final String KEY_STATUS = "status";
    public static final String KEY_FPHONE = "friend_phone";
    public static final String JSON_ARRAY = "result";
}


public class PFriends extends AppCompatActivity {

    ArrayList<SelectPFriend> selectPFriends;
    List<SelectPFriend> temp;

    ListView listView;
    PFriendsAdapter adapter;
    public String userNumber,id;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfriends);
        Log.e("Entered Requests: ", "OnCreate");

        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");
        id = broadcastData.getString("id");

        Log.e("User no from PFriends", userNumber);
        selectPFriends = new ArrayList<SelectPFriend>();
        listView = (ListView) findViewById(R.id.pfriends_list);

        loading = ProgressDialog.show(PFriends.this, "Please wait...", "Fetching...", false, false);

        String url = Config1.DATA_URL + userNumber;
        Log.e("URL",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(PFriends.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(PFriends.this);
        requestQueue.add(stringRequest);

        listView.setFastScrollEnabled(true);

    }

    private void showJSON(String response) {
        String user_phone = "";
        String user_name = "";
        String status = "";
        String friend_phone="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config1.JSON_ARRAY);
            Log.e("JSONArray:",result.toString());
            for(int i=0;i<result.length();i++) {
                JSONObject requestData = result.getJSONObject(i);
                user_name = requestData.getString(Config1.KEY_UNAME);
                user_phone = requestData.getString(Config1.KEY_UPHONE);
                friend_phone = requestData.getString(Config1.KEY_FPHONE);
                status = requestData.getString(Config1.KEY_STATUS);

                Log.e("UserName: ", user_name);
                Log.e("User Phone: ", user_phone);
                Log.e("Friend Phone: ", friend_phone);
                Log.e("Status: ", status);


                SelectPFriend selectPFriend = new SelectPFriend();
                // selectRequest.setThumb(bit_thumb);
                selectPFriend.setName(user_name);
                selectPFriend.setPhone(friend_phone);
                selectPFriend.setUserNumber(userNumber);
                selectPFriend.setId(id);
                Log.e("userNo in PFriends: ", userNumber);
                Log.e("Id in PFriends: ",id);
                selectPFriends.add(selectPFriend);

                adapter = new PFriendsAdapter(selectPFriends, PFriends.this);
                listView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}









