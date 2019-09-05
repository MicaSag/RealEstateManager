package com.openclassrooms.realestatemanager.EstateList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentRepository;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class EstateListViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateListViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    @Nullable
    private MediatorLiveData<List<Estate>> mCurrentEstates = new MediatorLiveData<>();

    public EstateListViewModel(EstateDataRepository estateDataSource,
                               Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;


        mCurrentEstates.addSource(CurrentRealEstateAgentRepository.getInstance().getRealEstateAgent_Id(),
                new Observer<Long>() {
                    @Override
                    public void onChanged(@Nullable Long aLong) {
                        filterEstates(aLong,mEstateDataSource.getEstates().getValue());
                    }
                });
        mCurrentEstates.addSource(mEstateDataSource.getEstates(),
                new Observer<List<Estate>>() {
                    @Override
                    public void onChanged(@Nullable List<Estate> estates) {
                        filterEstates(CurrentRealEstateAgentRepository.getInstance().getRealEstateAgent_Id().getValue()
                                ,estates);
                    }
        });
    }

    private void filterEstates(Long realEstateAgent_Id, List<Estate> estates) {
        ArrayList<Estate> localEstate = new ArrayList<>();

        if (realEstateAgent_Id==null|| estates==null)return ;

        for (Estate estate:estates)
        {
            if (estate.getRealEstateAgent_Id() == realEstateAgent_Id){
                localEstate.add(estate);
            }
        }
        mCurrentEstates.setValue(localEstate);
    }

    // ------------
    //  FOR ESTATE
    // ------------

    // Get current Estate List
    public LiveData<List<Estate>> getCurrentEstates() {
        return mCurrentEstates;
    }



/*
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
    */
}

