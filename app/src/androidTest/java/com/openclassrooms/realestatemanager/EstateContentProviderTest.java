package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.Utils.Converters;
import com.openclassrooms.realestatemanager.provider.EstateContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class EstateContentProviderTest {

    // FOR DEBUG
    private static final String TAG = EstateContentProviderTest.class.getSimpleName();

    // FOR DATA
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static long USER_ID = 1;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext(),
                RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver();
    }

    @Test
    public void getEstates() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());

        for (int i=0; i < cursor.getCount();i++){

            cursor.moveToPosition(i);
            Log.d(TAG, "getEstatesWhenNoEstateInserted: =======================================");
            Log.d(TAG, "getEstatesWhenNoEstateInserted: estate_Id ="+cursor.getLong(cursor.getColumnIndexOrThrow("estate_Id")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: type ="+cursor.getString(cursor.getColumnIndexOrThrow("type")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: price ="+cursor.getInt(cursor.getColumnIndexOrThrow("price")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: area ="+cursor.getInt(cursor.getColumnIndexOrThrow("area")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: numberOfParts ="+cursor.getInt(cursor.getColumnIndexOrThrow("numberOfParts")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: numberOfBathrooms ="+cursor.getInt(cursor.getColumnIndexOrThrow("numberOfBathrooms")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: numberOfBedrooms ="+cursor.getInt(cursor.getColumnIndexOrThrow("numberOfBedrooms")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: description ="+cursor.getString(cursor.getColumnIndexOrThrow("description")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: photos ="+ Converters.fromString(cursor.getString(cursor.getColumnIndexOrThrow("photos"))));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: address ="+Converters.fromString(cursor.getString(cursor.getColumnIndexOrThrow("address"))));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: pointOfInterest ="+Converters.fromString(cursor.getString(cursor.getColumnIndexOrThrow("pointOfInterest"))));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: status ="+cursor.getString(cursor.getColumnIndexOrThrow("status")));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: dateEntryOfTheMarket ="+Converters.fromTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow("dateEntryOfTheMarket"))));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: dateOfSale ="+Converters.fromTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow("dateOfSale"))));
            Log.d(TAG, "getEstatesWhenNoEstateInserted: realEstateAgent_Id ="+cursor.getLong(cursor.getColumnIndexOrThrow("realEstateAgent_Id")));
        }
        cursor.close();

        //assertThat(cursor.getCount(), is(18));
    }

    @Test
    public void insertAndGetEstate() {
        // BEFORE : Adding demo item
        final Uri userUri = mContentResolver.insert(EstateContentProvider.URI_ESTATE, generateEstate());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        //assertThat(cursor.getCount(), is(18));
        assertThat(cursor.moveToLast(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("description")), is("A good description"));
    }

    // ---

    private ContentValues generateEstate(){
        final ContentValues values = new ContentValues();
        values.put("type", "Penthouse");
        values.put("price", "33333");
        values.put("area", "100");
        values.put("parts","2");
        values.put("bathrooms","2");
        values.put("bedrooms","2");
        values.put("description","A good description");
        values.put("photos",Converters.fromArrayList(
                new ArrayList<>(Arrays.asList(  "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                                                "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg"))));
        values.put("address",Converters.fromArrayList(
                new ArrayList<>(Arrays.asList(  "Vale Court","Ealing Road","London","TW8 0LN","UK" ))));
        values.put("interests",Converters.fromArrayList(
                new ArrayList<>(Arrays.asList(  "Sushi Tetsu",
                                                "Amrutha Lounge"))));
        values.put("status","1");
        values.put("dateEntryOfTheMarket",Converters.dateToTimestamp(LocalDateTime.now()));
        values.put("dateOfSale",Converters.dateToTimestamp(LocalDateTime.now()));
        values.put("realEstateAgentId","1");
        return values;
    }

    @Test
    public void deleteAndGetEstate() {
        // TEST
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content")
                .authority(EstateContentProvider.AUTHORITY)
                .appendPath(EstateContentProvider.TABLE_NAME_ESTATE)
                .appendPath("15");
        Uri uri = builder.build();
        Log.d(TAG, "deleteAndGetEstate: uri = "+uri);
        int count = mContentResolver.delete(uri,null,null);
        Log.d(TAG, "deleteAndGetEstate: count = "+count);
    }
}
