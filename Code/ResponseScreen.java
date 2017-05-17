package com.example.sanketnawle.whatshappening;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ResponseScreen extends AppCompatActivity {

    public String event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_screen);

        Bundle broadcastData = getIntent().getExtras();
        event_id= broadcastData.getString("Event id");
    }


    public void attending(View view)
    {

        Intent send = new Intent(this, Attending.class);
        send.putExtra("Event id", event_id);
        startActivity(send);

    }

    public void notAttending(View view)
    {

        Intent send = new Intent(this, NotAttending.class);
        send.putExtra("Event id", event_id);
        startActivity(send);

    }

    public void mayBe(View view)
    {

        Intent send = new Intent(this, MayBe.class);
        send.putExtra("Event id", event_id);
        startActivity(send);

    }

    public void noResponse(View view)
    {

        Intent send = new Intent(this, NoResponse.class);
        send.putExtra("Event id", event_id);
        startActivity(send);

    }











}
