package com.openclassrooms.realestatemanager.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

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

                ContentValues contentValuesRealEstateFlat = new ContentValues();
                contentValuesRealEstateFlat.put("type", "Flat");
                contentValuesRealEstateFlat.put("price", 10000000);
                contentValuesRealEstateFlat.put("area", 750);
                contentValuesRealEstateFlat.put("numberOfParts", 5);
                contentValuesRealEstateFlat.put("numberOfBathrooms", 2);
                contentValuesRealEstateFlat.put("numberOfBedrooms", 4);
                contentValuesRealEstateFlat.put("description", "Beautifull FLAT in Paris");
                contentValuesRealEstateFlat.put("photos", Arrays.asList("\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"").toString());
                contentValuesRealEstateFlat.put("address", Arrays.asList("\"3 way of the temple\"", "\"\"", "\"PARIS\"", "\"75001\"", "\"France\"", "\"1er arrond\"").toString());
                contentValuesRealEstateFlat.put("pointOfInterest", Arrays.asList("\"School Jean Baptiste\"", "\"Super Market Lidl\"").toString());
                contentValuesRealEstateFlat.put("status", false);
                contentValuesRealEstateFlat.put("dateEntryOfTheMarket", LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8).toString());
                contentValuesRealEstateFlat.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateFlat.put("realEstateAgent_Id", 1);

                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateFlat);

                ContentValues contentValuesRealEstateHouse = new ContentValues();
                contentValuesRealEstateHouse.put("type", "House");
                contentValuesRealEstateHouse.put("price", 9000000);
                contentValuesRealEstateHouse.put("area", 890);
                contentValuesRealEstateHouse.put("numberOfParts", 10);
                contentValuesRealEstateHouse.put("numberOfBathrooms", 3);
                contentValuesRealEstateHouse.put("numberOfBedrooms", 6);
                contentValuesRealEstateHouse.put("description", "Beautifull House in Paris");
                contentValuesRealEstateHouse.put("photos", Arrays.asList("\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"",
                        "\"https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg\"").toString());
                contentValuesRealEstateHouse.put("address", Arrays.asList("\"3 way of the temple\"", "\"\"", "\"PARIS\"", "\"75001\"", "\"France\"", "\"1er arrond\"").toString());
                contentValuesRealEstateHouse.put("pointOfInterest", Arrays.asList("\"School Jean Baptiste\"", "\"Super Market Lidl\"").toString());
                contentValuesRealEstateHouse.put("status", false);
                contentValuesRealEstateHouse.put("dateEntryOfTheMarket", LocalDateTime.now().withDayOfMonth(3).withYear(2018).withMonth(4).toString());
                contentValuesRealEstateHouse.put("dateOfSale", LocalDateTime.now().toString());
                contentValuesRealEstateHouse.put("realEstateAgent_Id", 2);

                db.insert("Estate", OnConflictStrategy.IGNORE, contentValuesRealEstateHouse);
            }
        };
    }
}
