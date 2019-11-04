package com.openclassrooms.realestatemanager.Models.views;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import java.util.concurrent.Executor;

public class EstateDetailsViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateDetailsViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

        public EstateDetailsViewModel(EstateDataRepository estateDataSource,
                               Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;

        estateDataSource.getEstate(1).observeForever(new Observer<Estate>() {
            @Override
            public void onChanged(Estate estate) {
                Log.d(TAG, "onChanged: estate_Id = "+estate.getEstate_Id());
            }
        });
    }

    public LiveData<Estate> getEstate(Long estate_Id) {
        Log.d(TAG, "getEstate: ");
        return mEstateDataSource.getEstate(estate_Id);
    }
}


