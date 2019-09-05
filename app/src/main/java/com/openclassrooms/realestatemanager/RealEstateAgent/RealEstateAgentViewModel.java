package com.openclassrooms.realestatemanager.RealEstateAgent;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.Repositories.RealEstateAgentDataRepository;

import java.util.concurrent.Executor;

public class RealEstateAgentViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = RealEstateAgentViewModel.class.getSimpleName();

    // REPOSITORIES
    private final RealEstateAgentDataRepository mRealEstateAgentDataSource;
    private final Executor mExecutor;

    // DATA
    @Nullable
    private LiveData<RealEstateAgent> mCurrentRealEstateAgent;

    public RealEstateAgentViewModel(RealEstateAgentDataRepository realEstateAgentDataSource,
                                    Executor executor) {
        mRealEstateAgentDataSource = realEstateAgentDataSource;
        mExecutor = executor;
    }

    public void init(long realEstateAgent_Id) {
        Log.d(TAG, "init: ");
        if (mCurrentRealEstateAgent != null) {
            return;
        }
        mCurrentRealEstateAgent = mRealEstateAgentDataSource.getRealEstateAgent(realEstateAgent_Id);
    }

    public LiveData<RealEstateAgent> getCurrentRealEstateAgent() {
        return mCurrentRealEstateAgent;
    }

    public void setCurrentRealEstateAgent(long realEstateAgent_Id) {
        mCurrentRealEstateAgent = mRealEstateAgentDataSource.getRealEstateAgent(realEstateAgent_Id);
    }

    public LiveData<RealEstateAgent> getRealEstateAgent(long realEstateAgent_Id) {
        return mRealEstateAgentDataSource.getRealEstateAgent(realEstateAgent_Id);
    }

    public void createRealEstateAgent(RealEstateAgent realEstateAgent) {
        mExecutor.execute(() -> {
            mRealEstateAgentDataSource.createRealEstateAgent(realEstateAgent);
        });
    }

    public void deleteRealEstateAgent(long realEstateAgent_Id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRealEstateAgentDataSource.deleteRealEstateAgent(realEstateAgent_Id);
            }
        });
    }
}

