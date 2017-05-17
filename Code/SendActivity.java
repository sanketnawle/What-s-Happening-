package com.example.sanketnawle.whatshappening;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;


public class SendActivity extends Activity {

    private static final int DISCOVERABLE_REQUEST_CODE = 0x1;
    private boolean CONTINUE_READ_WRITE = true;
    private BluetoothDevice remoteDevice;
    public static String blocked [] = new String[100];
    public static int blockedcount=0;
    public static String broad1;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
      //  TextView broad = (TextView) findViewById(R.id.broadcastMessage);
      //  final String broad1 = broad.getText().toString();
        Bundle broadcastData = getIntent().getExtras();

        if (broadcastData == null) {
            Toast.makeText(SendActivity.this, "Nothing to broadcast", Toast.LENGTH_LONG).show();
            return;
        }

        String broadcast = broadcastData.getString("broadcast");
        final TextView broadcastMesssage = (TextView) findViewById(R.id.broadcastMessage);
        broadcastMesssage.setText(broadcast);

        TextView broad = (TextView) findViewById(R.id.broadcastMessage);
        broad1 = broad.getText().toString();

        //Always make sure that Bluetooth server is discoverable during listening...
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(discoverableIntent, DISCOVERABLE_REQUEST_CODE);
        remoteDevice = discoverableIntent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        android.util.Log.e("TrackingFlow", "Creating thread to start listening...");
        new Thread(reader).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            try {
                is.close();
                os.close();
                socket.close();
            } catch (Exception e) {
            }
            CONTINUE_READ_WRITE = false;

           /*Intent main = new Intent(this, BluetoothActivity.class);
            main.putExtra("send","send");

            startActivity(main);*/


        }
    }

    private BluetoothSocket socket;
    private InputStream is;
    private OutputStreamWriter os;

    public Runnable reader;

    {
        reader = new Runnable() {
            public void run() {



                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                UUID uuid = UUID.fromString("4e5d48e0-75df-11e3-981f-0800200c9a66");
               // android.util.Log.e("UUID:",remoteDevice.getUuids().toString());
                //UUID uuid = UUID.fromString(remoteDevice.getUuids().toString());


                try {

                    BluetoothServerSocket serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord("BLTServer", uuid);
                    android.util.Log.e("TrackingFlow", "Listening...");

                    socket = serverSocket.accept();
                    android.util.Log.e("TrackingFlow", "Socket accepted...");
                    is = socket.getInputStream();
                    os = new OutputStreamWriter(socket.getOutputStream());
                    new Thread(writter).start();
                    serverSocket.close();
                   // startActivity(new Intent(SendActivity.this, SendActivity.class));
                   // finish();
                    new Thread(reader).start();

                  /* int bufferSize = 1024;
                    int bytesRead = -1;
                    byte[] buffer = new byte[bufferSize];
                    //Keep reading the messages while connection is open...
                    while (CONTINUE_READ_WRITE) {
                        final StringBuilder sb = new StringBuilder();
                        bytesRead = is.read(buffer);
                        if (bytesRead != -1) {
                            String result = "";
                            while ((bytesRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
                                result = result + new String(buffer, 0, bytesRead - 1);
                                bytesRead = is.read(buffer);
                            }
                            result = result + new String(buffer, 0, bytesRead - 1);
                            sb.append(result);
                        }
                        android.util.Log.e("TrackingFlow", "Read: " + sb.toString());
                        //socket.close();

                        startActivity(new Intent(SendActivity.this, SendActivity.class));
                        finish();


                        //new Thread(reader).start();
                        //Show message on UIThread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SendActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
                            }


                        });
                    }
                    */

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        };


    }



       // public void goToMain(View view) {



    public Runnable writter = new Runnable() {
        @Override
        public void run() {
            // if (flag) {
           // int index = 0;
            //while(index<10){
            try {

                os.write(broad1+"\n");

               // blocked[blockedcount++]= remoteDevice.getName();

                os.flush();
                //Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //}
        // }
    };

}


