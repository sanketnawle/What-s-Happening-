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


class Config10 {
    public static final String DATA_URL = "http://whatshappening.16mb.com/getFriends.php?id=";
  //  public static final String DATA_URL2 = "http://whatshappening.16mb.com/removeData.php?id=";
   // public static final String DATA_URL3 = "http://whatshappening.16mb.com/addData.php?id=";
    public static final String KEY_UNAME = "user_name";
    public static final String KEY_UPHONE = "user_phone";
    public static final String KEY_STATUS = "status";
    public static final String KEY_FPHONE = "friend_phone";
    public static final String JSON_ARRAY = "result";
}


public class IFriends extends AppCompatActivity {

    ArrayList<SelectIFriend> selectIFriends;
    List<SelectIFriend> temp;

    ListView listView;
    IFriendsAdapter adapter;
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

        Log.e("User no from IFriends", userNumber);
        selectIFriends = new ArrayList<SelectIFriend>();
        listView = (ListView) findViewById(R.id.pfriends_list);

        loading = ProgressDialog.show(IFriends.this, "Please wait...", "Fetching...", false, false);

        String url = Config10.DATA_URL + userNumber;
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
                        Toast.makeText(IFriends.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(IFriends.this);
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
            JSONArray result = jsonObject.getJSONArray(Config10.JSON_ARRAY);
            Log.e("JSONArray:",result.toString());
            for(int i=0;i<result.length();i++) {
                JSONObject requestData = result.getJSONObject(i);
                user_name = requestData.getString(Config10.KEY_UNAME);
                user_phone = requestData.getString(Config10.KEY_UPHONE);
                friend_phone = requestData.getString(Config10.KEY_FPHONE);
                status = requestData.getString(Config10.KEY_STATUS);

                Log.e("UserName: ", user_name);
                Log.e("User Phone: ", user_phone);
                Log.e("Friend Phone: ", friend_phone);
                Log.e("Status: ", status);


                SelectIFriend selectIFriend = new SelectIFriend();
                // selectRequest.setThumb(bit_thumb);
                selectIFriend.setName(user_name);
                selectIFriend.setPhone(friend_phone);
                selectIFriend.setUserNumber(userNumber);
                selectIFriend.setId(id);
                Log.e("userNo in IFriends: ", userNumber);
                Log.e("Id in IFriends: ",id);
                selectIFriends.add(selectIFriend);

                adapter = new IFriendsAdapter(selectIFriends, IFriends.this);
                listView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}









