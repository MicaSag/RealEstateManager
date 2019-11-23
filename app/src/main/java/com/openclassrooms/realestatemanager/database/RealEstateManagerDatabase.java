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

                /// ADD ESTATES //
                db.insert("Estate", OnConflictStrategy.IGNORE, TestData.house1());
                db.insert("Estate", OnConflictStrategy.IGNORE, TestData.house2());
                db.insert("Estate", OnConflictStrategy.IGNORE, TestData.penthouse1());
                db.insert("Estate", OnConflictStrategy.IGNORE, TestData.penthouse2());
                db.insert("Estate", OnConflictStrategy.IGNORE, TestData.loft1());
                db.insert("Estate", OnConflictStrategy.IGNORE, TestData.loft2());
                db.insert("Estate", OnConflictStrategy.IGNORE, TestData.flat1());
            }
        };
    }
}
