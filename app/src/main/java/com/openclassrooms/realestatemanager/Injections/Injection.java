package com.openclassrooms.realestatemanager.Injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.Repositories.RealEstateAgentDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static EstateDataRepository mEstateDataRepository;

    public static EstateDataRepository providePropertyDataSource(Context context) {
        if (mEstateDataRepository == null) {
            RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
            mEstateDataRepository = new EstateDataRepository(database.estateDao());
        }

        return mEstateDataRepository;
    }

    public static RealEstateAgentDataRepository provideRealEstateAgentDataSource(Context context) {
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new RealEstateAgentDataRepository(database.realEstateAgentDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EstateDataRepository dataSourceProperty = providePropertyDataSource(context);
        RealEstateAgentDataRepository dataSourceRealEstateAgent = provideRealEstateAgentDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProperty, dataSourceRealEstateAgent, executor);
    }
}
