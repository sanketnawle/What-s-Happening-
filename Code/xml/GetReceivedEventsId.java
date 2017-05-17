package com.example.sanketnawle.whatshappening;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetReceivedEventsId extends Activity{

    public String userNumber,id;
  //  final String getId1[]={""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_events);
        Log.e("Entered GetREventsId: ", "OnCreate");

        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");

        Log.e("User no from IFriends", userNumber);


        String url = "http://whatshappening.16mb.com/getReceivedIEventsId.php?id="+userNumber;

        Log.e("URL",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("{\"result\":[]}")){
                    Toast.makeText(GetReceivedEventsId.this, "No events to show", Toast.LENGTH_LONG).show();
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
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(GetReceivedEventsId.this);
        requestQueue.add(stringRequest);

    }

    static String getId[];


    public void showJSON(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            Log.e("JSONArray:",result.toString());
            getId= new String[result.length()];

            Log.e("Length of getId:",String.valueOf(getId.length));
            for(int i=0;i<result.length();i++) {
                JSONObject requestData = result.getJSONObject(i);
                 getId[i] = requestData.getString("id");
                 Log.e("ID: " + i + " in GetREId: ", getId[i].toString());
            }


            Log.e("IDs in GetREId: ", getId.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

      /*  getId1 = new String[getId.length];

        for(int i=0;i<getId.length;i++) {
            getId1[i]=getId[i];
            Log.e("ID1: " + i + " in GetREId1: ", getId1[i].toString());
        } */



        Intent send = new Intent(this, ReceivedEvents.class);
        send.putExtra("userNumber", userNumber);
        Log.e("User no in GetREId: ",userNumber);
        startActivity(send);

    }

   public String[] getReceivedId()
   {
       return getId;
   }

}
