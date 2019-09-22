package com.openclassrooms.realestatemanager.Controllers.Fragments;


import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.EstateList.EstateListAdapter;
import com.openclassrooms.realestatemanager.Models.views.EstateListViewModel;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Michaël SAGOT on 23/07/2019.
 */
public class EstateListFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = EstateListFragment.class.getSimpleName();

    // Declare EstateListViewModel
    private EstateListViewModel mEstatesListViewModel;

    //For Data
    // Declare list of property & Adapter
    private List<Estate> mEstates;
    private EstateListAdapter mEstateListAdapter;

    // Declare OnPropertyClick Interface
    private EstateListAdapter.OnEstateClick mOnPropertyClick;

    // For Design
    @BindView(R.id.fragment_list_estate_recycler_view) RecyclerView mRecyclerView;

    public EstateListFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static EstateListFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        return new EstateListFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estate_list, container, false);
        ButterKnife.bind(this, view);

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        mEstatesListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateListViewModel.class);

        // Call during UI creation
        this.configureRecyclerView();

        // Get and Observe Current Estates
        this.getCurrentEstates();

        return view;
    }
    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------
    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Reset list
        mEstates = new ArrayList<>();
        // Create adapter passing the list of users
        mEstateListAdapter = new EstateListAdapter(mEstates, Glide.with(this), mOnPropertyClick);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerView.setAdapter(mEstateListAdapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    // --------------------------------------------------------------------------------------------
    //                                        ACTIONS
    // --------------------------------------------------------------------------------------------
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnPropertyClick = (EstateListAdapter.OnEstateClick)context;
    }
    // ---------------------------------------------------------------------------------------------
    //                                           DATA
    // ---------------------------------------------------------------------------------------------
    // Get all Estates for CurrentRealEstateAgent
    private void getCurrentEstates() {
        Log.d(TAG, "getEstates: ");
        mEstatesListViewModel.getCurrentEstates().observe(this, this::updateEstateList);
    }
    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    // Update the list of Estate
    private void updateEstateList(List<Estate> estates){
        Log.d(TAG, "updateEstateList: ");
        Log.d(TAG, "updateEstateList: estates.size = "+ estates.size());
        mEstateListAdapter.updateData(estates);
    }
}
