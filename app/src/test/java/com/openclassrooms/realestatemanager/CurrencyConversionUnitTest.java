package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.Utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * test the currency conversion function
 */
public class CurrencyConversionUnitTest {

    @Test
    public void convertEuroToDollar1() throws Exception {
        int result = Utils.convertEuroToDollar(10);
        assertEquals(11, result);
    }

    @Test
    public void convertEuroToDollar2() throws Exception {
        int result = Utils.convertEuroToDollar(1);
        assertEquals(1, result);
    }

    @Test
    public void convertEuroToDollar3() throws Exception {
        int result = Utils.convertEuroToDollar(175);
        assertEquals(191, result);
    }
}