package com.openclassrooms.realestatemanager.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.repositories.CurrentEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

public class DetailsEstateViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = DetailsEstateViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;

    @NonNull
    private LiveData<Estate> mCurrentEstate;

    public DetailsEstateViewModel(EstateDataRepository estateDataSource) {
        mEstateDataSource = estateDataSource;

        mCurrentEstate = Transformations.switchMap(
                CurrentEstateDataRepository.getInstance().getCurrentEstate_Id(),
                this::getEstate);
    }

    // Get Estate of the DataBase
    public LiveData<Estate> getEstate(Long estate_Id) {
        Log.d(TAG, "getEstate: ");

        return mEstateDataSource.getEstate(estate_Id);
    }

    // Get Current Estate
    @NonNull
    public LiveData<Estate> getCurrentEstate() {
        return mCurrentEstate;
    }
}


