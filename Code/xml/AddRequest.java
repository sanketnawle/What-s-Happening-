
package com.example.sanketnawle.whatshappening;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddRequest extends Activity{

    public String userNumber, phone_no, url;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);


        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("User no");
        phone_no = broadcastData.getString("Friend no");
        url = broadcastData.getString("URL");

        loading = ProgressDialog.show(AddRequest.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
              /*  R_SelectRequest data = RSelectRequests.get(0);
                Log.e("data in Requests", data.toString()); */
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(AddRequest.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(AddRequest.this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            Log.e("JSON object", "Created");

            if (jsonObject.names().get(0).equals("success")) {
                Toast.makeText(AddRequest.this, "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Requests.class);
                intent.putExtra("userNumber", userNumber);
                this.startActivity(intent);
            } else {
                Toast.makeText(AddRequest.this, "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
