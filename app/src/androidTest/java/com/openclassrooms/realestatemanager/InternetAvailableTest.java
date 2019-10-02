package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.openclassrooms.realestatemanager.Utils.Utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InternetAvailableTest {

    @Test
    public void internetAvailable() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        assertTrue(Utils.isInternetAvailable(context));
    }
}
