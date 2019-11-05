package com.openclassrooms.realestatemanager.models.views;

import android.util.Log;

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
        Log.d(TAG, "EstateListViewModel: ");
        mEstateDataSource = estateDataSource;
        mExecutor = executor;

        // list of all Estates contained in the table
        final SimpleSQLiteQuery simpleQuery =
                new SimpleSQLiteQuery("SELECT * FROM Estate WHERE realEstateAgent_Id =? ",
                        new Object[]{1});

        // Initialize Current Estate List
        mEstateDataSource.getEstates(simpleQuery).observeForever(new Observer<List<Estate>>() {
            @Override
            public void onChanged(List<Estate> estates) {
                Log.d(TAG, "onChanged: ");

                // Unsubscribe the Observer
                mEstateDataSource.getEstates(simpleQuery).removeObserver(this);

                CurrentEstateListDataRepository.getInstance().setCurrentEstateList(estates);
            }
        });
    }
}
