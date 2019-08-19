package com.openclassrooms.realestatemanager.PropertyList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.Repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.Repositories.RealEstateAgentDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    // REPOSITORIES
    private final PropertyDataRepository propertyDataSource;
    private final RealEstateAgentDataRepository realEstateAgentDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<RealEstateAgent> currentRealEstateAgent;

    public PropertyViewModel(PropertyDataRepository propertyDataSource,
                             RealEstateAgentDataRepository realEstateAgentDataSource,
                             Executor executor) {
        this.propertyDataSource = propertyDataSource;
        this.realEstateAgentDataSource = realEstateAgentDataSource;
        this.executor = executor;
    }

    public void init(long realEstateAgent_Id) {
        if (this.currentRealEstateAgent != null) {
            return;
        }
        currentRealEstateAgent = realEstateAgentDataSource.getRealEstateAgent(realEstateAgent_Id);
    }

    // --------------------
    // FOR REALESTATEAGENT
    // --------------------

    public LiveData<RealEstateAgent> getRealEstateAgent(long realEstateAgent_Id) { return this.currentRealEstateAgent;  }

    // -------------
    // FOR PROPERTY
    // -------------

    public LiveData<List<Property>> getItems(long userId) {
        return propertyDataSource.getItems(userId);
    }

    public void createItem(Property property) {
        executor.execute(() -> {
            propertyDataSource.createProperty(property);
        });
    }

    public void deleteItem(long property_Id) {
        executor.execute(() -> {
            propertyDataSource.deleteProperty(property_Id);
        });
    }

    public void updateItem(Property property) {
        executor.execute(() -> {
            propertyDataSource.updateProperty(property);
        });
    }
}

