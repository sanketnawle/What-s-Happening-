package com.example.sanketnawle.whatshappening;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


class R_RequestAdapter extends BaseAdapter {

    //private RequestQueue requestQueue;
   // private static final String log = "http://whatshappening.16mb.com/add_contact.php";
    //private StringRequest request;
    public List<R_SelectRequest> _data;
    private ArrayList<R_SelectRequest> arraylist;
    public String userNumber;
    private StringRequest request;
    private RequestQueue requestQueue;
    Context _c;
    ViewHolder v;
    R_RoundImage roundedImage;
   // public static final String DATA_URL2 = "http://whatshappening.16mb.com/removeData.php?id=";

    public R_RequestAdapter(List<R_SelectRequest> RSelectRequests, Context context) {
        _data = RSelectRequests;
        Log.e("_data in R_RequestAdapter",_data.toString());
        _c = context;
        Log.e("Contxt in ReqAdapter",_c.toString());
        this.arraylist = new ArrayList<R_SelectRequest>();
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
        final int j=i;
        Log.e("Entered:", "getView of R_RequestAdapter");
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.custom_row, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();

        v.add = (Button) view.findViewById(R.id.add);
        v.reject = (Button) view.findViewById(R.id.reject);
        v.name = (TextView) view.findViewById(R.id.name);
        // v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final R_SelectRequest data = (R_SelectRequest) _data.get(i);
        v.name.setText(data.getName());
        v.phone.setText(data.getPhone());
        v.imageView.setImageBitmap(data.getThumb());
        Log.e("Name in R_RequestAdapter", data.getName());
        Log.e("Phone in R_RequestAdapter", data.getPhone());

        try {

            if (data.getThumb() != null) {
                v.imageView.setImageBitmap(data.getThumb());
            } else {
                v.imageView.setImageResource(R.drawable.image);
            }
            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.image); // Load default image
            roundedImage = new R_RoundImage(bm);
            v.imageView.setImageDrawable(roundedImage);
        } catch (OutOfMemoryError e) {
            // Add default picture
            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.image));
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(_c);

        v.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  final String requestName;
                requestName = data.getName();
                Log.e("User no in SUA", requestName); */

                final String phone_no = data.getPhone().toString();
                Log.e("Phone:R_SelectUserAdapter", phone_no);

                final String userNo2;
                userNo2 = data.getUserNumber();

                final String url = Config.DATA_URL2 + userNo2 + "&phone_no=" + phone_no;
                Log.e("URL in ReqAdapter", url);


                Intent intent = new Intent(_c,DeleteRequest.class);
                intent.putExtra("Friend no",phone_no);
                intent.putExtra("User no",userNo2);
                intent.putExtra("URL",url);
                _c.startActivity(intent);

            }

        });


        v.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  final String requestName;
                requestName = data.getName();
                Log.e("User no in SUA", requestName); */

                final String phone_no = data.getPhone().toString();
                Log.e("Phone:R_SelectUserAdapter", phone_no);

                final String userNo2;
                userNo2 = data.getUserNumber();

                final String url = Config.DATA_URL3 + userNo2 + "&phone_no=" + phone_no;
                Log.e("URL in ReqAdapter", url);


                Intent intent = new Intent(_c,AddRequest.class);
                intent.putExtra("Friend no",phone_no);
                intent.putExtra("User no",userNo2);
                intent.putExtra("URL",url);
                _c.startActivity(intent);

            }

        });











                Log.e("Image Thumb", "--------------" + data.getThumb());

                view.setTag(data);
                return view;

            }

            static class ViewHolder {
                ImageView imageView;
                TextView name, phone;
                Button add, reject;
            }

        }