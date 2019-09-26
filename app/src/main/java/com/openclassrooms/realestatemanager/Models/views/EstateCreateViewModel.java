package com.openclassrooms.realestatemanager.Models.views;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import java.util.concurrent.Executor;

public class EstateCreateViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateCreateViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    private Estate mEstate = new Estate();

    public EstateCreateViewModel(EstateDataRepository estateDataSource,
                                 Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;
        mEstate.setPrice(18);
    }

    public Estate getEstate() {
        return mEstate;
    }

    public void setEstate(Estate mEstate) {
        this.mEstate = mEstate;
    }

    public void createEsate() {
        mExecutor.execute(() -> mEstateDataSource.createEstate(mEstate));
    }
}


