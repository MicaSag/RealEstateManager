package com.openclassrooms.realestatemanager.Models.views;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class EstateCreateViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateCreateViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    private MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> mPhotos;

    public EstateCreateViewModel(EstateDataRepository estateDataSource,
                                 Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;
        mPhotos = new MutableLiveData<>();
        mPhotos.setValue(new ArrayList<>());
    }

    public void createEstate(
           @NonNull String type,
            String price,
            Integer area//,
//            Integer numberOfParts,
//            Integer numberOfBathrooms,
//            Integer numberOfBedrooms,
//            String description,
//            ArrayList<String> photos,
//            ArrayList<String> photosDescription,
//            ArrayList<String> address,
//            Map<String,String> pointOfInterest,
//            LocalDateTime dateEntryOfTheMarket,
//            LocalDateTime dateOfSale,
//            long realEstateAgent_Id
    ) {
        Estate estate = validateData(type, price, area);

        if (estate != null) {

            mExecutor.execute(() -> {
                mEstateDataSource.createEstate(estate);

                mViewActionLiveData.postValue(ViewAction.FINISH_ACTIVITY);
            });
        } else {
            mViewActionLiveData.setValue(ViewAction.INVALID_INPUT);
        }
    }

    private Estate validateData(
            @NonNull   String type,
            String price,
            Integer area
    ) {
        if(type.isEmpty()) {
            return null;
        }

        int priceAsNumber;

        try {
            priceAsNumber = Integer.parseInt(price);
        } catch (NumberFormatException e) {
            e.printStackTrace();

            return null;
        }

       return new Estate(type, priceAsNumber, area /* BLABLA*/  );
    }

    public LiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }

    public LiveData<List<String>> getPhotos() {
        return mPhotos;
    }

    public void addPhoto(String photo) {
        List<String> l = mPhotos.getValue();
        l.add(photo);
        this.mPhotos.setValue(l);
    }

    public enum ViewAction {
        INVALID_INPUT,
        FINISH_ACTIVITY
    }
}


