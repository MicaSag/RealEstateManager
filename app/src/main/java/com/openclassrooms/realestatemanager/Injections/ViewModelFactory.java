package com.openclassrooms.realestatemanager.Injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.PropertyList.PropertyViewModel;
import com.openclassrooms.realestatemanager.Repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.Repositories.RealEstateAgentDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PropertyDataRepository propertyDataSource;
    private final RealEstateAgentDataRepository realEstateAgentDataSource;
    private final Executor executor;

    public ViewModelFactory(PropertyDataRepository itemDataSource, RealEstateAgentDataRepository userDataSource, Executor executor) {
        this.propertyDataSource = itemDataSource;
        this.realEstateAgentDataSource = userDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PropertyViewModel.class)) {
            return (T) new PropertyViewModel(propertyDataSource, realEstateAgentDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
