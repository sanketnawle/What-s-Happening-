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
import java.util.Arrays;
import java.util.List;


public class NotAttending extends AppCompatActivity {

    public String event_id;

    ArrayList<SelectYourEvent> selectYourEvents;
    List<SelectYourEvent> temp;

    ListView listView;
    YourEventAdapter adapter;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_attending);

        Bundle broadcastData = getIntent().getExtras();
        event_id= broadcastData.getString("Event id");

        String url ="http://whatshappening.16mb.com/getNotAttendingFriends.php?id="+event_id;

        loading = ProgressDialog.show(NotAttending.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                String responsenull = "{"+"\"result\":"+"[]"+"}";
                if(response.equals(responsenull)) {
                    setContentView(R.layout.nothing_to_show);
                    return;
                }
                showJSON(response);
                Log.e("Response in Attending", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:", "OnErrorResponse");
                Toast.makeText(NotAttending.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(NotAttending.this);
        requestQueue.add(stringRequest);


    }


    private void showJSON(String response) {
        ArrayAdapter<String> listAdapter ;
        String friend_name[];
        String friend_no[];
        String friends[];
        // String friend_no="";
        // String friend_name = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            Log.e("JSONArray:", result.toString());
            friend_name = new String[result.length()];
            friend_no = new String[result.length()];
            friends = new String[result.length()];

            for (int i = 0; i <result.length(); i++) {
                JSONObject requestData = result.getJSONObject(i);
                friend_name[i] = requestData.getString("friend_name");
                friend_no[i] = requestData.getString("friend_no");
                friends[i] = friend_name[i]+": \n"+friend_no[i];
                Log.e("UserName: ", friends[i]);
                //  Log.e("Friend Phone: ", friend_name);
            }

            ArrayList<String> FriendsList = new ArrayList<String>();

            ListView listView = (ListView) findViewById(R.id.notAttendingListView);
            FriendsList.addAll(Arrays.asList(friends));
            // FriendsList.add(friend_name+"\n"+friend_no);
            listAdapter = new ArrayAdapter<String>(this, R.layout.single_not_attending, FriendsList);
            Log.e("Friends in FriendList",FriendsList.toString());

            listAdapter.notifyDataSetChanged();
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String friend_details = ((TextView) view).getText().toString();
                    String[] parts = friend_details.split("\\n"); // String array, each element is text between dots

                    String friend_name = parts[0];
                    String friend_phone = parts[1];
                    Log.e("Friend name:", friend_name);
                    Log.e("Friend phone:", friend_phone);

                    Intent send = new Intent(NotAttending.this, Profile_MainScreen.class);
                    send.putExtra("userNumber", friend_phone);
                    send.putExtra("Calling", "NotAttending");
                    startActivity(send);

                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
