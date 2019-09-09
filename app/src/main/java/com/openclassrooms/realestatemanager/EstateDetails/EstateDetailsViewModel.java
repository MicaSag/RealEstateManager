package com.openclassrooms.realestatemanager.EstateDetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Repositories.CurrentEstateDataRepository;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentDataRepository;
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
    @NonNull
    private MediatorLiveData<Estate> mCurrentEstate = new MediatorLiveData<>();

    public EstateDetailsViewModel(EstateDataRepository estateDataSource,
                               Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;

        // list of all Estates contained in the table
        @NonNull
        LiveData<List<Estate>> estates = mEstateDataSource.getEstates();

        // When CurrentRealEstateAgent change
        mCurrentEstate.addSource(CurrentRealEstateAgentDataRepository.
                        getInstance().getCurrentRealEstateAgent_Id(),
                new Observer<Long>() {
                    @Override
                    public void onChanged(@Nullable Long agent_Id) {
                        Log.d(TAG, "1:onChanged() called with: agentId = [" + agent_Id + "]");
                        filterFirstEstateAgent(agent_Id, estates.getValue());
                    }
                });
        // When Estate List change
        mCurrentEstate.addSource(estates,
                new Observer<List<Estate>>() {
                    @Override
                    public void onChanged(@Nullable List<Estate> estates) {
                        Log.d(TAG, "2:onChanged() called with: estates.size() = [" + estates.size() + "]");
                        filterEstate(CurrentRealEstateAgentDataRepository.getInstance()
                                .getCurrentRealEstateAgent_Id().getValue(), estates);
                    }
                });
        // When CurrentEstate_Id change
        mCurrentEstate.addSource(CurrentEstateDataRepository.getInstance().getCurrentEstate_Id(),
                new Observer<Long>() {
                    @Override
                    public void onChanged(@Nullable Long estate_Id) {
                        Log.d(TAG, "3:onChanged() called with: estate_Id = [" + estate_Id + "]");
                        filterEstate(estate_Id, estates.getValue());
                    }
                });
    }

    // Retrieves only the first Estate of the agent passed in parameter
    private void filterFirstEstateAgent(Long realEstateAgent_Id, List<Estate> estates) {
        Log.d(TAG, "filterEstate: ");

        if (realEstateAgent_Id == null || estates == null) {
            return;
        }

        for (Estate estate : estates) {

            Log.d(TAG, "filterEstate: estate.getRealEstateAgent_Id()    = "+estate.getRealEstateAgent_Id());
            Log.d(TAG, "filterEstate: realEstateAgent_Id                = "+realEstateAgent_Id);

            if (estate.getRealEstateAgent_Id() == realEstateAgent_Id) {
                mCurrentEstate.setValue(estate);
                break;
            }
        }
    }

    // Returns the Estate having the identifier passed in parameter
    private void filterEstate(Long estate_Id, List<Estate> estates) {
        Log.d(TAG, "filterEstate: ");

        if (estate_Id == null || estates == null) {
            return;
        }

        for (Estate estate : estates) {

            Log.d(TAG, "filterEstate: estate.getEstate_Id()    = "+estate.getEstate_Id());
            Log.d(TAG, "filterEstate: estate_Id                = "+estate_Id);

            if (estate.getEstate_Id() == estate_Id) {
                mCurrentEstate.setValue(estate);
                break;
            }
        }
    }

    @Nullable
    public LiveData<Estate> getCurrentEstate() {
        Log.d(TAG, "getCurrentEstate: ");
        return mCurrentEstate;
    }
}


