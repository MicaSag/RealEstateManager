package com.openclassrooms.realestatemanager.models.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.repositories.CurrentRealEstateAgentDataRepository;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MapViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = MapViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    @NonNull
    private MediatorLiveData<List<Estate>> mCurrentEstates = new MediatorLiveData<>();

    public MapViewModel(EstateDataRepository estateDataSource,
                        Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;

        // list of all Estates contained in the table
        LiveData<List<Estate>> estates = mEstateDataSource.getEstates();

        // When CurrentRealEstateAgent change
        mCurrentEstates.addSource(CurrentRealEstateAgentDataRepository.
                        getInstance().getCurrentRealEstateAgent_Id(),
                new Observer<Long>() {
                    @Override
                    public void onChanged(@Nullable Long agentId) {
                        filterEstates(agentId, estates.getValue());
                    }
                });
        // When Estate List change
        mCurrentEstates.addSource(estates,
                new Observer<List<Estate>>() {
                    @Override
                    public void onChanged(@Nullable List<Estate> estates) {
                        filterEstates(CurrentRealEstateAgentDataRepository.getInstance()
                                .getCurrentRealEstateAgent_Id().getValue(), estates);
                    }
                });
    }

    // Retrieves only the Estate list of the agent specified as a parameter
    private void filterEstates(Long realEstateAgent_Id, List<Estate> estates) {
        ArrayList<Estate> localEstate = new ArrayList<>();

        if (realEstateAgent_Id == null || estates == null) {
            return;
        }

        for (Estate estate : estates) {
            if (estate.getRealEstateAgent_Id() == realEstateAgent_Id) {
                localEstate.add(estate);
            }
        }

        mCurrentEstates.setValue(localEstate);
    }

    // Get current Estate List
    public LiveData<List<Estate>> getCurrentEstates() {

        return mCurrentEstates;
    }
}
