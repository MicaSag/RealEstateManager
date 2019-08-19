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

import com.openclassrooms.realestatemanager.Database.Dao.PropertyDao;
import com.openclassrooms.realestatemanager.Database.Dao.RealEstateAgentDao;
import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.Utils.Converters;

@Database(entities = {Property.class, RealEstateAgent.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile RealEstateManagerDatabase INSTANCE;

    // --- DAO ---
    public abstract PropertyDao propertyDao();
    public abstract RealEstateAgentDao realEstateAgentDao();

    // --- INSTANCE ---
    public static RealEstateManagerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class, "RealEstateManagerDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("realEstateAgent_Id", 1);
                contentValues.put("username", "Michaël");
                contentValues.put("urlPicture", "https://scontent-cdt1-1.xx.fbcdn.net/v/t1.0-9/62059518_3225084230850434_4625271974942212096_n.jpg?_nc_cat=102&_nc_oc=AQm4TL75tws1vDGbVxEsiD3s-GK59eUV0jV3QtFZSz6AUO34w6MkbooJyHava90OMXc&_nc_ht=scontent-cdt1-1.xx&oh=8aae1769eb35da4c3c337fb116728365&oe=5DA8B870");

                db.insert("RealEstateAgent", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
