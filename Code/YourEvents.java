package com.example.sanketnawle.whatshappening;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

class Config20 {
    public static final String DATA_URL = "http://whatshappening.16mb.com/getYourIEvents.php?id=";
  //  public static final String DATA_URL2 = "http://whatshappening.16mb.com/removeData.php?id=";
   // public static final String DATA_URL3 = "http://whatshappening.16mb.com/addData.php?id=";
    public static final String KEY_ENAME = "event_name";
    public static final String KEY_DATE  = "event_date";
    public static final String KEY_DETAILS = "event_details";
    public static final String KEY_LOCATION = "event_location";
    public static final String KEY_TIME = "event_time";
    public static final String KEY_EID = "event_id";
    public static final String JSON_ARRAY = "result";
}


public class YourEvents extends AppCompatActivity {

    ArrayList<SelectYourEvent> selectYourEvents;
    List<SelectYourEvent> temp;

    ListView listView;
    YourEventAdapter adapter;
    public String userNumber;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_events);

        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");

        Log.e("User no from onCreate", userNumber);
        selectYourEvents = new ArrayList<SelectYourEvent>();
        listView = (ListView) findViewById(R.id.yourEventsList);

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = Config20.DATA_URL + userNumber;
        Log.e("URL",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                String responsenull = "{"+"\"result\":"+"[]"+"}";
                if(response.equals(responsenull)) {
                    setContentView(R.layout.nothing_your_events);
                }
                showJSON(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(YourEvents.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(YourEvents.this);
        requestQueue.add(stringRequest);

        listView.setFastScrollEnabled(true);

    }

    private void showJSON(String response) {
        String event_name = "";
        String event_date="";
        String event_details = "";
        String event_location = "";
        String event_time = "";
        String event_id = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            Log.e("Response in YourEvents:",response);
            JSONArray result = jsonObject.getJSONArray(Config20.JSON_ARRAY);
            Log.e("JSONArray:",result.toString());
            for(int i=0;i<result.length();i++) {
                JSONObject requestData = result.getJSONObject(i);
                event_name = requestData.getString(Config20.KEY_ENAME);
                event_details = requestData.getString(Config20.KEY_DETAILS);
                event_location = requestData.getString(Config20.KEY_LOCATION);
                event_time = requestData.getString(Config20.KEY_TIME);
                event_date = requestData.getString(Config20.KEY_DATE);
                event_id = requestData.getString(Config20.KEY_EID);

                Log.e("Event date: ", event_date);
                Log.e("Event Name: ", event_name);
                Log.e("Event details: ", event_details);
                Log.e("Event location: ", event_location);
                Log.e("Event time: ", event_time);

                SelectYourEvent selectYourEvent = new SelectYourEvent();

                selectYourEvent.setEventName(event_name);
                selectYourEvent.setEventDate(event_date);
                selectYourEvent.setUserNumber(userNumber);
                selectYourEvent.setEventDetails(event_details);
                selectYourEvent.setEventLocation(event_location);
                selectYourEvent.setEventTime(event_time);
                selectYourEvent.setEventId(event_id);

                Log.e("userNo in Requests: ", userNumber);

                selectYourEvents.add(selectYourEvent);

                adapter = new YourEventAdapter(selectYourEvents, YourEvents.this);
                listView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void goToEvent(View view)
    {
        Intent send = new Intent(this,CreateInvitation.class);
        send.putExtra("userNumber",userNumber);
        startActivity(send);

    }


    public void goToNull()
    {
        Toast.makeText(YourEvents.this,"No events created", Toast.LENGTH_LONG).show();

    }

}

