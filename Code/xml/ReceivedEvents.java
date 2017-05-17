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


class ConfigReceivedEvents {
    public static final String DATA_URL = "http://whatshappening.16mb.com/getReceivedIEvents.php?id=";
    //  public static final String DATA_URL2 = "http://whatshappening.16mb.com/removeData.php?id=";
    // public static final String DATA_URL3 = "http://whatshappening.16mb.com/addData.php?id=";
    public static final String KEY_ENAME = "event_name";
    public static final String KEY_UNAME = "user_name";
    public static final String KEY_DATE  = "event_date";
    public static final String KEY_DETAILS = "event_details";
    public static final String KEY_LOCATION = "event_location";
    public static final String KEY_TIME = "event_time";
    public static final String KEY_ID = "event_id";
    public static final String JSON_ARRAY = "result";

}


public class ReceivedEvents extends AppCompatActivity {

    ArrayList<SelectReceivedEvent> selectReceivedEvents;
    List<SelectReceivedEvent> temp;

    ListView listView;
    ReceivedEventAdapter adapter;

    public String userNumber;
   // private RequestQueue requestQueue;
  //  private static final String log = "http://whatshappening.16mb.com/getReceivedPEvents.php";
   // private StringRequest request;

    private ProgressDialog loading;
    public String[] id;
    public String sendId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_events);

        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");
        Log.e("User no in REvents", userNumber);

        GetReceivedEventsId G = new GetReceivedEventsId();
        id= G.getReceivedId();

        if(id.length>0) {
            for (int i = 0; i < id.length - 1; i++) {
                sendId += id[i] + ",";
            }
        }

        if(id.length>0) {
            sendId += id[id.length - 1];
        }

        Log.e("sendID in REvents: ",sendId);


        Log.e("Ids in ReceivedEvents",id.toString());

        selectReceivedEvents = new ArrayList<SelectReceivedEvent>();
        listView = (ListView) findViewById(R.id.receivedEventsList);

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = ConfigReceivedEvents.DATA_URL + sendId;
        Log.e("URL",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                String responsenull = "{"+"\"result\":"+"[]"+"}";
                if(response.equals(responsenull)|| response.equals("")) {
                    setContentView(R.layout.nothing_to_show);
                    return;
                }
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(ReceivedEvents.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ReceivedEvents.this);
        requestQueue.add(stringRequest);

        listView.setFastScrollEnabled(true);

    }


    private void showJSON(String response) {
        String event_name = "";
        String user_name = "";
        String event_date="";
        String event_details = "";
        String event_location = "";
        String event_time = "";
        String event_id = "";
        try {

            Log.e("Response in REvents:",response);
            JSONObject jsonObject = new JSONObject(response);

            JSONArray result = jsonObject.getJSONArray(ConfigReceivedEvents.JSON_ARRAY);
            Log.e("JSONArray:",result.toString());
            for(int i=0;i<result.length();i++) {
                JSONObject requestData = result.getJSONObject(i);
                event_name = requestData.getString(ConfigReceivedEvents.KEY_ENAME);
                user_name = requestData.getString(ConfigReceivedEvents.KEY_UNAME);
                event_details = requestData.getString(ConfigReceivedEvents.KEY_DETAILS);
                event_location = requestData.getString(ConfigReceivedEvents.KEY_LOCATION);
                event_time = requestData.getString(ConfigReceivedEvents.KEY_TIME);
                event_date = requestData.getString(ConfigReceivedEvents.KEY_DATE);
                event_id = requestData.getString(ConfigReceivedEvents.KEY_ID);

                Log.e("UserName in REvents:: ", user_name);
                Log.e("Event id in REvents: ", event_id);
                Log.e("Event date in REvents: ", event_date);
                Log.e("EventName in REvents:: ", event_name);
                Log.e("Edetails in REvents:: ", event_details);
                Log.e("Elocation in REvents:: ", event_location);
                Log.e("Eventtime in REvents:: ", event_time);

                SelectReceivedEvent selectReceivedEvent = new SelectReceivedEvent();

                selectReceivedEvent.setEventName(event_name);
                selectReceivedEvent.setUserName(user_name);
                selectReceivedEvent.setEventDate(event_date);
                selectReceivedEvent.setUserNumber(userNumber);
                selectReceivedEvent.setEventDetails(event_details);
                selectReceivedEvent.setEventLocation(event_location);
                selectReceivedEvent.setEventTime(event_time);
                selectReceivedEvent.setEventId(event_id);

                Log.e("userNo in Requests: ", userNumber);

                selectReceivedEvents.add(selectReceivedEvent);

                adapter = new ReceivedEventAdapter(selectReceivedEvents, ReceivedEvents.this);
                listView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
