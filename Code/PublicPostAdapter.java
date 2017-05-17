package com.example.sanketnawle.whatshappening;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

public class PublicPostAdapter extends BaseAdapter{

    private RequestQueue requestQueue;
    private static final String log = "http://whatshappening.16mb.com/add_contact.php";
    private StringRequest request;
    public List<SelectPublicPost> _data;
    private ArrayList<SelectPublicPost> arraylist;
    public String userNumber;
    Context _c;
    ViewHolder v;


    public PublicPostAdapter(List<SelectPublicPost> selectPublicPosts, Context context) {
        _data = selectPublicPosts;
        _c = context;
        this.arraylist = new ArrayList<SelectPublicPost>();
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
        Log.e("Entered SUAdapter: ", "Oncreate");

        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.public_post_info, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();
        v.ename = (TextView) view.findViewById(R.id.eventName);

        final SelectPublicPost data = (SelectPublicPost) _data.get(i);
        v.ename.setText(data.getEventName());

        Log.e("Entered SUAdapter", "Set the View");




        v.event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String friend_no = data.getFriendPhone().toString();
                final String event_name = data.getEventName().toString();
                final String event_details = data.getEventDetails().toString();
                final String event_location = data.getEventLocation().toString();
                final String event_time = data.getEventTime().toString();
               // final String event_name = data.getFriendPhone().toString();


                Intent intent = new Intent(_c, P_EventDetails.class);
                intent.putExtra("Event name",event_name );
                intent.putExtra("Event detalis",event_details );
                intent.putExtra("Event location",event_location );
                intent.putExtra("Event time",event_time );

                _c.startActivity(intent);


            }


        });

        view.setTag(data);
        return view;
    }






    static class ViewHolder {
        TextView ename;
        RelativeLayout event;

    }

}
