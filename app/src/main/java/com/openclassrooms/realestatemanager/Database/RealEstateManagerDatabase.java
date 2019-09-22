package com.openclassrooms.realestatemanager.Database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;

import com.openclassrooms.realestatemanager.Database.Dao.EstateDao;
import com.openclassrooms.realestatemanager.Database.Dao.RealEstateAgentDao;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.Utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;

@Database(entities = {Estate.class, RealEstateAgent.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateManagerDatabase extends RoomDatabase {

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

                ContentValues contentValuesAgent1 = new ContentValues();
                contentValuesAgent1.put("realEstateAgent_Id", 1);
                contentValuesAgent1.put("username", "Michaël");
                contentValuesAgent1.put("urlPicture", "https://scontent-cdt1-1.xx.fbcdn.net/v/t1.0-9/62059518_3225084230850434_4625271974942212096_n.jpg?_nc_cat=102&_nc_oc=AQm4TL75tws1vDGbVxEsiD3s-GK59eUV0jV3QtFZSz6AUO34w6MkbooJyHava90OMXc&_nc_ht=scontent-cdt1-1.xx&oh=8aae1769eb35da4c3c337fb116728365&oe=5DA8B870");

                db.insert("RealEstateAgent", OnConflictStrategy.IGNORE, contentValuesAgent1);

                ContentValues contentValuesAgent2 = new ContentValues();
                contentValuesAgent2.put("realEstateAgent_Id", 2);
                contentValuesAgent2.put("username", "Nino");
                contentValuesAgent2.put("urlPicture", "https://scontent-cdt1-1.xx.fbcdn.net/v/t1.0-9/62059518_3225084230850434_4625271974942212096_n.jpg?_nc_cat=102&_nc_oc=AQm4TL75tws1vDGbVxEsiD3s-GK59eUV0jV3QtFZSz6AUO34w6MkbooJyHava90OMXc&_nc_ht=scontent-cdt1-1.xx&oh=8aae1769eb35da4c3c337fb116728365&oe=5DA8B870");

                db.insert("RealEstateAgent", OnConflictStrategy.IGNORE, contentValuesAgent2);

                ContentValues contentValuesRealEstateHouse1 = new ContentValues();
                contentValuesRealEstateHouse1.put("type", "House 1");
                contentValuesRealEstateHouse1.put("price", 9000000);
                contentValuesRealEstateHouse1.put("area", 890);
                contentValuesRealEstateHouse1.put("numberOfParts", 10);
                contentValuesRealEstateHouse1.put("numberOfBathrooms", 3);
                contentValuesRealEstateHouse1.put("numberOfBedrooms", 6);
                contentValuesRealEstateHouse1.put("description", "Beautiful House in Paris");
                contentValuesRealEstateHouse1.put("photos", Arrays.asList("\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"").toString());
                contentValuesRealEstateHouse1.put("address", Arrays.asList("\"3 way of the temple\"", "\"\"", "\"PARIS\"", "\"75001\"", "\"France\"", "\"1er arrond\"").toString());
                contentValuesRealEstateHouse1.put("pointOfInterest", Arrays.asList("\"School Jean Baptiste\"", "\"Super Market Lidl\"").toString());
                contentValuesRealEstateHouse1.put("status", false);
                contentValuesRealEstateHouse1.put("dateEntryOfTheMarket", LocalDateTime.now().withDayOfMonth(3).withYear(2018).withMonth(4).toString());
                contentValuesRealEstateHouse1.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateHouse1.put("realEstateAgent_Id", 1);
                // INSERT HOUSE 1
                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateHouse1);

                ContentValues contentValuesRealEstateFlat1 = new ContentValues();
                contentValuesRealEstateFlat1.put("type", "Flat 1");
                contentValuesRealEstateFlat1.put("price", 10000000);
                contentValuesRealEstateFlat1.put("area", 750);
                contentValuesRealEstateFlat1.put("numberOfParts", 5);
                contentValuesRealEstateFlat1.put("numberOfBathrooms", 2);
                contentValuesRealEstateFlat1.put("numberOfBedrooms", 4);
                contentValuesRealEstateFlat1.put("description", "Beautiful FLAT in Paris");
                contentValuesRealEstateFlat1.put("photos", Arrays.asList("\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"").toString());
                contentValuesRealEstateFlat1.put("address", Arrays.asList("\"3 way of the temple\"", "\"\"", "\"PARIS\"", "\"75001\"", "\"France\"", "\"1er arrond\"").toString());
                contentValuesRealEstateFlat1.put("pointOfInterest", Arrays.asList("\"School Jean Baptiste\"", "\"Super Market Lidl\"").toString());
                contentValuesRealEstateFlat1.put("status", false);
                contentValuesRealEstateFlat1.put("dateEntryOfTheMarket", LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8).toString());
                contentValuesRealEstateFlat1.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateFlat1.put("realEstateAgent_Id", 1);
                // INSERT FLAT 1
                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateFlat1);

                ContentValues contentValuesRealEstateFlat2 = new ContentValues();
                contentValuesRealEstateFlat2.put("type", "Flat 2");
                contentValuesRealEstateFlat2.put("price", 10000000);
                contentValuesRealEstateFlat2.put("area", 750);
                contentValuesRealEstateFlat2.put("numberOfParts", 5);
                contentValuesRealEstateFlat2.put("numberOfBathrooms", 2);
                contentValuesRealEstateFlat2.put("numberOfBedrooms", 4);
                contentValuesRealEstateFlat2.put("description", "Beautiful FLAT in Paris");
                contentValuesRealEstateFlat2.put("photos", Arrays.asList("\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"").toString());
                contentValuesRealEstateFlat2.put("address", Arrays.asList("\"3 way of the temple\"", "\"\"", "\"PARIS\"", "\"75001\"", "\"France\"", "\"1er arrond\"").toString());
                contentValuesRealEstateFlat2.put("pointOfInterest", Arrays.asList("\"School Jean Baptiste\"", "\"Super Market Lidl\"").toString());
                contentValuesRealEstateFlat2.put("status", false);
                contentValuesRealEstateFlat2.put("dateEntryOfTheMarket", LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8).toString());
                contentValuesRealEstateFlat2.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateFlat2.put("realEstateAgent_Id", 2);
                // INSERT FLAT 2
                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateFlat2);

                ContentValues contentValuesRealEstateHouse2 = new ContentValues();
                contentValuesRealEstateHouse2.put("type", "House 2");
                contentValuesRealEstateHouse2.put("price", 9000000);
                contentValuesRealEstateHouse2.put("area", 890);
                contentValuesRealEstateHouse2.put("numberOfParts", 10);
                contentValuesRealEstateHouse2.put("numberOfBathrooms", 3);
                contentValuesRealEstateHouse2.put("numberOfBedrooms", 6);
                contentValuesRealEstateHouse2.put("description", "Beautiful House in Paris");
                contentValuesRealEstateHouse2.put("photos", Arrays.asList("\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"").toString());
                contentValuesRealEstateHouse2.put("address", Arrays.asList("\"3 way of the temple\"", "\"\"", "\"PARIS\"", "\"75001\"", "\"France\"", "\"1er arrond\"").toString());
                contentValuesRealEstateHouse2.put("pointOfInterest", Arrays.asList("\"School Jean Baptiste\"", "\"Super Market Lidl\"").toString());
                contentValuesRealEstateHouse2.put("status", false);
                contentValuesRealEstateHouse2.put("dateEntryOfTheMarket", LocalDateTime.now().withDayOfMonth(3).withYear(2018).withMonth(4).toString());
                contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateHouse2.put("realEstateAgent_Id", 2);
                // INSERT HOUSE 2
                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateHouse2);
            }
        };
    }
}
