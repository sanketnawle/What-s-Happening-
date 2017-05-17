package com.example.sanketnawle.whatshappening;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ReceivedEventDetails extends Activity {


    public String event_name,user_name, event_details, event_location, event_time,event_date,userNumber,event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.received_event_details);

        Bundle broadcastData = getIntent().getExtras();
        event_name = broadcastData.getString("Event name");
        event_details = broadcastData.getString("Event details");
        event_location = broadcastData.getString("Event location");
        event_time = broadcastData.getString("Event time");
        event_date = broadcastData.getString("Event date");
        event_id = broadcastData.getString("Event id");
        Log.e("Event id in RED:",event_id);
        user_name = broadcastData.getString("User name");
        userNumber = broadcastData.getString("userNumber");
        Log.e("User no in REDetails: ",userNumber);

        TextView ename = (TextView) findViewById(R.id.eventName);
        TextView uname = (TextView) findViewById(R.id.user_name);
        TextView edetails = (TextView) findViewById(R.id.eventDetails);
        TextView elocation = (TextView) findViewById(R.id.eventLocation);
        TextView etime = (TextView) findViewById(R.id.eventTime);
        TextView edate = (TextView) findViewById(R.id.eventDate);

        ename.setText(event_name);
        uname.setText(user_name);
        edetails.setText(event_details);
        elocation.setText(event_location);
        etime.setText(event_time);
        edate.setText(event_date);

    }



    public void sendResponse(View view)
    {
        final ProgressDialog loading;
        String url= "http://whatshappening.16mb.com/send_i_response.php?friend_no="+userNumber+"&event_id="+event_id;

        RadioButton attending = (RadioButton)findViewById(R.id.attending);
        RadioButton not_attending = (RadioButton)findViewById(R.id.not_attending);
        RadioButton may_be_attending = (RadioButton)findViewById(R.id.may_be_attending);

        if(attending.isChecked()) {
            url += "&response=1";

        }
        if(not_attending.isChecked())
            url+="&response=2";

        if(may_be_attending.isChecked())
            url+="&response=3";

        Log.e("url in REventDetails: ",url);

        loading = ProgressDialog.show(ReceivedEventDetails.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
              /*  SelectRequest data = selectRequests.get(0);
                Log.e("data in Requests", data.toString()); */
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(ReceivedEventDetails.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ReceivedEventDetails.this);
        requestQueue.add(stringRequest);

    }



    private void showJSON(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            Log.e("JSON object", "Created");

            if (jsonObject.names().get(0).equals("success")) {
                Toast.makeText(ReceivedEventDetails.this, "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(ReceivedEventDetails.this, "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void goToReminder(View view)
    {
        Toast.makeText(ReceivedEventDetails.this, "Went to Reminder", Toast.LENGTH_LONG).show();
        Intent calendarIntent = new Intent(Intent.ACTION_EDIT);
        calendarIntent.setType("vnd.android.cursor.item/event");
        calendarIntent.putExtra("title", event_name);
        //calendarIntent.putExtra("beginTime", startTimeMillis);
        //calendarIntent.putExtra("endTime", endTimeMillis);
        calendarIntent.putExtra("description", "Description");
        startActivity(calendarIntent);
    }

}
