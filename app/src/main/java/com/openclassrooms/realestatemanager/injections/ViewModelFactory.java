package com.openclassrooms.realestatemanager.injections;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.models.views.CreateEstateViewModel;
import com.openclassrooms.realestatemanager.models.views.DetailsEstateViewModel;
import com.openclassrooms.realestatemanager.models.views.SearchEstateViewModel;
import com.openclassrooms.realestatemanager.models.views.UpdateEstateViewModel;
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
        if (modelClass.isAssignableFrom(DetailsEstateViewModel.class)) {
            return (T) new DetailsEstateViewModel(estateDataSource);
        }
        if (modelClass.isAssignableFrom(RealEstateManagerViewModel.class)) {
            return (T) new RealEstateManagerViewModel(estateDataSource, realEstateAgentDataSource);
        }
        if (modelClass.isAssignableFrom(CreateEstateViewModel.class)) {
            return (T) new CreateEstateViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(UpdateEstateViewModel.class)) {
            return (T) new UpdateEstateViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(SearchEstateViewModel.class)) {
            return (T) new SearchEstateViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
