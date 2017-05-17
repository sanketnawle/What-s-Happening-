
package com.example.sanketnawle.whatshappening;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


class Config {
    public static final String DATA_URL = "http://whatshappening.16mb.com/getData.php?id=";
    public static final String DATA_URL2 = "http://whatshappening.16mb.com/removeData.php?id=";
    public static final String DATA_URL3 = "http://whatshappening.16mb.com/addData.php?id=";
    public static final String KEY_UNAME = "user_name";
    public static final String KEY_UPHONE = "user_phone";
    public static final String KEY_STATUS = "status";
    public static final String KEY_FPHONE = "friend_phone";
    public static final String JSON_ARRAY = "result";
}


public class Requests extends AppCompatActivity {

    //private EditText editTextId;
    //private Button buttonGet;
    //private TextView textViewResult;
    ArrayList<R_SelectRequest> RSelectRequests;
    List<R_SelectRequest> temp;

    ListView listView;
    R_RequestAdapter adapter;
    public String userNumber;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Log.e("Entered Requests: ","OnCreate");
        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");

       /* R_SelectRequest s = new R_SelectRequest();
        userNumber= s.getUserNumber(); */

        Log.e("User no from onCreate", userNumber);
        RSelectRequests = new ArrayList<R_SelectRequest>();
        listView = (ListView) findViewById(R.id.request_list);

        loading = ProgressDialog.show(Requests.this, "Please wait...", "Fetching...", false, false);

        String url = Config.DATA_URL + userNumber;
        Log.e("URL",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.e("Response in requests: ",response);
                String responsenull = "{"+"\"result\":"+"[]"+"}";
                if(response.equals(responsenull)) {
                    setContentView(R.layout.nothing_to_show);
                 }
                showJSON(response);
              /*  R_SelectRequest data = RSelectRequests.get(0);
                Log.e("data in Requests", data.toString()); */
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(Requests.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Requests.this);
        requestQueue.add(stringRequest);





        // Select item on listclick
      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.e("search", "here---------------- listener");

                R_SelectRequest data = RSelectRequests.get(i);

            }
        }); */

        listView.setFastScrollEnabled(true);


        //  LoadRequest loadRequest = new LoadRequest();
        // loadRequest.execute();

    }

    private void showJSON(String response) {
        String user_phone = "";
        String user_name = "";
        String status = "";
        String friend_phone="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            Log.e("JSONArray:",result.toString());
            for(int i=0;i<result.length();i++) {
                JSONObject requestData = result.getJSONObject(i);
                user_name = requestData.getString(Config.KEY_UNAME);
                user_phone = requestData.getString(Config.KEY_UPHONE);
                friend_phone = requestData.getString(Config.KEY_FPHONE);
                status = requestData.getString(Config.KEY_STATUS);

                Log.e("UserName: ", user_name);
                Log.e("User Phone: ", user_phone);
                Log.e("Friend Phone: ", friend_phone);
                Log.e("Status: ", status);


                R_SelectRequest RSelectRequest = new R_SelectRequest();
                // RSelectRequest.setThumb(bit_thumb);
                RSelectRequest.setName(user_name);
                RSelectRequest.setPhone(user_phone);
                RSelectRequest.setUserNumber(userNumber);
                Log.e("userNo in Requests: ",userNumber);
                // RSelectRequest.setEmail(id);
                RSelectRequests.add(RSelectRequest);

                adapter = new R_RequestAdapter(RSelectRequests, Requests.this);
                listView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    }




   /* class LoadRequest extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

           // String id = userNumber;

            loading = ProgressDialog.show(Requests.this, "Please wait...", "Fetching...", false, false);

            String url = Config.DATA_URL + userNumber;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error:","OnErrorResponse");
                            Toast.makeText(Requests.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(Requests.this);
            requestQueue.add(stringRequest);
            return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter = new R_RequestAdapter(RSelectRequests, Requests.this);
        listView.setAdapter(adapter);

        // Select item on listclick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.e("search", "here---------------- listener");

                R_SelectRequest data = RSelectRequests.get(i);

            }
        });

        listView.setFastScrollEnabled(true);
    }

}

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void showJSON(String response) {
        String name = "";
        String phone = "";
        String status = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject requestData = result.getJSONObject(0);
            name = requestData.getString(Config.KEY_NAME);
            phone = requestData.getString(Config.KEY_PHONE);
            status = requestData.getString(Config.KEY_STATUS);

            Log.e("Name: ", name);
            Log.e("Phone: ", phone);
            Log.e("Status: ", status);

            R_SelectRequest selectRequest = new R_SelectRequest();
            // selectRequest.setThumb(bit_thumb);
            selectRequest.setName(name);
            selectRequest.setPhone(phone);
            // selectRequest.setEmail(id);
            RSelectRequests.add(selectRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


} */







