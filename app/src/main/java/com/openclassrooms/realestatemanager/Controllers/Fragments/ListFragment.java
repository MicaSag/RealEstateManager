package com.openclassrooms.realestatemanager.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.PropertyList.PropertyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Michaël SAGOT on 23/07/2019.
 */
public class ListFragment extends Fragment implements PropertyAdapter.Listener {

    // For debugging Mode
    private static final String TAG = ListFragment.class.getSimpleName();

    //For Data
    // Declare list of property & Adapter
    private List<Property> mListProperty;
    private PropertyAdapter adapter;

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

        // Allows management of a click on an element of the list
        this.configureOnClickRecyclerView();

        return view;
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Reset list
        mListProperty = new ArrayList<>();
        Property propertyOne = new Property();
        propertyOne.setType("COUCOU");
        mListProperty.add(propertyOne);
        Property propertyTwo = new Property();
        propertyTwo.setType("HEHEHE");
        mListProperty.add(propertyTwo);
        // Create adapter passing the list of users
        this.adapter = new PropertyAdapter(mListProperty, this);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    // -------------------
    // ACTIONS
    // -------------------
    @Override
    public void onClickDeleteButton(int position) {}

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_list_property)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                        // 1 - Get user from adapter
                        Property property = adapter.getProperty(position);
                        // 2 - Show result in a Toast
                        Toast.makeText(getContext(), "You clicked on property : "+property.getType(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<Property> listProperty){
        mListProperty.addAll(listProperty);
        adapter.notifyDataSetChanged();
    }
}
