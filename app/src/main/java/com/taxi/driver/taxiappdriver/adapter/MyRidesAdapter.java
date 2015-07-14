package com.taxi.driver.taxiappdriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.taxi.driver.taxiappdriver.R;

import java.util.ArrayList;

/**
 * Created by sharma on 9/7/15.
 */
public class MyRidesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public MyRidesAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MyRidesHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_my_rides, parent, false);
            holder = new MyRidesHolder();
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.list_item_my_rides_layout);
            convertView.setTag(holder);
        } else {
            holder = (MyRidesHolder) convertView.getTag();
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Yet to implement", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    private class MyRidesHolder {
        RelativeLayout layout;
    }
}
