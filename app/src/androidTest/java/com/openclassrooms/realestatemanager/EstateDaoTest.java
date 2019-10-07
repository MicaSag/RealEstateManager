package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.Models.Estate;
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
public class EstateDaoTest {

    // FOR DATA
    private RealEstateManagerDatabase database;

    // DATA SET FOR TEST
    // --> New RealEstateAgent
    private static long REAL_ESTATE_AGENT_ID = 1;
    private static RealEstateAgent REAL_ESTATE_AGENT_DEMO
            = new RealEstateAgent(REAL_ESTATE_AGENT_ID,
            "Michaël",
            "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg");
    // --> New Estate
    private static Estate newEstateFlat
            = new Estate("Flat", 10000000, 750,
    5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75001","France","1er arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID);
    private static Estate newEstateHouse
            = new Estate("House", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75005","France","5ème arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID);
    private static Estate newEstatePenthouse
            = new Estate("Penthouse", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75010","France","10ème arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
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
    public void getEstatesWhenNoEstateInserted() throws InterruptedException {
        // TEST
        List<Estate> estates = LiveDataTestUtil.getValue(this.database.estateDao().getEstates(REAL_ESTATE_AGENT_ID));
        assertTrue(estates.isEmpty());
    }

    @Test
    public void insertAndGetEstates() throws InterruptedException {
        // BEFORE : Adding demo realEstateAgent & demo estates

        this.database.realEstateAgentDao().createRealEstateAgent(REAL_ESTATE_AGENT_DEMO);
        this.database.estateDao().insertEstate(newEstateFlat);
        this.database.estateDao().insertEstate(newEstateHouse);
        this.database.estateDao().insertEstate(newEstatePenthouse);

        // TEST
        List<Estate> items = LiveDataTestUtil.getValue(this.database.estateDao().getEstates(REAL_ESTATE_AGENT_ID));
        assertTrue(items.size() == 3);
    }

    @Test
    public void insertAndUpdateEstate() throws InterruptedException {
        // BEFORE : Adding demo realEstateAgent & demo estates. Next, update property added & re-save it.
        this.database.realEstateAgentDao().createRealEstateAgent(REAL_ESTATE_AGENT_DEMO);
        this.database.estateDao().insertEstate(newEstateFlat);
        Estate estateAdded = LiveDataTestUtil.getValue(this.database.estateDao().getEstates(REAL_ESTATE_AGENT_ID)).get(0);
        estateAdded.setStatus(true);
        this.database.estateDao().updateEstate(estateAdded);

        //TEST
        List<Estate> estates = LiveDataTestUtil.getValue(this.database.estateDao().getEstates(REAL_ESTATE_AGENT_ID));
        assertTrue(estates.size() == 1 && estates.get(0).getStatus());
    }

    @Test
    public void insertAndDeleteEstate() throws InterruptedException {
        // BEFORE : Adding demo realEstateAgent & demo estates. Next, update property added & delete it.
        this.database.realEstateAgentDao().createRealEstateAgent(REAL_ESTATE_AGENT_DEMO);
        this.database.estateDao().insertEstate(newEstateFlat);
        Estate estateAdded = LiveDataTestUtil.getValue(this.database.estateDao().getEstates(REAL_ESTATE_AGENT_ID)).get(0);
        this.database.estateDao().deleteEstate(estateAdded.getEstate_Id());

        //TEST
        List<Estate> items = LiveDataTestUtil.getValue(this.database.estateDao().getEstates(REAL_ESTATE_AGENT_ID));
        assertTrue(items.isEmpty());
    }
}
