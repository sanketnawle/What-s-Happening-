package com.example.sanketnawle.whatshappening;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class P_ReceivedEventDetails extends Activity {


    public String event_name,user_name, event_details, event_location, event_time,event_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_received_event_details);

        Bundle broadcastData = getIntent().getExtras();
        event_name = broadcastData.getString("Event name");
        user_name = broadcastData.getString("User name");
        event_details = broadcastData.getString("Event details");
        event_location = broadcastData.getString("Event location");
        event_time = broadcastData.getString("Event time");
        event_date = broadcastData.getString("Event date");


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


    public void goToReminder(View view)
    {
        Toast.makeText(P_ReceivedEventDetails.this, "Went to Reminder", Toast.LENGTH_LONG).show();
        Toast.makeText(P_ReceivedEventDetails.this, "Went to Reminder", Toast.LENGTH_LONG).show();
        Intent calendarIntent = new Intent(Intent.ACTION_EDIT);
        calendarIntent.setType("vnd.android.cursor.item/event");
        calendarIntent.putExtra("title", event_name);
        //calendarIntent.putExtra("beginTime", startTimeMillis);
        //calendarIntent.putExtra("endTime", endTimeMillis);
        calendarIntent.putExtra("description", "Description");
        startActivity(calendarIntent);
    }

}
