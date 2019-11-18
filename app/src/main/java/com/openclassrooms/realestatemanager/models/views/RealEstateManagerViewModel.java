package com.openclassrooms.realestatemanager.models.views;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.annotation.Nullable;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.util.Log;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.RealEstateAgent;
import com.openclassrooms.realestatemanager.models.SearchData;
import com.openclassrooms.realestatemanager.repositories.CurrentRealEstateAgentDataRepository;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentDataRepository;

import java.util.ArrayList;
import java.util.List;

public class RealEstateManagerViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = RealEstateManagerViewModel.class.getSimpleName();

    // REPOSITORIES
    private final RealEstateAgentDataRepository mRealEstateAgentDataSource;
    private final EstateDataRepository mEstateDataSource;

    // DATA
    @Nullable
    private LiveData<RealEstateAgent> mCurrentRealEstateAgent;
    @NonNull
    private MediatorLiveData<List<Estate>> mCurrentEstates = new MediatorLiveData<>();
    @NonNull
    private MutableLiveData<SearchData> mSearchData = new MutableLiveData<>();


    public RealEstateManagerViewModel(EstateDataRepository estatedatarepository,
                                      RealEstateAgentDataRepository realEstateAgentDataSource) {
        mRealEstateAgentDataSource = realEstateAgentDataSource;
        mEstateDataSource = estatedatarepository;

        // Get the whole list of estates
        LiveData<List<Estate>> estates = mEstateDataSource.getEstates();

        // Initialize SearchData
        SearchData sd = new SearchData();
        sd.init(1);
        mSearchData.setValue(sd);

        // When Estate List contained in the table change
        mCurrentEstates.addSource(estates,
                new Observer<List<Estate>>() {
                    @Override
                    public void onChanged(@Nullable List<Estate> estates) {
                        Log.d(TAG, "onChanged() estates called with: mSearchData = [" + mSearchData + "]");
                        // Build SimpleSQLiteQuery
                        SimpleSQLiteQuery mSimpleQuery = new SimpleSQLiteQuery(
                                mSearchData.getValue().getQueryString(), mSearchData.getValue().getArgs().toArray());
                        // Submit Request and get the result
                        LiveData<List<Estate>> lEstate = mEstateDataSource.getEstates(mSimpleQuery);

                        lEstate.observeForever(new Observer<List<Estate>>() {
                            @Override
                            public void onChanged(List<Estate> estates) {

                                // Unsubscribe the Observer
                                lEstate.removeObserver(this);

                                // Filter and Update Estates List
                                mCurrentEstates.setValue(manageFilterEstates(estates,mSearchData.getValue()));
                            }
                        });
                    }
                });

        // When CurrentRealEstateAgent change
        mCurrentEstates.addSource(CurrentRealEstateAgentDataRepository.
                        getInstance().getCurrentRealEstateAgent_Id(),
                new Observer<Long>() {
                    @Override
                    public void onChanged(@Nullable Long agentId) {
                        Log.d(TAG, "onChanged: agentId");
                        // Build SimpleSQLiteQuery
                        SimpleSQLiteQuery mSimpleQuery = new SimpleSQLiteQuery(
                                mSearchData.getValue().getQueryString(), mSearchData.getValue().getArgs().toArray());
                        // Submit Request and get the result
                        LiveData<List<Estate>> lEstate = mEstateDataSource.getEstates(mSimpleQuery);

                        lEstate.observeForever(new Observer<List<Estate>>() {
                            @Override
                            public void onChanged(List<Estate> estates) {

                                // Unsubscribe the Observer
                                lEstate.removeObserver(this);

                                // Filter and Update Estates List
                                mCurrentEstates.setValue(manageFilterEstates(estates,mSearchData.getValue()));
                            }
                        });
                    }
                });

        // When SearchData change
        mCurrentEstates.addSource(getSearchData(),
                new Observer<SearchData>() {
                    @Override
                    public void onChanged(@Nullable SearchData searchData) {
                        Log.d(TAG, "onChanged() called with: searchData = [" + searchData + "]");
                        // Build SimpleSQLiteQuery
                        SimpleSQLiteQuery mSimpleQuery = new SimpleSQLiteQuery(
                                searchData.getQueryString(), searchData.getArgs().toArray());
                        // Submit Request and get the result
                        LiveData<List<Estate>> lEstate = mEstateDataSource.getEstates(mSimpleQuery);

                        lEstate.observeForever(new Observer<List<Estate>>() {
                            @Override
                            public void onChanged(List<Estate> estates) {

                                // Unsubscribe the Observer
                                lEstate.removeObserver(this);

                                // Filter and Update Estates List
                                mCurrentEstates.setValue(manageFilterEstates(estates,searchData));
                            }
                        });
                    }
                });
    }

    //Manages the filtering of the estate list
    private List<Estate> manageFilterEstates(List<Estate> estates, SearchData searchData) {
        Log.d(TAG, "manageFilterEstates: searchData = "+searchData);
        List<Estate> localEstate = new ArrayList<>();
        if (estates != null ) {
            localEstate = filterEstatesAgentId(estates,
                    CurrentRealEstateAgentDataRepository.getInstance()
                            .getCurrentRealEstateAgent_Id().getValue());

            if (localEstate.size() != 0) {
                // Filter on City argument
                if (!searchData.getCity().isEmpty())
                    localEstate = filterEstatesCity(estates, searchData.getCity());
                // Filter on numberOfPhoto
                if (searchData.getPhotoMax() > 0)
                    localEstate = filterEstatesNumberOfPhoto(localEstate,
                            searchData.getPhotoMin(), searchData.getPhotoMax());
                // Filter on interestPoint
                if (searchData.getChips().get(0))
                    localEstate = filterEstatesInterestPoint(localEstate, "School");
                if (searchData.getChips().get(1))
                    localEstate = filterEstatesInterestPoint(localEstate, "Swimming Pool");
                if (searchData.getChips().get(2))
                    localEstate = filterEstatesInterestPoint(localEstate, "Town Hall");
                if (searchData.getChips().get(3))
                    localEstate = filterEstatesInterestPoint(localEstate, "Library");
                if (searchData.getChips().get(4))
                    localEstate = filterEstatesInterestPoint(localEstate, "Garden");
                if (searchData.getChips().get(5))
                    localEstate = filterEstatesInterestPoint(localEstate, "Restaurant");
            }
        }
        return localEstate;
    }

    // Retrieves only the Estate list of the agent specified as a parameter
    private List<Estate> filterEstatesAgentId(List<Estate> estates, Long realEstateAgent_Id) {
        Log.d(TAG, "filterEstatesAgentId: ");
        List<Estate> localEstate = new ArrayList<>();

        if (estates == null) return localEstate;
        Log.d(TAG, "filterEstatesAgentId: estates.size() = "+estates.size());
        for (Estate estate : estates) {
            Log.d(TAG, "filterEstatesAgentId: O");
            Log.d(TAG, "filterEstatesAgentId() ["+estate.getEstate_Id()+"] : "+estate.getEstate_Id());
            if (estate.getRealEstateAgent_Id() == realEstateAgent_Id) {
                localEstate.add(estate);
            }
        }
        Log.d(TAG, "filterEstatesAgentId: localEstate.size() = "+localEstate.size());
        return localEstate;
    }

    // Retrieves only the Estate list of the city specified as a parameter
    private List<Estate> filterEstatesCity(List<Estate> estates, String city) {
        Log.d(TAG, "filterEstatesCity: ");
        List<Estate> localEstate = new ArrayList<>();

        for (Estate estate : estates) {
            if (estate.getAddress().get(3).equals(city)) {
                localEstate.add(estate);
            }
        }
        return localEstate;
    }
    // Retrieves only the Estate list of the number of photo specified as a parameter
    private List<Estate> filterEstatesNumberOfPhoto(List<Estate> estates, int photoMin, int photoMax) {
        Log.d(TAG, "filterEstatesNumberOfPhoto: ");
        List<Estate> localEstate = new ArrayList<>();

        for (Estate estate : estates) {
            if (    estate.getPhotos().size() >= photoMin &&
                    estate.getPhotos().size() <= photoMax   ) {
                localEstate.add(estate);
            }
        }
        return localEstate;
    }
    // Retrieves only the Estate list of the interest point specified as a parameter
    private List<Estate> filterEstatesInterestPoint(List<Estate> estates,String interestPoint) {
        for (Estate estate : estates) {
            Log.d(TAG, "filterEstatesAgentId: S");
            Log.d(TAG, "filterEstatesInterestPoint() ["+interestPoint+"] : "+estate.getPointOfInterest());
        }
        List<Estate> localEstate = new ArrayList<>();

        for (Estate estate : estates) {
            if (estate.getPointOfInterest().containsKey(interestPoint)) localEstate.add(estate);
        }
        return localEstate;
    }

    public void init(long realEstateAgent_Id) {
        if (mCurrentRealEstateAgent != null) {
            return;
        }
        mCurrentRealEstateAgent = mRealEstateAgentDataSource.getRealEstateAgent(realEstateAgent_Id);
    }

    public LiveData<RealEstateAgent> getCurrentRealEstateAgent() {
        return mCurrentRealEstateAgent;
    }

    @NonNull
    public MediatorLiveData<List<Estate>> getCurrentEstates() {
        return mCurrentEstates;
    }

    public LiveData<SearchData> getSearchData() {
        return mSearchData;
    }

    public void setSearchData(SearchData searchData) {
        this.mSearchData.setValue(searchData);
    }
}

