package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 *
 * test the currency conversion function
 */
public class CurrencyConversionUnitTest {

    @Test
    public void convertEuroToDollar1() throws Exception {
        BigDecimal result = Utils.convertEuroToDollar(new BigDecimal(10));
        assertEquals(10.90, result.doubleValue(),0.01);
        assertEquals(2, result.scale());
    }

    @Test
    public void convertEuroToDollar2() throws Exception {
        BigDecimal result = Utils.convertEuroToDollar(new BigDecimal(147));
        assertEquals(160.23, result.doubleValue(),0.01);
        assertEquals(2, result.scale());
    }

    @Test
    public void convertEuroToDollar3() throws Exception {
        BigDecimal result = Utils.convertEuroToDollar(new BigDecimal(784.68));
        assertEquals(855.30, result.doubleValue(),0.01);
        assertEquals(2, result.scale());
    }
}