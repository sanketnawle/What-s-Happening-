package com.example.sanketnawle.whatshappening;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    public EditText userNumber, password;
    private Button sign_in_registermain;
    private Button sign_in_login;
    private RequestQueue requestQueue;
    private static final String log = "http://whatshappening.16mb.com/user_control.php";
    private StringRequest request;
    SessionManager session;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNumber = (EditText) findViewById(R.id.phone_no);
        password = (EditText) findViewById(R.id.password);
        sign_in_login = (Button) findViewById(R.id.email_sign_in_button);
        sign_in_registermain = (Button) findViewById(R.id.email_register_button);

        session = new SessionManager(getApplicationContext());

        requestQueue = Volley.newRequestQueue(this);

        sign_in_login.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, log, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                session.createLoginSession(userNumber.getText().toString(), password.getText().toString());
                                Intent intent = new Intent(LoginActivity.this,Home.class);
                                intent.putExtra("userNumber",userNumber.getText().toString());
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
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
                        hashMap.put("phone_no", userNumber.getText().toString());
                        hashMap.put("password", password.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });


        sign_in_registermain.setOnClickListener(new View.OnClickListener()

                                            {
                                                @Override
                                                public void onClick(View view) {
                                                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                                                }
                                            }

        );

    }

    public void onForgotPassword(View view)
    {

        Log.e("Entered: ", "onForgotPassword");
        Intent intent = new Intent(LoginActivity.this,ForgotProfilePassword.class);
        intent.putExtra("calling", "Login");
        startActivity(intent);
    }


    @Override
    public void onBackPressed()
    {
        //finish();
        System.exit(1);
        super.onBackPressed();
    }


}
       // public void registerActivity() {


        //startActivity(new Intent(getApplicationContext(), RegisterActivity.class));


   // }



