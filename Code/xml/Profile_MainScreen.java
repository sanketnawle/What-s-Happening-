package com.example.sanketnawle.whatshappening;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile_MainScreen extends Activity {

    public String userNumber,calling;
    private EditText editTextId;
    private Button buttonGetImage;
    private ImageView imageView;
    private String imageURL = "http://whatshappening.16mb.com/get_image.php";
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle broadcastData = getIntent().getExtras();
        userNumber = broadcastData.getString("userNumber");
        try {
            calling = broadcastData.getString("Calling");

            if (calling.equals("Home")) {
                setContentView(R.layout.activity_main_screen_profile);
            } else {
                setContentView(R.layout.activity_main_screen_friend_profile);
            }
        }
        catch (Exception e)
        {

        }

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = "http://whatshappening.16mb.com/get_profile.php?id="+userNumber;
        Log.e("URL", url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(Profile_MainScreen.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Profile_MainScreen.this);
        requestQueue.add(stringRequest);



    }



    private void showJSON(String response) {
        String email = "";
        String firstname="";
        String lastname = "";
        String mobile = "";
        String dob = "";
        String city = "";
        String country = "";
        String dob1= "";
        String image="";
        final String JSON_ARRAY = "result";


        try {
            JSONObject jsonObject = new JSONObject(response);
            Log.e("Response in YourEvents:",response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            Log.e("JSONArray:",result.toString());
            for(int i=0;i<result.length();i++) {
                JSONObject requestData = result.getJSONObject(i);
                email = requestData.getString("email");
                firstname = requestData.getString("firstname");
                lastname = requestData.getString("lastname");
                mobile = requestData.getString("mobile");
                dob = requestData.getString("dob");
                city= requestData.getString("city");
                country= requestData.getString("country");
                image= requestData.getString("image");


                Log.e("Email: ", email);
                Log.e("First Name: ", firstname);
                Log.e("lastname: ", lastname);
                Log.e("mobile: ", mobile);
                Log.e("dob: ", dob);
                Log.e("city: ", city);
                Log.e("image: ", image);

                TextView first_nameT = (TextView)findViewById(R.id.first_name);
                TextView last_nameT = (TextView)findViewById(R.id.last_name);
                TextView phone_noT = (TextView)findViewById(R.id.phone_no);
                TextView dobT = (TextView)findViewById(R.id.dob);
                TextView cityT = (TextView)findViewById(R.id.city);
                TextView countryT = (TextView)findViewById(R.id.country);
                TextView emailT = (TextView)findViewById(R.id.email);
                ImageView imageView = (ImageView)findViewById(R.id.user_image);

                if(image.equals(""))
                {
                    image= "http://whatshappening.16mb.com/user_images/default_profile.jpg";
                }


               /* Picasso.with(this).load(image).placeholder(R.mipmap.ic_launcher)
                        .noFade().into(imageView); */

                Picasso.with(this).load(image).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);

                loading.dismiss();

              //  Picasso.with(this).invalidate(image);


                first_nameT.setText(firstname);
                last_nameT.setText(lastname);
                phone_noT.setText(mobile);
                dobT.setText(dob);
                cityT.setText(city);
                countryT.setText(country);
                emailT.setText(email);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

 /*   private void getImage() {
        String id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void,Bitmap> {


            ImageView bmImage;
            ProgressDialog loading;

            public GetImage(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                loading.dismiss();
                bmImage.setImageBitmap(bitmap);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profile_MainScreen.this,"Downloading Image","Please wait...",true,true);
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                String url =  imageURL+ strings[0];
                Bitmap mIcon = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    mIcon = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
                return mIcon;
            }
        }

        GetImage gi = new GetImage(imageView);
        gi.execute(id);
    }
*/

    public void onEditClick(View view)
    {
        Intent intent = new Intent(this, UploadPic.class);
        intent.putExtra("userNumber", userNumber);
        getIntent().removeExtra("userNumber");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivityForResult(intent, 0);

    }


  /*  @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Home.class);
        intent.putExtra("userNumber",userNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }*/
}

