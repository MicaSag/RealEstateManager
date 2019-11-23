package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;

import com.openclassrooms.realestatemanager.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;

public class TestData {

    static ContentValues house1(){

        //////////////////////////////     REAL ESTATE AGENT 1 : HOUSE 1    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "House");
        contentValues.put("price", 3999000);
        contentValues.put("area", 7200);
        contentValues.put("numberOfParts", 15);
        contentValues.put("numberOfBathrooms", 8);
        contentValues.put("numberOfBedrooms", 7);
        contentValues.put("description", "Why move to the Hamptons when you can have all they offer so much closer? " +
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
        contentValues.put("photos", photos);
        contentValues.put("photosDescription","[\"outside\",\"facade\",\"living room\",\"dining room\"," +
                "\"kitchen\",\"bedroom\",\"bathroom\",\"swimming-pool\"]");
        contentValues.put("address", Arrays.asList("\"8 Mindy Ct\"", "\"\"", "\"Lattingtown\"", "\"New York\"", "\"United states\"").toString());
        contentValues.put("pointOfInterest", "{\"Swimming Pool\":\"Swimming Pool\",\"Library\":\"Library\"}");
        contentValues.put("status", false);
        contentValues.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(3).withYear(2018).withMonth(4)));
        //contentValuesRealEstateHouse1.put("dateOfSale", LocalDateTime.now().toString());
        contentValues.put("realEstateAgent_Id", 1);

        return contentValues;
    }

    static ContentValues house2(){
        //////////////////////////////     REAL ESTATE AGENT 1 : HOUSE 2    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "House");
        contentValues.put("price", 2014000);
        contentValues.put("area", 6000);
        contentValues.put("numberOfParts", 10);
        contentValues.put("numberOfBathrooms", 5);
        contentValues.put("numberOfBedrooms", 5);
        contentValues.put("description", "Centre Island, Stately Brick Colonial Style Manor Home, " +
                "Perched On 3.26 Waterfront Acres w/ 250 feet of Sandy Beach ." +
                "This Spectacular Property Features 180 Degree Panoramic Waterviews From Almost Every Room." +
                "The Spacious Interior Boasts,10 Ft Ceilings,Radiant Heat, Marble Bathrooms,5 Fireplaces, " +
                "Custom Moldings & Architectural Details Throughout." +
                "The Home Also Includes A Separate 2 Car Gar & 800 Sq Ft Cottage W/Waterviews. " +
                "Beautifully Landscaped Property Perfect For Entertaining W/Steps To Beach.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_outside.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_facade.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_living_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_dining_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_kitchen.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_bedroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house2/house_2_bathroom.jpg\"]");
        contentValues.put("photosDescription","[" +
                "\"outside\"," +
                "\"facade\"," +
                "\"living room\"," +
                "\"dining room\"," +
                "\"kitchen\"," +
                "\"bedroom\"," +
                "\"bathroom\"]");
        contentValues.put("address", Arrays.asList("\"512 Centre Island Rd\"", "\"\"", "\"Centre Island\"", "\"New York\"", "\"United states\"").toString());
        contentValues.put("pointOfInterest", "{" +
                "\"Town hall\":\"Town hall\"," +
                "\"Library\":\"Library\"," +
                "\"School\":\"School\"}");
        contentValues.put("status", false);
        contentValues.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(25).withYear(2019).withMonth(12)));
        //contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
        contentValues.put("realEstateAgent_Id", 1);

        return contentValues;
    }

    static ContentValues penthouse1(){
        //////////////////////////////     REAL ESTATE AGENT 1 : penthouse 1   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Penthouse");
        contentValues.put("price", 2890000);
        contentValues.put("area", 13000);
        contentValues.put("numberOfParts", 25);
        contentValues.put("numberOfBathrooms", 8);
        contentValues.put("numberOfBedrooms", 7);
        contentValues.put("description", "Secluded Waterview Gated 18 Room, 7 Bedrooms, 8.5 Baths, 4 Kitchens Plus 2 Pooliside Cooks Kitchens, " +
                "Indoor/Outdoor Pools With Hot Tubs. Putting Green And Private Path To Beach. Geothermal Heat And Cac, Radiant Heat. " +
                "Koi Pond, 8000 Square Foot Driveway And Terraces With Sophisticated Snow Melt System. Creston Smart Home. " +
                "Full House Security With Camera. Dream Home For Entertainers Whether On Large Or Intimate Scale.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house3/house_3_outside.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house3/house_3_facade.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house3/house_3_living_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house3/house_3_dining_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house3/house_3_kitchen.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house3/house_3_bathroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house3/house_3_swimming_pool.jpg\"]");
        contentValues.put("photosDescription","[" +
                "\"outside\"," +
                "\"facade\"," +
                "\"living room\"," +
                "\"dining room\"," +
                "\"kitchen\"," +
                "\"bathroom\"," +
                "\"swimming-pool\"]");
        contentValues.put("address", Arrays.asList("\"Longwood Rd\"", "\"\"", "\"Sands Point\"", "\"New York\"", "\"United states\"").toString());
        contentValues.put("pointOfInterest", "{" +
                "\"Town hall\":\"Town hall\"," +
                "\"Library\":\"Library\"," +
                "\"School\":\"School\"}");
        contentValues.put("status", false);
        contentValues.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(12).withYear(2019).withMonth(1)));
        //contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
        contentValues.put("realEstateAgent_Id", 1);

        return contentValues;
    }

    static ContentValues penthouse2(){
        //////////////////////////////     REAL ESTATE AGENT 1 : penthouse 2   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Penthouse");
        contentValues.put("price", 3695000);
        contentValues.put("area", 7500);
        contentValues.put("numberOfParts", 15);
        contentValues.put("numberOfBathrooms", 6);
        contentValues.put("numberOfBedrooms", 6);
        contentValues.put("description", "Incredible Price Drop - Huge Opportunity! Brand New Construction! " +
                "Magnificent Brick Colonial sited on 2 verdant acres bordering a nature preserve in the heart of Upper Brookville. " +
                "Located on a quiet cul-de-sac, this timeless beauty blends classic design with modern technology. " +
                "An impressive two-story foyer flows seamlessly to spacious formal and informal living spaces. " +
                "Notable highlights include a gourmet eat-in-kitchen with all high end appliances (Wolf and Sub Zero), butlers pantry, " +
                "imported walnut flooring, formal living room with French doors leading to a covered bluestone terrace, " +
                "a separate library or office, a sumptuous master suite with fireplace, private terrace and spa-like marble bath, " +
                "all en-suite bedrooms (five bedrooms on the second level), Control4 Smart Home Automation & Security system, mud room, " +
                "fully finished lower level including a recreation room, gym, movie theater with adjacent concession stand, " +
                "3- car garage and much more. Excellent location.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house4/house_4_outside.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house4/house_4_facade.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house4/house_4_living_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house4/house_4_dining_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house4/house_4_kitchen.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house4/house_4_bedroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house4/house_4_bathroom.jpg\"]");
        contentValues.put("photosDescription","[" +
                "\"outside\"," +
                "\"facade\"," +
                "\"living room\"," +
                "\"dining room\"," +
                "\"kitchen\"," +
                "\"bedroom\"," +
                "\"bathroom\"]");
        contentValues.put("address", Arrays.asList("\"677 Linda Ct\"", "\"\"", "\"Upper Brookville\"", "\"New York\"", "\"United states\"").toString());
        contentValues.put("pointOfInterest", "{" +
                "\"Town hall\":\"Town hall\"," +
                "\"Library\":\"Library\"," +
                "\"School\":\"School\"}");
        contentValues.put("status", false);
        contentValues.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(17).withYear(2017).withMonth(7)));
        //contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
        contentValues.put("realEstateAgent_Id", 1);

        return contentValues;
    }

    static ContentValues loft1(){
        //////////////////////////////     REAL ESTATE AGENT 1 : loft 1   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Loft");
        contentValues.put("price", 3599000);
        contentValues.put("area", 3764);
        contentValues.put("numberOfParts", 10);
        contentValues.put("numberOfBathrooms", 2);
        contentValues.put("numberOfBedrooms", 3);
        contentValues.put("description", "Waters Edge- A rare opportunity ! Magnificent Mill Neck 410 waterfront property with mooring rights. " +
                "Brick colonial on 7.7 secluded park-like acres with water views & spectacular sunsets from every room! Generator, TAXES BEING REDUCED!");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house5/house_5_outside.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house5/house_5_facade.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house5/house_5_living_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house5/house_5_dining_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house5/house_5_kitchen.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house5/house_5_bedroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house5/house_5_bathroom.jpg\"]");
        contentValues.put("photosDescription","[" +
                "\"outside\"," +
                "\"facade\"," +
                "\"living room\"," +
                "\"dining room\"," +
                "\"kitchen\"," +
                "\"bedroom\"," +
                "\"bathroom\"]");
        contentValues.put("address", Arrays.asList("\"115 Horseshoe Rd\"", "\"\"", "\"Mill Neck\"", "\"New York\"", "\"United states\"").toString());
        contentValues.put("pointOfInterest", "{" +
                "\"Town hall\":\"Town hall\"," +
                "\"Library\":\"Library\"," +
                "\"School\":\"School\"}");
        contentValues.put("status", false);
        contentValues.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(11).withYear(2019).withMonth(2)));
        //contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
        contentValues.put("realEstateAgent_Id", 1);

        return contentValues;
    }

    static ContentValues loft2(){
        //////////////////////////////     REAL ESTATE AGENT 1 : loft 2   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Loft");
        contentValues.put("price", 32295000);
        contentValues.put("area", 6600);
        contentValues.put("numberOfParts", 10);
        contentValues.put("numberOfBathrooms", 6);
        contentValues.put("numberOfBedrooms", 5);
        contentValues.put("description", "Elegant & Grand Estatelike CH Colonial on 1 3/4 Acre Property. 5 BR, 6.5 Bth with Spacious Rms. " +
                "Magnificent Moldings & Details Throughout. Master Suite Boasts Beautiful Bth, Huge WIC & Gym.Finished Bsmt Features Wine Cellar, " +
                "Playroom, Sauna & Bath. Media Room has Bar Area..Parklike Grounds Are Perfect for Entertaining with Gunite IG Htsd Pool, " +
                "Cabana with Bth, Hartru, Slate Patio, Tennis Ct & Fully Equipped Playgound & Built in BBQ. SD#14. Convenient Location to Shops ," +
                "Trans & Houses of Worship");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_outside.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_facade.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_living_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_dining_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_kitchen.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_bedroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_bathroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_swimming_pool.jpg\"]");
        contentValues.put("photosDescription","[" +
                "\"outside\"," +
                "\"facade\"," +
                "\"living room\"," +
                "\"dining room\"," +
                "\"kitchen\"," +
                "\"bedroom\"," +
                "\"bathroom\"," +
                "\"swimming-pool\"]");
        contentValues.put("address", Arrays.asList("\"91 Cedar Ave\"", "\"\"", "\"Hewlett Bay Park\"", "\"New York\"", "\"United states\"").toString());
        contentValues.put("pointOfInterest", "{" +
                "\"Town hall\":\"Town hall\"," +
                "\"Library\":\"Library\"," +
                "\"School\":\"School\"}");
        contentValues.put("status", false);
        contentValues.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(1).withYear(2019).withMonth(8)));
        //contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
        contentValues.put("realEstateAgent_Id", 1);

        return contentValues;
    }

    static ContentValues flat1(){
        //////////////////////////////     REAL ESTATE AGENT 1 : Flat 1   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Flat");
        contentValues.put("price", 2795000);
        contentValues.put("area", 11650);
        contentValues.put("numberOfParts", 20);
        contentValues.put("numberOfBathrooms", 4);
        contentValues.put("numberOfBedrooms", 7);
        contentValues.put("description", "Muttontown. If the cares of the world haven’t left you as you wend your way along a private, " +
                "third-mile, wooded drive, then this fairytale-like estate will complete the job. Like a castle in the woods, " +
                "it greets you with a magnificent brick, stone, and stucco façade, a turret with steeple, arched windows, stone-like balustrades, " +
                "an architectural roof resembling slate, and Brass Lions guarding entry to a large portico and patio. " +
                "Standing majestically on 2.18-acres, this Continental-style abode enjoys a secluded and amazing rear property with endless areas " +
                "for entertaining and pure enjoyment. An expansive bluestone patio extends the width of the house, from a shady veranda at one end, " +
                "past an awning-covered outdoor kitchen replete with grill, granite bar.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_outside.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_facade.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_living_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_dining_room.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_kitchen.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_bedroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_bathroom.jpg\"," +
                "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house7/house_7_swimming_pool.jpg\"]");
        contentValues.put("photosDescription","[" +
                "\"outside\"," +
                "\"facade\"," +
                "\"living room\"," +
                "\"dining room\"," +
                "\"kitchen\"," +
                "\"bedroom\"," +
                "\"bathroom\"," +
                "\"swimming-pool\"]");
        contentValues.put("address", Arrays.asList("\"2017 Midlane South\"", "\"\"", "\"Muttontown\"", "\"New York\"", "\"United states\"").toString());
        contentValues.put("pointOfInterest", "{" +
                "\"Town hall\":\"Town hall\"," +
                "\"Library\":\"Library\"," +
                "\"School\":\"School\"}");
        contentValues.put("status", false);
        contentValues.put("dateEntryOfTheMarket", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        //contentValuesRealEstateHouse2.put("dateOfSale", LocalDateTime.now().toString());
        contentValues.put("realEstateAgent_Id", 1);

        return contentValues;
    }
}
