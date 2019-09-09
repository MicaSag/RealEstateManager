package com.openclassrooms.realestatemanager.Injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.EstateDetails.EstateDetailsViewModel;
import com.openclassrooms.realestatemanager.EstateList.EstateListViewModel;
import com.openclassrooms.realestatemanager.RealEstateAgent.RealEstateAgentViewModel;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.Repositories.RealEstateAgentDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final EstateDataRepository estateDataSource;
    private final RealEstateAgentDataRepository realEstateAgentDataSource;
    private final Executor executor;

    public ViewModelFactory(EstateDataRepository estateDataSource, RealEstateAgentDataRepository agentDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.realEstateAgentDataSource = agentDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstateListViewModel.class)) {
            return (T) new EstateListViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(EstateDetailsViewModel.class)) {
            return (T) new EstateDetailsViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(RealEstateAgentViewModel.class)) {
            return (T) new RealEstateAgentViewModel(realEstateAgentDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
