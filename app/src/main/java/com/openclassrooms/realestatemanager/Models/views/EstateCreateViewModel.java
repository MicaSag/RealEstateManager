package com.openclassrooms.realestatemanager.Models.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class EstateCreateViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateCreateViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    private Estate mEstate = new Estate();
    private MutableLiveData<ArrayList<String>> mPhotos;

    public EstateCreateViewModel(EstateDataRepository estateDataSource,
                                 Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;
        mPhotos = new MutableLiveData<>();
        mPhotos.setValue(new ArrayList<>());
    }

    public Estate getEstate() {
        return mEstate;
    }

    public void setEstate(Estate mEstate) {
        this.mEstate = mEstate;
    }

    public void createEstate() {
        mExecutor.execute(() -> mEstateDataSource.createEstate(mEstate));
    }

    public LiveData<ArrayList<String>> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.mPhotos.setValue(photos);
    }
}


