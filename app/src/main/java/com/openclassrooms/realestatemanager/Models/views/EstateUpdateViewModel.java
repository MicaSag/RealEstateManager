package com.openclassrooms.realestatemanager.Models.views;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class EstateUpdateViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateUpdateViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    private Estate mEstate = new Estate();
    private MutableLiveData<ArrayList<String>> mPhotos;

    public EstateUpdateViewModel(EstateDataRepository estateDataSource,
                                 Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;
        mPhotos = new MutableLiveData<>();
        mPhotos.setValue(new ArrayList<>());
    }

    public Estate getEstate() {
        Log.d(TAG, "getEstate: ");
        return mEstate;
    }

    public void setEstate(Estate mEstate) {
        Log.d(TAG, "setEstate() called with: mEstate = [" + mEstate + "]");
        this.mEstate = mEstate;
    }

    public LiveData<ArrayList<String>> getPhotos() {
        Log.d(TAG, "getPhotos: ");
        return mPhotos;
    }

    public void setPhotos(ArrayList<String> photos) {
        Log.d(TAG, "setPhotos: ");
        this.mPhotos.setValue(photos);
    }

    public LiveData<Estate> getEstate(long estate_Id) {
        Log.d(TAG, "getEstate() called with: estate_Id = [" + estate_Id + "]");
        return mEstateDataSource.getEstate(estate_Id);
    }
    public void updateEstate() {
        Log.d(TAG, "updateEstate: ");
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: mEstate = "+mEstate.toString());
                mEstateDataSource.updateEstate(mEstate);
            }
        });
    }
}


