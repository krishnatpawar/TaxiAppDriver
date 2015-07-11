package com.taxi.driver.taxiappdriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.taxi.driver.taxiappdriver.R;

import java.util.ArrayList;

/**
 * Created by sharma on 9/7/15.
 */
public class NewRidesAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<String> list;

    public NewRidesAdapter(Context context, ArrayList<String> list) {
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_new_rides, parent, false);
        }
        return convertView;
    }
}
