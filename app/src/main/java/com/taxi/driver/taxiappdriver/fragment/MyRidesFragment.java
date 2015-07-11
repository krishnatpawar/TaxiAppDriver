package com.taxi.driver.taxiappdriver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.taxi.driver.taxiappdriver.R;
import com.taxi.driver.taxiappdriver.adapter.MyRidesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRidesFragment extends Fragment {

    private ListView lvMyRides;
    private MyRidesAdapter adapter;

    public MyRidesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_rides, container, false);

        lvMyRides = (ListView) view.findViewById(R.id.listView_myrides);

        setAdapter();
        return view;
    }

    private void setAdapter() {
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<20; i++) {
            list.add("gv");
        }

        adapter = new MyRidesAdapter(getActivity(), list);
        lvMyRides.setAdapter(adapter);
    }

}
