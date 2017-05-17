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
import java.util.Map;


public class IFriendsAdapter extends BaseAdapter{


    public List<SelectIFriend> _data;
    private ArrayList<SelectIFriend> arraylist;
    public String userNumber,id;
    private StringRequest request;
    private RequestQueue requestQueue;
    Context _c;
    ViewHolder v;
    RoundImage roundedImage;
    public static final String log = "http://whatshappening.16mb.com/send_i_friends.php";

    public IFriendsAdapter(List<SelectIFriend> selectIFriends, Context context) {
        _data = selectIFriends;
        Log.e("_data in RequestAdapter",_data.toString());
        _c = context;
        Log.e("Contxt in ReqAdapter",_c.toString());
        this.arraylist = new ArrayList<SelectIFriend>();
        this.arraylist.addAll(_data);
        Log.e("ArrayList in ReqAdapter",arraylist.toString());
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

        Log.e("Entered:", "getView of IFriendsAdapter");
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.single_pfriend, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();

        v.add = (Button) view.findViewById(R.id.add);
        v.name = (TextView) view.findViewById(R.id.name);
        // v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final SelectIFriend data = (SelectIFriend) _data.get(i);
        v.name.setText(data.getName());
        v.phone.setText(data.getPhone());
        v.imageView.setImageBitmap(data.getThumb());
        Log.e("Name in IFriendsAdapter", data.getName());
        Log.e("Phone in PFriendsAdapt", data.getPhone());

        try {

            if (data.getThumb() != null) {
                v.imageView.setImageBitmap(data.getThumb());
            } else {
                v.imageView.setImageResource(R.drawable.ic_launcher);
            }
            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.ic_launcher); // Load default image
            roundedImage = new RoundImage(bm);
            v.imageView.setImageDrawable(roundedImage);
        } catch (OutOfMemoryError e) {
            // Add default picture
            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.ic_launcher));
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(_c);

        v.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               final Button add = (Button) view;


                final String phone_no = data.getPhone().toString();
                Log.e("Phone:SelectUserAdapter", phone_no);

                final String userNo2;
                userNo2 = data.getUserNumber();

                final String id2;
                id2 = data.getId();
                Log.e("ID in PFAdapter",id2);


                request = new StringRequest(Request.Method.POST, log, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Response in PFAdapter",response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(_c, "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                               // v.add.setText("Sent");
                                add.setText("Sent");
                                add.setClickable(false);

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

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {


                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("id", id2);
                       // hashMap.put("user_no", userNo2);
                        hashMap.put("friend_no", data.getPhone());
                        hashMap.put("friend_name",data.getName());
                        Log.e("Hashmap in public post",hashMap.toString());
                        return hashMap;

                    }
                };

                requestQueue.add(request);












            }

        });







        Log.e("Image Thumb", "--------------" + data.getThumb());

        view.setTag(data);
        return view;

    }

    static class ViewHolder {
        ImageView imageView;
        TextView name, phone;
        Button add;
    }

}
