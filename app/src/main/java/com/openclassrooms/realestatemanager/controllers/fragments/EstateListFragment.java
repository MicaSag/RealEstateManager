package com.openclassrooms.realestatemanager.controllers.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.estateList.EstateListAdapter;
import com.openclassrooms.realestatemanager.controllers.activities.RealEstateManagerActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.views.RealEstateManagerViewModel;
import com.openclassrooms.realestatemanager.repositories.CurrentEstateDataRepository;

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

    // Declare ViewModel
    private RealEstateManagerViewModel mRealEstateManagerViewModel;

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

        // Initialize ButterKnife
        ButterKnife.bind(this, view);

        // Configure ViewModel
        configureViewModel();

        // Call during UI creation
        this.configureRecyclerView();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {

        // User RealEstateManagerActivity ViewModel
        /*mRealEstateManagerViewModel = ViewModelProviders.of(getActivity())
                .get(RealEstateManagerViewModel.class);*/

        mRealEstateManagerViewModel = ((RealEstateManagerActivity) getActivity())
                .getRealEstateManagerViewModel();

        // Observe a change of Current Estates
        mRealEstateManagerViewModel.getCurrentEstates().observe(this, this::updateEstateList);

        // Observe a change of Current Estate
        CurrentEstateDataRepository.getInstance().getCurrentEstate_Id().observe(this, this::updateEstateList);
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
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    // Update the list of Estate
    private void updateEstateList(List<Estate> estates){

        if (estates != null) mEstateListAdapter.updateData(estates);
    }
    // Update the list of Estate
    private void updateEstateList(Long currentEstate_Id){

        mEstateListAdapter.notifyDataSetChanged();
    }
}