package com.openclassrooms.realestatemanager.injections;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.models.views.EstateCreateViewModel;
import com.openclassrooms.realestatemanager.models.views.EstateDetailsViewModel;
import com.openclassrooms.realestatemanager.models.views.EstateSearchViewModel;
import com.openclassrooms.realestatemanager.models.views.EstateUpdateViewModel;
import com.openclassrooms.realestatemanager.models.views.MapViewModel;
import com.openclassrooms.realestatemanager.models.views.RealEstateManagerViewModel;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentDataRepository;

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
        if (modelClass.isAssignableFrom(EstateDetailsViewModel.class)) {
            return (T) new EstateDetailsViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(RealEstateManagerViewModel.class)) {
            return (T) new RealEstateManagerViewModel(estateDataSource, realEstateAgentDataSource);
        }
        if (modelClass.isAssignableFrom(EstateCreateViewModel.class)) {
            return (T) new EstateCreateViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(EstateUpdateViewModel.class)) {
            return (T) new EstateUpdateViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(EstateSearchViewModel.class)) {
            return (T) new EstateSearchViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
