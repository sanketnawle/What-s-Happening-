package com.example.sanketnawle.whatshappening;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainScreen extends AppCompatActivity {
    public String userNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");

    }


    public void yourEventsClick(View view)
    {
        Intent send = new Intent(this, YourEvents.class);
        send.putExtra("userNumber", userNumber);
        startActivity(send);

    }




    public void receivedEventsClick(View view)
    {
       Intent send = new Intent(this, GetReceivedEventsId.class);
        send.putExtra("userNumber", userNumber);
        startActivity(send);
    }

}
