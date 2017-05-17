package com.example.sanketnawle.whatshappening;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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


public class ForgotProfilePassword extends Activity {

    String calling;
    private RequestQueue requestQueue;
   // private static final String log = "http://whatshappening.16mb.com/nfcAuthenticate.php";
    private static final String forgot_log = "http://whatshappening.16mb.com/ProfileForgotPassword.php";
    private StringRequest request;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  ToggleButton tg = (ToggleButton)findViewById(R.id.tglReadWrite);
        // tg.setChecked(false);
        Log.e("Entered: ","ForgotProfilePassword");
        requestQueue = Volley.newRequestQueue(this);
       // Bundle broadcastData = getIntent().getExtras();
        //calling = broadcastData.getString("calling");
       // Log.e("calling in ForgotPPass", calling);

        // Create Object of Dialog class
        final Dialog forgot_dial = new Dialog(this);
        // Set GUI of login screen
        forgot_dial.setContentView(R.layout.forgot_dialog);
        forgot_dial.show();

        forgot_dial.setTitle("Forgot password");
        Log.e("Set conent view:","forgot_dialog");
        // Init button of login GUI
        Button btnProfileForgot = (Button) forgot_dial.findViewById(R.id.btnProfileForgot);
        ImageButton profile_cancel = (ImageButton) forgot_dial.findViewById(R.id.profile_cancel);
       // Button btnForgot = (Button) login.findViewById(R.id.btnForgot);
        final EditText userNumber = (EditText) forgot_dial.findViewById(R.id.userNumber);
        //  final EditText txtPassword = (EditText) login.findViewById(R.id.txtPassword);
        Log.e("Found:","all references");

        // Attached listener for login GUI button
        btnProfileForgot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userNumber.getText().toString().trim().length() > 0) {

                    request = new StringRequest(Request.Method.POST, forgot_log, new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            try {
                                Log.e("Response in forgotPPass", response);
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Password sent to registered email address ", Toast.LENGTH_SHORT).show();
                                    forgot_dial.dismiss();
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Error processing request", Toast.LENGTH_SHORT).show();
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
                            hashMap.put("userNumber", userNumber.getText().toString());
                           // hashMap.put("password", txtPassword.getText().toString());
                           // hashMap.put("nfc_id", nfc_id);
                            Log.e("Hashmap in ForgotP_Pass", hashMap.toString());
                            return hashMap;
                        }
                    };

                    requestQueue.add(request);


                } else {
                    Toast.makeText(ForgotProfilePassword.this,
                            "Please enter phone number", Toast.LENGTH_LONG).show();

                }
            }
        });

        profile_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotProfilePassword.this, LoginActivity.class));
                finish();
            }
        });


    }

}
