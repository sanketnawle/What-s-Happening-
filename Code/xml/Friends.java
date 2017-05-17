package com.example.sanketnawle.whatshappening;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class Friends extends AppCompatActivity {

    public String userNumber;
    private ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");
        Log.e("userNumber in friends: ",userNumber);

        String url ="http://whatshappening.16mb.com/getFriends.php?id="+userNumber;

        loading = ProgressDialog.show(Friends.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                String responsenull = "{"+"\"result\":"+"[]"+"}";
                if(response.equals(responsenull)) {
                    setContentView(R.layout.nothing_friends);
                    return;
                }
                showJSON(response);
                Log.e("Response in Friends",response);
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(Friends.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Friends.this);
        requestQueue.add(stringRequest);
    }


    public void addFriend(View view)
    {
        Intent send = new Intent(this, Contacts.class);
        send.putExtra("userNumber", userNumber);
        startActivity(send);

    }


    private void showJSON(String response) {
        ArrayAdapter<String> listAdapter = null;
        String user_phone = "";
        String user_name = "";
        String status = "";
        String friend_phone = "";

        try {

            ArrayList<String> FriendsList = new ArrayList<String>();
            listAdapter = new ArrayAdapter<String>(this, R.layout.friend, FriendsList);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            Log.e("JSONArray:", result.toString());
            ListView listView = (ListView) findViewById(R.id.friendsListView);
            for (int i = 0; i < result.length(); i++) {
                JSONObject requestData = result.getJSONObject(i);
                user_name = requestData.getString(Config.KEY_UNAME);
                user_phone = requestData.getString(Config.KEY_UPHONE);
                friend_phone = requestData.getString(Config.KEY_FPHONE);
                status = requestData.getString(Config.KEY_STATUS);

                Log.e("UserName: ", user_name);
                Log.e("User Phone: ", user_phone);
                Log.e("Friend Phone: ", friend_phone);
                Log.e("Status: ", status);

                listAdapter.add(user_name + "\n" + friend_phone);
                listView.setAdapter(listAdapter);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String friend_details = ((TextView) view).getText().toString();
                    String[] parts = friend_details.split("\\n"); // String array, each element is text between dots

                    String friend_name = parts[0];
                    String friend_phone=parts[1];
                    Log.e("Friend name:",friend_name);
                    Log.e("Friend phone:",friend_phone);

                    Intent send = new Intent(Friends.this, Profile_MainScreen.class);
                    send.putExtra("userNumber", friend_phone);
                    send.putExtra("Calling","Friends");
                    startActivity(send);


                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

      //  ListView friendsListView = (ListView)findViewById(R.id.friendsListView);


    }


   /* public void onFriendClick(View view){
        Intent send = new Intent(this, Profile_MainScreen.class);
        send.putExtra("userNumber", userNumber);
        startActivity(send);
    }*/

    }
