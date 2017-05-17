package com.example.sanketnawle.whatshappening;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class P_EventDetails extends AppCompatActivity {

    public String event_name, event_details, event_location, event_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_your_event_details);

        Bundle broadcastData = getIntent().getExtras();
        event_name = broadcastData.getString("Event name");
        event_details = broadcastData.getString("Event details");
        event_location = broadcastData.getString("Event location");
        event_time = broadcastData.getString("Event time");


        TextView ename = (TextView) findViewById(R.id.eventName);
        TextView edetails = (TextView) findViewById(R.id.eventDetails);
        TextView elocation = (TextView) findViewById(R.id.eventLocation);
        // TextView etime = (TextView) findViewById(R.id.eventTime);

        ename.setText(event_name);
        edetails.setText(event_details);
        elocation.setText(event_location);
        // etime.setText(event_time);

    }

}


