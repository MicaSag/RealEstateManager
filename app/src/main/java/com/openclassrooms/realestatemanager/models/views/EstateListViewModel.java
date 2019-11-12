package com.openclassrooms.realestatemanager.models.views;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.repositories.CurrentEstateListDataRepository;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateListViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateListViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    public EstateListViewModel(EstateDataRepository estateDataSource,
                               Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;

        // list of all Estates contained in the table
        final SimpleSQLiteQuery simpleQuery =
                new SimpleSQLiteQuery("SELECT * FROM Estate WHERE realEstateAgent_Id =? ",
                        new Object[]{1});

        LiveData<List<Estate>> lEstate = mEstateDataSource.getEstates(simpleQuery);
        // Initialize Current Estate List
        lEstate.observeForever(new Observer<List<Estate>>() {
            @Override
            public void onChanged(List<Estate> estates) {
                Log.d(TAG, "onChanged: ");

                // Unsubscribe the Observer
                lEstate.removeObserver(this);

                CurrentEstateListDataRepository.getInstance().setCurrentEstateList(estates);
            }
        });
    }
}

