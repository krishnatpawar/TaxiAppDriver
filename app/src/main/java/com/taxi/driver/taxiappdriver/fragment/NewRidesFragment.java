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
import com.taxi.driver.taxiappdriver.adapter.NewRidesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRidesFragment extends Fragment {

    private ListView lvNewRides;
    private NewRidesAdapter adapter;

    public NewRidesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_rides, container, false);
        lvNewRides = (ListView) view.findViewById(R.id.listView_new_rides);
        setAdapter();
        return view;
    }



    private void setAdapter() {
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<20; i++) {
            list.add("gv");
        }

        adapter = new NewRidesAdapter(getActivity(), list);
        lvNewRides.setAdapter(adapter);
    }


}
