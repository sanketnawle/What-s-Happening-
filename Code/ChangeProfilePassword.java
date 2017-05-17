package com.example.sanketnawle.whatshappening;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * Created by Sanket N on 01-04-2016.
 */
public class ChangeProfilePassword extends Activity {

    String userNumber;
    private RequestQueue requestQueue;
    private static final String change_log = "http://whatshappening.16mb.com/ProfileChangePassword.php";
    private StringRequest request;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // ToggleButton tg = (ToggleButton)findViewById(R.id.tglReadWrite);
       // tg.setChecked(false);
        requestQueue = Volley.newRequestQueue(this);
        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");
        Log.e("userNo in ChangeP_Pass", userNumber);

        // Create Object of Dialog class
        final Dialog change = new Dialog(this);
        // Set GUI of login screen
        change.setContentView(R.layout.password_dialog);
        change.setTitle("Change password");

        // Init button of login GUI
        Button btnChange = (Button) change.findViewById(R.id.btnChange);
        final EditText txtPasswordChange = (EditText) change.findViewById(R.id.txtPasswordChange);

        // Attached listener for login GUI button
        btnChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtPasswordChange.getText().toString().trim().length() > 0) {

                    request = new StringRequest(Request.Method.POST, change_log, new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            try {
                                Log.e("Response in ChangePPass",response);
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Password changed successfully ", Toast.LENGTH_SHORT).show();
                                    change.dismiss();
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Error: please try again", Toast.LENGTH_SHORT).show();
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

                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("password", txtPasswordChange.getText().toString());
                            hashMap.put("userNumber", userNumber);
                            Log.e("Hashmap in public post",hashMap.toString());
                            return hashMap;

                        }
                    };

                    requestQueue.add(request);


                } else {
                    Toast.makeText(ChangeProfilePassword.this,
                            "Please enter new Password", Toast.LENGTH_LONG).show();

                }
            }
        });

        change.show();

    }

}
