package com.example.sanketnawle.whatshappening;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {
    public EditText email, password;
    private EditText firstname, lastname, mobile, city, country;
    private TextView dob;
    private Button sign_in_register;
    private Button pPickDate;
    private int pYear;
    private int pMonth;
    private int pDay;
    DatePickerDialog.OnDateSetListener pDateSetListener;
    /**
     * This integer will uniquely define the dialog to be used for displaying date picker.
     */
    static final int DATE_DIALOG_ID = 0;


    private RequestQueue requestQueue;
    private static final String log = "http://whatshappening.16mb.com/user_reg.php";
    private StringRequest request;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        requestQueue = Volley.newRequestQueue(this);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        //  male = (RadioButton) findViewById(R.id.maleRadioButton);
        // female = (RadioButton) findViewById(R.id.femaleRadioButton);
        dob = (TextView) findViewById(R.id.dob);
        mobile = (EditText) findViewById(R.id.mobile);
        city = (EditText) findViewById(R.id.city);
        country = (EditText) findViewById(R.id.country);

        pPickDate = (Button) findViewById(R.id.pickDate);

        sign_in_register = (Button) findViewById(R.id.register_button);

        pPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        /** Display the current date in the TextView */
        updateDisplay();


    pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                }
            };



        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, log, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, Home.class);
                                intent.putExtra("userNumber", mobile.getText().toString());
                                startActivity(intent);
                            } else if (jsonObject.names().get(0).equals("exists")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("exists"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                            } else {

                                Toast.makeText(RegisterActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
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
                        hashMap.put("email", email.getText().toString());
                        hashMap.put("password", password.getText().toString());
                        hashMap.put("firstname", firstname.getText().toString());
                        hashMap.put("lastname", lastname.getText().toString());
                        // hashMap.put("male", male.getText().toString());
                        //hashMap.put("female", female.getText().toString());
                        hashMap.put("mobile", mobile.getText().toString());
                        hashMap.put("dob", dob.getText().toString());
                        hashMap.put("city", city.getText().toString());
                        hashMap.put("country", country.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });


    }


    private void updateDisplay() {
        dob.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pMonth + 1).append("/")
                        .append(pDay).append("/")
                        .append(pYear).append(" "));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }

}