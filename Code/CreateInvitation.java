package com.example.sanketnawle.whatshappening;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreateInvitation extends AppCompatActivity {

    public String userNumber,id;
    public String date1,time1;
    private RequestQueue requestQueue;
    private static final String log = "http://whatshappening.16mb.com/add_i_friends.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invitation);


        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");
        Log.e("User no in public post", userNumber);

        requestQueue = Volley.newRequestQueue(this);





        custom = new CustomDateTimePicker(this,
                new CustomDateTimePicker.ICustomDateTimeListener() {

                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year, String monthFullName,
                                      String monthShortName, int monthNumber, int date,
                                      String weekDayFullName, String weekDayShortName,
                                      int hour24, int hour12, int min, int sec,
                                      String AM_PM) {
                        ((TextView) findViewById(R.id.lablel))
                                .setText(calendarSelected
                                        .get(Calendar.DAY_OF_MONTH)
                                        + "/" + (monthNumber + 1) + "/" + year
                                        + ", " + hour12 + ":" + min
                                        + " " + AM_PM);

                         date1 = (calendarSelected
                                .get(Calendar.DAY_OF_MONTH)
                                + "/" + (monthNumber + 1) + "/" + year).toString();

                         time1 = ( hour12 + ":" + min
                                + " " + AM_PM).toString();

                    }

                    @Override
                    public void onCancel() {

                    }
                });
        /**
         * Pass Directly current time format it will return AM and PM if you set
         * false
         */
        custom.set24HourFormat(false);
        /**
         * Pass Directly current data and time to show when it pop up
         */
        custom.setDate(Calendar.getInstance());

        findViewById(R.id.button_date).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        custom.showDialog();
                    }
                });

        findViewById(R.id.sendEvent).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        request = new StringRequest(Request.Method.POST, log, new Response.Listener<String>() {
                            @Override

                            public void onResponse(String response) {
                                try {
                                    Log.e("Response in public post",response);
                                    //JSONObject jsonObject = new JSONObject(response);
                                    if (!response.equals("0")) {
                                        Toast.makeText(getApplicationContext(), "SUCCESS ", Toast.LENGTH_SHORT).show();
                                        id = response;
                                        Log.e("Id value: ", id);
                                        Intent send = new Intent(CreateInvitation.this, IFriends.class);
                                        send.putExtra("userNumber", userNumber);
                                        send.putExtra("id",id);
                                        startActivity(send);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                EditText ename = (EditText) findViewById(R.id.eventName);
                                EditText edetails = (EditText) findViewById(R.id.eventDetails);
                                EditText elocation = (EditText) findViewById(R.id.eventLocation);

                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("user_name", "Abhi");
                                hashMap.put("user_no", userNumber);
                                hashMap.put("event_name", ename.getText().toString());
                                hashMap.put("event_description", edetails.getText().toString());
                                hashMap.put("event_location", elocation.getText().toString());
                                hashMap.put("event_date", date1);
                                hashMap.put("event_time", time1);

                                Log.e("Hashmap in public post",hashMap.toString());
                                return hashMap;

                            }
                        };

                        requestQueue.add(request);
                    }
                });


                    }

    CustomDateTimePicker custom;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.i_activity_event_details, menu);
        return true;
    }

}

