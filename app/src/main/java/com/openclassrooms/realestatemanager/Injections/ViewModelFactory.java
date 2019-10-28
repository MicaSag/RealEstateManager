package com.openclassrooms.realestatemanager.Injections;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Models.views.EstateCreateViewModel;
import com.openclassrooms.realestatemanager.Models.views.EstateDetailsViewModel;
import com.openclassrooms.realestatemanager.Models.views.EstateListViewModel;
import com.openclassrooms.realestatemanager.Models.views.EstateUpdateViewModel;
import com.openclassrooms.realestatemanager.Models.views.MapViewModel;
import com.openclassrooms.realestatemanager.Models.views.RealEstateAgentViewModel;
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
        if (modelClass.isAssignableFrom(EstateCreateViewModel.class)) {
            return (T) new EstateCreateViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(EstateUpdateViewModel.class)) {
            return (T) new EstateUpdateViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(estateDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
