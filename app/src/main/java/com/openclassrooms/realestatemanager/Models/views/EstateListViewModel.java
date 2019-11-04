package com.openclassrooms.realestatemanager.Models.views;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Repositories.CurrentEstateListDataRepository;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

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
        SimpleSQLiteQuery simpleQuery =
                new SimpleSQLiteQuery("SELECT * FROM Estate WHERE realEstateAgent_Id =? ",
                        new Object[]{1});
        LiveData<List<Estate>> estates = mEstateDataSource.getEstates(simpleQuery);

        estates.observeForever(new Observer<List<Estate>>() {
            @Override
            public void onChanged(List<Estate> estates) {
                Log.d(TAG, "onChanged: ");
                CurrentEstateListDataRepository.getInstance().setCurrentEstateList(estates);
            }
        });
    }
}

