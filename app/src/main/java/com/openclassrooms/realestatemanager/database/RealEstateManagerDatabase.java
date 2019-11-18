package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.database.dao.RealEstateAgentDao;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.RealEstateAgent;
import com.openclassrooms.realestatemanager.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;

@Database(entities = {Estate.class, RealEstateAgent.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    // For Debug
    private static final String TAG = "RealEstateManagerDataba";

    // --- SINGLETON ---
    private static volatile RealEstateManagerDatabase INSTANCE;

    // --- DAO ---
    public abstract EstateDao estateDao();

    public abstract RealEstateAgentDao realEstateAgentDao();

    // --- INSTANCE ---
    public static RealEstateManagerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class, "RealEstateManagerDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .setJournalMode(JournalMode.TRUNCATE)  // Disable .wal (write ahead logging)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                //////////////////////////////     REAL ESTATE AGENT 1      //////////////////////////////
                ContentValues contentValuesAgent1 = new ContentValues();
                contentValuesAgent1.put("realEstateAgent_Id", 1);
                contentValuesAgent1.put("username", "Michaël");
                contentValuesAgent1.put("urlPicture", "https://scontent-cdt1-1.xx.fbcdn.net/v/t1.0-9/62059518_3225084230850434_4625271974942212096_n.jpg?_nc_cat=102&_nc_oc=AQm4TL75tws1vDGbVxEsiD3s-GK59eUV0jV3QtFZSz6AUO34w6MkbooJyHava90OMXc&_nc_ht=scontent-cdt1-1.xx&oh=8aae1769eb35da4c3c337fb116728365&oe=5DA8B870");
                db.insert("RealEstateAgent", OnConflictStrategy.IGNORE, contentValuesAgent1);

                //////////////////////////////     REAL ESTATE AGENT 1      //////////////////////////////
                ContentValues contentValuesAgent2 = new ContentValues();
                contentValuesAgent2.put("realEstateAgent_Id", 2);
                contentValuesAgent2.put("username", "Nino");
                contentValuesAgent2.put("urlPicture", "https://scontent-cdt1-1.xx.fbcdn.net/v/t1.0-9/62059518_3225084230850434_4625271974942212096_n.jpg?_nc_cat=102&_nc_oc=AQm4TL75tws1vDGbVxEsiD3s-GK59eUV0jV3QtFZSz6AUO34w6MkbooJyHava90OMXc&_nc_ht=scontent-cdt1-1.xx&oh=8aae1769eb35da4c3c337fb116728365&oe=5DA8B870");
                db.insert("RealEstateAgent", OnConflictStrategy.IGNORE, contentValuesAgent2);

                //////////////////////////////     REAL ESTATE AGENT 1 : HOUSE 1    //////////////////////////////
                ContentValues contentValuesRealEstateHouse1 = new ContentValues();
                contentValuesRealEstateHouse1.put("type", "House");
                contentValuesRealEstateHouse1.put("price", 3999000);
                contentValuesRealEstateHouse1.put("area", 7200);
                contentValuesRealEstateHouse1.put("numberOfParts", 15);
                contentValuesRealEstateHouse1.put("numberOfBathrooms", 8);
                contentValuesRealEstateHouse1.put("numberOfBedrooms", 7);
                contentValuesRealEstateHouse1.put("description", "Why move to the Hamptons when you can have all they offer so much closer? " +
                        "Once you see this stunning new eleven-thousand square-foot entertainer’s paradise sitting on two-and-a-half serene and secluded acres in the waterfront community of Lattingtown Harbor, " +
                        "with its own beach house, beach, dock, and mooring rights, you will ask the same question. Bringing Traditional style into the 21st Century, " +
                        "the home is unique and stunning. It boasts: vaulted sky-lit ceilings; three dramatic stone fireplaces; oak floors, a fabulous indoor pool with cabana bath; " +
                        "a finished basement with home theater and Sonos audio; I-pad 3-station smart-house technology; seven spacious bedrooms with baths en suite, including a master and two guest suites on the first floor; " +
                        "two powder rooms; and exquisite appointments throughout. This architectural masterpiece will both pamper and enthrall you and your guests. Lattingtown, on the shores of Long Island Sound, " +
                        "is the northernmost part of a larger area fanning out from the village of Locust Valley, that is referred to by many as “Locust Valley” " +
                        "just as all seaside communities on Long Island’s lower eastern peninsula are called “the Hamptons.” Located about 30 miles from Manhattan, " +
                        "it has in recent years become a closer and more appealing destination for a younger generation of successful New Yorkers to live. " +
                        "It is known for its grand estates, scenic vistas, country and golf clubs, beaches, and its proximity to yacht clubs, fine dining, " +
                        "quaint shopping districts, private and public schools, and the Long Island Railroad. Approached by a circular drive off a cul-de-sac, " +
                        "this sprawling mini estate is carved out of pristine woodland enfolding it with a sense of seclusion with ample space for the addition of any number of summer recreational venues. " +
                        "Across the rear of the house a wide bluestone patio with outdoor kitchen, fire pit, and easy access to the pool, is perfect for summer entertaining. " +
                        "The long circular drive and service courtyard serving the attached three car garage provide ample parking for guests. Providing the ultimate in luxurious “Gold Coast” living, " +
                        "the bright and airy interior is a work of art designed to please all with supreme comfort and stunning style. ");
                String photos = "[\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_outside.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_facade.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_living_room.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_dining_room.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_kitchen.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_bedroom.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_bathroom.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house1/house_1_swimming_pool.jpg\"]";
                contentValuesRealEstateHouse1.put("photos", photos);
                contentValuesRealEstateHouse1.put("photosDescription","[\"outside\",\"facade\",\"living room\",\"dining room\"," +
                                                                    "\"kitchen\",\"bedroom\",\"bathroom\",\"swimming-pool\"]");
                contentValuesRealEstateHouse1.put("address", Arrays.asList("\"8 Mindy Ct\"", "\"\"", "\"Lattingtown\"", "\"New York\"", "\"United states\"").toString());
                contentValuesRealEstateHouse1.put("pointOfInterest", "{\"Swimming Pool\":\"Swimming Pool\",\"Library\":\"Library\"}");
                contentValuesRealEstateHouse1.put("status", false);
                contentValuesRealEstateHouse1.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(3).withYear(2018).withMonth(4)));
                //contentValuesRealEstateHouse1.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateHouse1.put("realEstateAgent_Id", 1);
                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateHouse1);

                //////////////////////////////     REAL ESTATE AGENT 1 : HOUSE 1    //////////////////////////////
                ContentValues contentValuesRealEstateHouse2 = new ContentValues();
                contentValuesRealEstateHouse2.put("type", "House");
                contentValuesRealEstateHouse2.put("price", 3999000);
                contentValuesRealEstateHouse2.put("area", 6000);
                contentValuesRealEstateHouse2.put("numberOfParts", 10);
                contentValuesRealEstateHouse2.put("numberOfBathrooms", 5);
                contentValuesRealEstateHouse2.put("numberOfBedrooms", 5);
                contentValuesRealEstateHouse2.put("description", "Centre Island, Stately Brick Colonial Style Manor Home, " +
                        "Perched On 3.26 Waterfront Acres w/ 250 feet of Sandy Beach ." +
                        "This Spectacular Property Features 180 Degree Panoramic Waterviews From Almost Every Room." +
                        "The Spacious Interior Boasts,10 Ft Ceilings,Radiant Heat, Marble Bathrooms,5 Fireplaces, " +
                        "Custom Moldings & Architectural Details Throughout." +
                        "The Home Also Includes A Separate 2 Car Gar & 800 Sq Ft Cottage W/Waterviews. " +
                        "Beautifully Landscaped Property Perfect For Entertaining W/Steps To Beach.");
                contentValuesRealEstateHouse2.put("photos", "[" +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_outside.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_facade.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_living_room.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_dining_room.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_kitchen.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_bedroom.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_bathroom.jpg\"]");
                contentValuesRealEstateHouse2.put("photosDescription","[" +
                        "\"outside\"," +
                        "\"facade\"," +
                        "\"living room\"," +
                        "\"dining room\"," +
                        "\"kitchen\"," +
                        "\"bedroom\"," +
                        "\"bathroom\"]");
                contentValuesRealEstateHouse2.put("address", Arrays.asList("\"512 Centre Island Rd\"", "\"\"", "\"Centre Island\"", "\"New York\"", "\"United states\"").toString());
                contentValuesRealEstateHouse2.put("pointOfInterest", "{" +
                        "\"Town hall\":\"Town hall\"," +
                        "\"Library\":\"Library\"," +
                        "\"School\":\"School\"}");
                contentValuesRealEstateHouse2.put("status", false);
                contentValuesRealEstateHouse2.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(25).withYear(2019).withMonth(12)));
                //contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateHouse2.put("realEstateAgent_Id", 1);
                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateHouse2);
            }
        };
    }
}
