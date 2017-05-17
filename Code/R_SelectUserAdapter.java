package com.example.sanketnawle.whatshappening;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class R_SelectUserAdapter extends BaseAdapter {

    private RequestQueue requestQueue;
    private static final String log = "http://whatshappening.16mb.com/add_contact.php";
    private StringRequest request;
    public List<R_SelectUser> _data;
    private ArrayList<R_SelectUser> arraylist;
    public String userNumber;
    Context _c;
    ViewHolder v;
    R_RoundImage roundedImage;




        public R_SelectUserAdapter(List<R_SelectUser> RSelectUsers, Context context) {
        _data = RSelectUsers;
        _c = context;
        this.arraylist = new ArrayList<R_SelectUser>();
        this.arraylist.addAll(_data);
    }



    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Log.e("Entered SUAdapter: ","Oncreate");

        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.contact_info, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();
        v.addContact = (Button) view.findViewById(R.id.addContact);
        v.title = (TextView) view.findViewById(R.id.name);
       // v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final R_SelectUser data = (R_SelectUser) _data.get(i);
        v.title.setText(data.getName());
        v.addContact.setText(data.getAddContact());
        v.phone.setText(data.getPhone());
        v.imageView.setImageBitmap(data.getThumb());
       // final String phone_no = v.phone.toString();

        Log.e("Entered SUAdapter","Set the View");

        // Set image if exists
        try {

            if (data.getThumb() != null) {
                v.imageView.setImageBitmap(data.getThumb());
            } else {
                v.imageView.setImageResource(R.drawable.image);
            }
            // Seting round image
            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.image); // Load default image
            roundedImage = new R_RoundImage(bm);
            v.imageView.setImageDrawable(roundedImage);
        } catch (OutOfMemoryError e) {
            // Add default picture
            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.image));
            e.printStackTrace();
        }

        Log.e("Image Thumb", "--------------" + data.getThumb());


        requestQueue = Volley.newRequestQueue(_c);

        // Set check box listener android
         v.addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   final Button addContact = (Button) view;


                    //TextView callContact = (TextView) view;
                final String userNumber;
               /* R_SelectUser s = new R_SelectUser();
                userNumber= s.getUserNumber();
                Log.e("User no in SUA",userNumber);
*/
                userNumber= data.getUserNumber();
                Log.e("User no in SUA",userNumber);

                   final  String phone_no = data.getPhone().toString();
                   Log.e("Phone:R_SUA", phone_no);
                //   Log.e("Userno in onClick",userNumber);
                 //  data.callContact(contact);

                request = new StringRequest(Request.Method.POST, log, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Entered:","OnResponse of SUA");
                            Log.e("String response",response);
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("JSON object","Created");

                           if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(_c, "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                               data.setAddContact("Sent");
                               addContact.setText("Sent");
                            } else {
                                Toast.makeText(_c, "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            Log.e("Error response Listener","problem");
                    }


                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Log.e("User no in hashmap", userNumber);
                        Log.e("Phone no in hashmap", phone_no);
                        Log.e("Created","hashmap");

                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("phone_no", phone_no);
                        hashMap.put("userNumber", userNumber);

                        return hashMap;

                    }
                };

                requestQueue.add(request);

            }
        });


        view.setTag(data);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        Log.e("Entered SUAdapter: ","filter");
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (R_SelectUser wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        Button addContact;
    }
}
