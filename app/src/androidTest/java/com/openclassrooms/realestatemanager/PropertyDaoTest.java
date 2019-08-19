package com.openclassrooms.realestatemanager;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PropertyDaoTest {

    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    // --> New RealEstateAgent
    private static long REAL_ESTATE_AGENT_ID = 1;
    private static RealEstateAgent REAL_ESTATE_AGENT_DEMO
            = new RealEstateAgent(REAL_ESTATE_AGENT_ID,
            "Michaël",
            "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg");
    // --> New Property
    private static Property NEW_PROPERTY_FLAT
            = new Property("Flat", 10000000, 750,
    5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            "3 way of the temple, 75001 PARIS",
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID);
    private static Property NEW_PROPERTY_HOUSE
            = new Property("House", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            "3 way of the temple, 75001 PARIS",
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID);
    private static Property NEW_PROPERTY_PENTHOUSE
            = new Property("Penthouse", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            "3 way of the temple, 75001 PARIS",
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetRealEstateAgent() throws InterruptedException {
        // BEFORE : Adding a new realEstateAgent
        this.database.realEstateAgentDao().createRealEstateAgent(REAL_ESTATE_AGENT_DEMO);
        // TEST
        RealEstateAgent realEstateAgent =
                LiveDataTestUtil.getValue(this.database.realEstateAgentDao().getRealEstateAgent(REAL_ESTATE_AGENT_ID));

        assertTrue(realEstateAgent.getUserName().equals(REAL_ESTATE_AGENT_DEMO.getUserName())
                && realEstateAgent.getRealEstateAgent_Id() == REAL_ESTATE_AGENT_ID);
    }

    @Test
    public void getPropertysWhenNoPropertyInserted() throws InterruptedException {
        // TEST
        List<Property> propertys = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertys(REAL_ESTATE_AGENT_ID));
        assertTrue(propertys.isEmpty());
    }

    @Test
    public void insertAndGetPropertys() throws InterruptedException {
        // BEFORE : Adding demo realEstateAgent & demo propertys

        this.database.realEstateAgentDao().createRealEstateAgent(REAL_ESTATE_AGENT_DEMO);
        this.database.propertyDao().insertProperty(NEW_PROPERTY_FLAT);
        this.database.propertyDao().insertProperty(NEW_PROPERTY_HOUSE);
        this.database.propertyDao().insertProperty(NEW_PROPERTY_PENTHOUSE);

        // TEST
        List<Property> items = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertys(REAL_ESTATE_AGENT_ID));
        assertTrue(items.size() == 3);
    }

    @Test
    public void insertAndUpdateProperty() throws InterruptedException {
        // BEFORE : Adding demo realEstateAgent & demo propertys. Next, update property added & re-save it.
        this.database.realEstateAgentDao().createRealEstateAgent(REAL_ESTATE_AGENT_DEMO);
        this.database.propertyDao().insertProperty(NEW_PROPERTY_FLAT);
        Property propertyAdded = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertys(REAL_ESTATE_AGENT_ID)).get(0);
        propertyAdded.setStatus(true);
        this.database.propertyDao().updateProperty(propertyAdded);

        //TEST
        List<Property> propertys = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertys(REAL_ESTATE_AGENT_ID));
        assertTrue(propertys.size() == 1 && propertys.get(0).getStatus());
    }

    @Test
    public void insertAndDeleteProperty() throws InterruptedException {
        // BEFORE : Adding demo realEstateAgent & demo propertys. Next, update property added & delete it.
        this.database.realEstateAgentDao().createRealEstateAgent(REAL_ESTATE_AGENT_DEMO);
        this.database.propertyDao().insertProperty(NEW_PROPERTY_FLAT);
        Property propertyAdded = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertys(REAL_ESTATE_AGENT_ID)).get(0);
        this.database.propertyDao().deleteProperty(propertyAdded.getProperty_Id());

        //TEST
        List<Property> items = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertys(REAL_ESTATE_AGENT_ID));
        assertTrue(items.isEmpty());
    }
}
