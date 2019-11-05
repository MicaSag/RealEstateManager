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
                        "the bright and airy interior is a work of art designed to please all with supreme comfort and stunning style. " +
                        "It begins with a spectacular two-story marble entrance foyer where a graceful floating staircase with ornate filigree balustrade carries the eye up to a soaring tray ceiling. " +
                        "A cloak room and powder room are nearby, and to the rear, three steps lead into the sky-lit living room with three sets of French doors with transom lights open to the rear patio. " +
                        "The fireplace’s massive marble overmantel ascends to the soaring two-story vaulted ceiling, and a unique glass-enclosed, climate-controlled wine vault makes beverage selection a breeze. " +
                        "The banquet-sized formal dining room, with its coffered barrel ceiling, fielded paneling, and pinpoint lighting, provides a stunning setting for gracious dinner parties. " +
                        "The magnificent cook’s kitchen with clerestory-lit vaulted ceiling, offers the finest in appliances, and marble-topped custom cabinetry including a huge center island with prep sink and seating. " +
                        "A second sink looks over the front lawn. Both have integrated dishwashers. Other appliances include a Wolf gas six-burner cooktop with pot filler, " +
                        "built-in double oven, microwave, and oversized refrigerator and freezer. A breakfast alcove features a glass wall and door into the pool area. " +
                        "A service hall leads to the laundry room, garage, mudroom, back stairs, and cabana bath and dressing room by the pool. With vaulted coffered ceiling, " +
                        "the den is perfect for both relaxing and entertaining with a stacked-stone fireplace, built-in wet bar, French doors to the patio, and a folding wall of glass opening the room to the pool. " +
                        "This indoor treat boasts a large freeform pool surrounded by stone flooring and illuminated by a huge greenhouse-like wall of windows extending into the vaulted ceiling. A wall of sliding doors opens to the patio. " +
                        "Off the foyer, a private hall leads to the five-star master suite. This tranquil and spacious getaway begins with a sunny study with closet and private staircase to the second floor. " +
                        "The palatial master bedroom is the perfect retreat where you can relax in your armchairs by the cozy gas fireplace in the winter while watching the television over your mantel or relax on your patio " +
                        "in the summer enjoying soft evening sea breezes. A private hall gives access to your marble powder room with bidet, two large, custom fitted, walk-in closets with pocket doors, and a spacious marble " +
                        "ath boasting two generous vanities with extensive cabinetry (one with dressing table), a Roman spa, and a huge glass-enclosed steam shower with seat and multiple computer-controlled shower heads. " +
                        "Another hall off the foyer provides access to two guest suites with their own luxury baths and custom-fitted walk-in closets. The second-floor landing proffers a dramatic open floor plan with filigree " +
                        "balustrades overlooking the foyer, great room, and den. At one end, a spacious open sitting area is flooded with sunlight through a wall of windows with doors to an expansive rear terrace, " +
                        "ideal for sunbathing or morning coffee. A door opens to a hall off which three generous bedrooms enjoy their own private designer baths. Two have custom-fitted walk-in closets, " +
                        "and one offers an entire wall of closets. At the other end of the floor, a second suite of rooms includes a large staff bedroom with private access to a hall bath, " +
                        "which can also serve a huge bonus room with walk-in linen or game closet and a laundry room. The full finished basement offers ample space for fun and entertainment. " +
                        "One area, with mirrored wall, could serve as a gym, dance studio, or even a ballroom. Another immense space with enough room for any number of activities, " +
                        "provides access to a home theatre and a powder room. Additional features of this exceptional home include inground sprinklers and handicap access. " +
                        "Offering the comfort, style, elegance, and state-of-the-art technology in a resort-like location close to the city, all this home now needs is you.");
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
