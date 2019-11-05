package com.openclassrooms.realestatemanager.models.views;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.repositories.CurrentEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

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
    }

    // Get Estate of the DataBase
    public LiveData<Estate> getEstate(Long estate_Id) {
        Log.d(TAG, "getEstate: ");

        return mEstateDataSource.getEstate(estate_Id);
    }

    // Get Current Estate
    public LiveData<Estate> getCurrentEstate() {
        Log.d(TAG, "getCurrentEstate: ");

         return Transformations.switchMap(
                CurrentEstateDataRepository.getInstance().getCurrentEstate_Id(),
                longResult -> {
                    return getEstate(longResult);
                });
    }
}


