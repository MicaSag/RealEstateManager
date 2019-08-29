package com.openclassrooms.realestatemanager.Controllers.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.PropertyList.PropertyViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.PropertyList.PropertyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Michaël SAGOT on 23/07/2019.
 */
public class ListFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = ListFragment.class.getSimpleName();

    //For Data
    // Declare list of property & Adapter
    private List<Property> mListProperty;
    private PropertyAdapter mAdapter;

    // Declare OnPropertyClick Interface
    private PropertyAdapter.OnPropertyClick mOnPropertyClick;

    // For Design
    @BindView(R.id.fragment_list_recycler_view) RecyclerView mRecyclerView;

    public ListFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static ListFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        return new ListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnPropertyClick = (PropertyAdapter.OnPropertyClick)context;
    }

    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        // Call during UI creation
        this.configureRecyclerView();

        return view;
    }

    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Reset list
        mListProperty = new ArrayList<>();
        // Create adapter passing the list of users
        mAdapter = new PropertyAdapter(mListProperty, Glide.with(this), mOnPropertyClick);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerView.setAdapter(mAdapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    // ---------------------------------------------------------------------------------------------
    //                                           DATA
    // ---------------------------------------------------------------------------------------------
    public PropertyAdapter getmAdapter() {
        return mAdapter;
    }

    // --------------------------------------------------------------------------------------------
    //                                        ACTIONS
    // --------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(List<Property> listProperty){
        mAdapter.updateData(listProperty);
    }
}
