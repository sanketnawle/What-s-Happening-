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


public class YourEventAdapter extends BaseAdapter {


    public List<SelectYourEvent> _data;
    private ArrayList<SelectYourEvent> arraylist;
    public String userNumber;
    private StringRequest request;
    private RequestQueue requestQueue;
    Context _c;
    ViewHolder v;
    // public static final String DATA_URL2 = "http://whatshappening.16mb.com/removeData.php?id=";

    public YourEventAdapter(List<SelectYourEvent> selectYourEvents, Context context) {
        _data = selectYourEvents;
        Log.e("_data in RequestAdapter",_data.toString());
        _c = context;
        Log.e("Contxt in ReqAdapter",_c.toString());
        this.arraylist = new ArrayList<SelectYourEvent>();
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

        Log.e("Entered:", "getView of RequestAdapter");
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.single_your_event, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();

        v.name = (TextView) view.findViewById(R.id.name);
        v.date = (TextView) view.findViewById(R.id.date);
        v.clickEvent = (RelativeLayout)view.findViewById(R.id.clickEvent);

        final SelectYourEvent data = (SelectYourEvent) _data.get(i);
        v.name.setText(data.getEventName());
        v.date.setText(data.getEventDate()+" | "+data.getEventTime());


        Log.e("Ename in YEAdapter", data.getEventName());
        Log.e("EDate in YEAdapter", data.getEventDate());



        v.clickEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String event_name = data.getEventName().toString();
                final String event_details = data.getEventDetails().toString();
                final String event_location = data.getEventLocation().toString();
                final String event_time = data.getEventTime().toString();
                final String event_date = data.getEventDate().toString();
                final String event_id = data.getEventId().toString();

                Intent intent = new Intent(_c, YourEventDetails.class);
                intent.putExtra("Event name",event_name );
                intent.putExtra("Event details",event_details );
                intent.putExtra("Event location",event_location );
                intent.putExtra("Event time",event_time );
                intent.putExtra("Event date",event_date);
                intent.putExtra("userNumber",data.getUserNumber());
                intent.putExtra("Event id",event_id);
                _c.startActivity(intent);

            }

        });

                 view.setTag(data);
                 return view;

             }

             static class ViewHolder {
                 TextView name, date;
                 RelativeLayout clickEvent;

             }


         }
