package com.example.sanketnawle.whatshappening;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class P_YourEventDetails extends Activity {


    public String userNumber,event_id,event_name, event_details, event_location, event_time,event_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_your_event_details);

        Bundle broadcastData = getIntent().getExtras();
        event_name = broadcastData.getString("Event name");
        event_details = broadcastData.getString("Event details");
        event_location = broadcastData.getString("Event location");
        event_time = broadcastData.getString("Event time");
        event_date = broadcastData.getString("Event date");
        event_id = broadcastData.getString("Event id");
        userNumber = broadcastData.getString("userNumber");

        TextView ename = (TextView) findViewById(R.id.eventName);
        TextView edetails = (TextView) findViewById(R.id.eventDetails);
        TextView elocation = (TextView) findViewById(R.id.eventLocation);
        TextView etime = (TextView) findViewById(R.id.eventTime);
        TextView edate = (TextView) findViewById(R.id.eventDate);

        ename.setText(event_name);
        edetails.setText(event_details);
        elocation.setText(event_location);
        etime.setText(event_time);
        edate.setText(event_date);

    }


    public void goToPFriends(View view)
    {
        Intent intent = new Intent(this,PFriends.class);
        intent.putExtra("userNumber",userNumber);
        intent.putExtra("id",event_id);
        startActivity(intent);

    }

}
