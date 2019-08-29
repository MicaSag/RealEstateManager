package com.openclassrooms.realestatemanager.PropertyList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.Repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.Repositories.RealEstateAgentDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = PropertyViewModel.class.getSimpleName();

    // REPOSITORIES
    private final PropertyDataRepository mPropertyDataSource;
    private final RealEstateAgentDataRepository mRealEstateAgentDataSource;
    private final Executor mExecutor;

    // DATA
    @Nullable
    private LiveData<RealEstateAgent> mCurrentRealEstateAgent;

    public PropertyViewModel(PropertyDataRepository propertyDataSource,
                             RealEstateAgentDataRepository realEstateAgentDataSource,
                             Executor executor) {
        mPropertyDataSource = propertyDataSource;
        mRealEstateAgentDataSource = realEstateAgentDataSource;
        mExecutor = executor;
    }

    public void init(long realEstateAgent_Id) {
        Log.d(TAG, "init: ");
        if (mCurrentRealEstateAgent != null) {
            return;
        }
        mCurrentRealEstateAgent = mRealEstateAgentDataSource.getRealEstateAgent(realEstateAgent_Id);
    }

    // ----------------------
    // FOR REAL ESTATE AGENT
    // ----------------------

    public LiveData<RealEstateAgent> getCurrentRealEstateAgent() {
        return mCurrentRealEstateAgent;
    }

    public LiveData<RealEstateAgent> getRealEstateAgent(long realEstateAgent_Id) {
        return mRealEstateAgentDataSource.getRealEstateAgent(realEstateAgent_Id);
    }

    public void createRealEstateAgent(RealEstateAgent realEstateAgent) {
        mExecutor.execute(() -> {
            mRealEstateAgentDataSource.createRealEstateAgent(realEstateAgent);
        });
    }

    public void deleteRealEstateAgent(long realEstateAgent_Id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRealEstateAgentDataSource.deleteRealEstateAgent(realEstateAgent_Id);
            }
        });
    }

    // -------------
    // FOR PROPERTY
    // -------------

    public LiveData<List<Property>> getPropertys(long realEstateAgent_Id) {
        return mPropertyDataSource.getPropertys(realEstateAgent_Id);
    }

    public void createProperty(Property property) {
        mExecutor.execute(() -> {
            mPropertyDataSource.createProperty(property);
        });
    }

    public void deleteProperty(long property_Id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPropertyDataSource.deleteProperty(property_Id);
            }
        });
    }

    public void deletePropertys(long realEstateAgent_Id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPropertyDataSource.deletePropertys(realEstateAgent_Id);
            }
        });
    }

    public void updateProperty(Property property) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPropertyDataSource.updateProperty(property);
            }
        });
    }
}

