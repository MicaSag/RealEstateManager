package com.openclassrooms.realestatemanager.EstateDetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateDetailsViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateDetailsViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    @Nullable
    private LiveData<RealEstateAgent> mCurrentRealEstateAgent;

    public EstateDetailsViewModel(EstateDataRepository propertyDataSource,
                               Executor executor) {
        mEstateDataSource = propertyDataSource;
        mExecutor = executor;
    }

    // ------------
    //  FOR ESTATE
    // ------------

    // Get all estates from an real estate agent
    public LiveData<List<Estate>> getEstates(long realEstateAgent_Id) {
        return mEstateDataSource.getEstates(realEstateAgent_Id);
    }

    public LiveData<Estate> getEstate(long property_Id) {
        return mEstateDataSource.getEstate(property_Id);
    }

    public void createEstate(Estate estate) {
        mExecutor.execute(() -> {
            mEstateDataSource.createEstate(estate);
        });
    }

    public void deleteEstate(long estate_Id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mEstateDataSource.deleteEstate(estate_Id);
            }
        });
    }

    public void deleteEstates(long realEstateAgent_Id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mEstateDataSource.deleteEstates(realEstateAgent_Id);
            }
        });
    }

    public void updateEstate(Estate estate) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mEstateDataSource.updateEstate(estate);
            }
        });
    }
}


