package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.Utils.Utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 *
 * test the date format change function
 */
public class DateFormatChangeUnitTest {

    @Test
    public void dateFormatChange() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = Utils.getTodayDate();
        assertEquals(dateFormat.format(new Date()), result);
    }
}