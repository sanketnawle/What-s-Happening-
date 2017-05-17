package com.example.sanketnawle.whatshappening;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected String ipc = "27.106";
    protected char[] ipC;
    protected char[] ipA;
    protected int n=0;
    public String userNumber;
    private ImageView imageView;
    private String imageURL = "http://whatshappening.16mb.com/get_image.php";
    int delay = 1000; // delay for 1 sec.
    int period = 10000; //10sec
    int r;
    Context c;
    Boolean logged;
  //  String uid="9768632717";
    String Noti;
    Notification notification;
    SessionManager session;
    public Boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());

        Bundle broadcastData = getIntent().getExtras();
        if(broadcastData!=null) {
            userNumber = broadcastData.getString("userNumber");
        }
        else
        {
            HashMap<String, String> user = session.getUserDetails();

            // name
            userNumber = user.get(SessionManager.KEY_PASSWORD);

            // email
            String email   = user.get(SessionManager.KEY_EMAIL);
        }




        logged=session.checkLogin();

        if(!logged)
        {
            finish();
            return;
        }

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_PASSWORD);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        ipC = ipc.toCharArray();
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String url = "http://whatshappening.16mb.com/get_profile.php?id="+userNumber;
        Log.e("URL", url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response in home: ",response);
                showJSON(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", "OnErrorResponse");
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        requestQueue.add(stringRequest);

    n= getDeviceIpAddress();

    if(n==1) {
        Timer timer = new Timer();
        c = this;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                connected=true;
                new TableSize(c).execute("New Event", "");
                // Notify("You've received new message","You've received new message");
            }
        }, delay, period);
    }
        else{
        Toast.makeText(Home.this,"Please connect to the wifi network", Toast.LENGTH_LONG).show();
    }

    }


    public int getDeviceIpAddress() {
        try {
            //Loop through all the network interface devices
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface
                    .getNetworkInterfaces(); enumeration.hasMoreElements();) {
                NetworkInterface networkInterface = enumeration.nextElement();
                //Loop through all the ip addresses of the network interface devices
                for (Enumeration<InetAddress> enumerationIpAddr = networkInterface.getInetAddresses(); enumerationIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumerationIpAddr.nextElement();
                    //Filter out loopback address and other irrelevant ip addresses
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getAddress().length == 4) {
                        //Print the device ip address in to the text view
                        String checkIP = inetAddress.getHostAddress();
                        //ip.setText(inetAddress.getHostAddress());
                        ipA = checkIP.toCharArray();
                        for(int i=0;i<ipC.length;i++){
                            if(ipA[i]!=ipC[i])
                            {
                                n=8;

                            }else{
                                n=1;
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("ERROR:", e.toString());
        }

        return n;
    }



    private void Notify(String notificationTitle, String notificationMessage){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher,"New Message", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this,NotificationView.class);
        notificationIntent.putExtra("uid", userNumber);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
     //   notification.setLatestEventInfo(Home.this, notificationTitle,notificationMessage, pendingIntent);
      //  notificationManager.notify(9999, notification);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this);
        notification = builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher).setTicker("New message").setWhen(System.currentTimeMillis())
                .setAutoCancel(true).setContentTitle(notificationTitle)
                .setContentText(notificationMessage).build();
        notificationManager.notify(9999, notification);
    }

    public class TableSize  extends AsyncTask<String,Void,String> {

        private Context context;


        //flag 0 means get and 1 means post.(By default it is get.)
        public TableSize(Context context) {
            this.context = context;
        }

        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... arg) {

            try{
                String link = "http://whatshappening.16mb.com/event.php?uid="+userNumber;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }  }


        @Override
        protected void onPostExecute(String result){
            r = Integer.parseInt(result);
            if(r>0){
                if(r>1)
                    Noti = r+" new events";
                else
                    Noti = r+" new event";

                Notify(Noti,"Click For Detail");
            }
        }
    }

    private void showJSON(String response) {
        String email = "";
        String firstname = "";
        String lastname = "";
        String fullname ="";
        String image = "";
        final String JSON_ARRAY = "result";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            Log.e("JSONArray:", result.toString());
            for (int i = 0; i < result.length(); i++) {
                JSONObject requestData = result.getJSONObject(i);
                email = requestData.getString("email");
                firstname = requestData.getString("firstname");
                lastname = requestData.getString("lastname");
                image = requestData.getString("image");
                fullname = firstname+" "+lastname;

                Log.e("Email: ", email);
                Log.e("First Name: ", firstname);
                Log.e("lastname: ", lastname);
                Log.e("Full name",fullname);
                Log.e("image: ", image);

                TextView first_nameT = (TextView) findViewById(R.id.user_name);
                TextView emailT = (TextView) findViewById(R.id.user_email);
                ImageView imageView = (ImageView) findViewById(R.id.user_image);

                if (image.equals("")) {
                    image = "http://whatshappening.16mb.com/user_images/default_profile.jpg";
                }

                Picasso.with(this).load(image).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);
                first_nameT.setText(fullname);
                emailT.setText(email);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_requests) {

            Intent intent= new Intent(Home.this,Requests.class);
            intent.putExtra("userNumber",userNumber);
            startActivity(intent);

        } else if (id == R.id.nav_friends) {

            Intent send = new Intent(this, Friends.class);
            send.putExtra("userNumber", userNumber);
            startActivity(send);

        } else if (id == R.id.nav_alarm) {
            startActivity(new Intent(this,AlarmActivity.class));

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(this,NewsActivity.class));
        } else if (id == R.id.nav_logout) {
            session.logoutUser();
        }
        else if (id == R.id.nav_profile) {
            Intent send = new Intent(this, Profile_MainScreen.class);
            send.putExtra("userNumber", userNumber);
            send.putExtra("Calling","Home");
            startActivity(send);
        }else if (id == R.id.change_password) {

            Intent send = new Intent(this, ChangeProfilePassword.class);
            send.putExtra("userNumber", userNumber);
            startActivity(send);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onPublicPost(View view)
    {
        Intent intent= new Intent(Home.this,P_MainScreen.class);
        intent.putExtra("userNumber",userNumber);
        startActivity(intent);

    }


    public void onInvitation(View view)
    {
        Intent intent= new Intent(Home.this,MainScreen.class);
        intent.putExtra("userNumber",userNumber);
        startActivity(intent);

    }


    public void onAreaWise(View view) {
            Intent intent = new Intent(Home.this, AreaWiseHome.class);
            intent.putExtra("userNumber", userNumber);
            intent.putExtra("connected",connected);
            startActivity(intent);
        }


}
